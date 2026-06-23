package xyz.ItzArad.frontlineEconomy.core.managers;


import lombok.Getter;
import lombok.experimental.UtilityClass;
import xyz.ItzArad.frontlineEconomy.core.FrontlineEconomy;
import xyz.ItzArad.frontlineEconomy.core.models.TransactionData;
import xyz.ItzArad.frontlineEconomy.core.utils.JsonStorage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.stream.Collectors;

@UtilityClass
public class TransactionsManager {

    @Getter
    private final ConcurrentLinkedQueue<TransactionData> transactionsCache = new ConcurrentLinkedQueue<>();

    private Path storageDir;
    private JsonStorage storage;

    public void init() {
        try {
            storageDir = FrontlineEconomy.getInstance().getDataFolder().toPath().resolve("transactions");
            storage = new JsonStorage(storageDir);

            List<UUID> uuids = storage.listAll();
            for (UUID id : uuids) {
                try {
                    TransactionData tx = storage.load(id, TransactionData.class);
                    if (tx != null) transactionsCache.add(tx);
                } catch (IOException | RuntimeException e) {
                    FrontlineEconomy.getInstance().getLogger().log(Level.SEVERE, "Failed to load transaction " + id, e);
                }
            }

        } catch (IOException e) {
            FrontlineEconomy.getInstance().getLogger().log(Level.SEVERE, "Failed to initialize TransactionsManager", e);
        }
    }

    public void logTransaction(TransactionData tx) {
        transactionsCache.add(tx);
        saveTransactionAsync(tx);
    }

    public CompletableFuture<Void> saveTransactionAsync(TransactionData tx) {
        return storage.saveAsync(tx.getId(), tx)
                .exceptionally(ex -> {
                    FrontlineEconomy.getInstance().getLogger().log(Level.SEVERE, "Failed to save transaction " + tx.getId(), ex);
                    return null;
                });
    }

    public void saveTransactionSync(TransactionData tx) {
        try {
            storage.save(tx.getId(), tx);
        } catch (IOException e) {
            FrontlineEconomy.getInstance().getLogger().log(Level.SEVERE, "Failed to save transaction sync " + tx.getId(), e);
        }
    }

    public CompletableFuture<Void> saveAllAsync() {
        CompletableFuture<?>[] futures = transactionsCache.stream()
                .map(TransactionsManager::saveTransactionAsync)
                .toArray(CompletableFuture[]::new);

        return CompletableFuture.allOf(futures);
    }

    public void saveAllSync() {
        transactionsCache.forEach(TransactionsManager::saveTransactionSync);
    }
    public Set<TransactionData> getTransactionsForPlayer(UUID uuid) {
        return transactionsCache.stream()
                .filter(tx -> tx.getFrom().getUuid().equals(uuid) || tx.getTo().getUuid().equals(uuid))
                .collect(Collectors.toSet());
    }
}
