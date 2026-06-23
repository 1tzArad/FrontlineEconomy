package xyz.ItzArad.frontlineEconomy.core.managers;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import xyz.ItzArad.frontlineEconomy.core.FrontlineEconomy;
import xyz.ItzArad.frontlineEconomy.core.models.EcoAdminActionsData;
import xyz.ItzArad.frontlineEconomy.core.models.EcoPlayer;
import xyz.ItzArad.frontlineEconomy.core.utils.JsonStorage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;

@UtilityClass
public class EcoAdminManager {

    @Getter
    private final ConcurrentLinkedQueue<EcoAdminActionsData> adminActionsCache = new ConcurrentLinkedQueue<>();

    private Path storageDir;
    private JsonStorage storage;

    public void init() {
        try {
            storageDir = FrontlineEconomy.getInstance().getDataFolder().toPath().resolve("admin_actions");
            storage = new JsonStorage(storageDir);

            List<UUID> uuids = storage.listAll();
            for (UUID id : uuids) {
                try {
                    EcoAdminActionsData tx = storage.load(id, EcoAdminActionsData.class);
                    if (tx != null) adminActionsCache.add(tx);
                } catch (IOException | RuntimeException e) {
                    FrontlineEconomy.getInstance().getLogger().log(Level.SEVERE, "Failed to load EcoAdminAction " + id, e);
                }
            }

        } catch (IOException e) {
            FrontlineEconomy.getInstance().getLogger().log(Level.SEVERE, "Failed to initialize EcoAdminManager", e);
        }
    }

    public void logAdminAction(EcoAdminActionsData tx) {
        adminActionsCache.add(tx);
        saveAdminActionAsync(tx);
    }

    public CompletableFuture<Void> saveAdminActionAsync(EcoAdminActionsData tx) {
        return storage.saveAsync(tx.getUuid(), tx)
                .exceptionally(ex -> {
                    FrontlineEconomy.getInstance().getLogger().log(Level.SEVERE, "Failed to save admin actions " + tx.getUuid(), ex);
                    return null;
                });
    }

    public void saveAdminActionSync(EcoAdminActionsData tx) {
        try {
            storage.save(tx.getUuid(), tx);
        } catch (IOException e) {
            FrontlineEconomy.getInstance().getLogger().log(Level.SEVERE, "Failed to save admin actions sync " + tx.getUuid(), e);
        }
    }

    public CompletableFuture<Void> saveAllAsync() {
        CompletableFuture<?>[] futures = adminActionsCache.stream()
                .map(EcoAdminManager::saveAdminActionAsync)
                .toArray(CompletableFuture[]::new);

        return CompletableFuture.allOf(futures);
    }

    public void saveAllSync() {
        adminActionsCache.forEach(EcoAdminManager::saveAdminActionSync);
    }
    public List<EcoAdminActionsData> getAdminActionForPlayer(UUID uuid) {
        return adminActionsCache.stream()
                .filter(tx -> tx.getAdmin().getUuid().equals(uuid) || tx.getPlayer().getUuid().equals(uuid))
                .toList();
    }

    /*
    =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
     */

    public void setPlayerBalance(EcoPlayer admin, EcoPlayer player, long amount, String reason){
        player.setBalance(amount);
        // log
        logAdminAction(new EcoAdminActionsData(admin, player, amount, reason, EcoAdminActionsData.Action.SET));
    }
    public void takePlayerBalance(EcoPlayer admin, EcoPlayer player, long amount, String reason){
        player.withdraw(amount);
        // log
        logAdminAction(new EcoAdminActionsData(admin, player, amount, reason, EcoAdminActionsData.Action.TAKE));
    }
    public void addPlayerBalance(EcoPlayer admin, EcoPlayer player, long amount, String reason){
        player.deposit(amount);
        // log
        logAdminAction(new EcoAdminActionsData(admin, player, amount, reason, EcoAdminActionsData.Action.ADD));
    }

    public void resetPlayerBalance(EcoPlayer admin, EcoPlayer player, String reason){
        player.setBalance(0);
        // log
        logAdminAction(new EcoAdminActionsData(admin, player, 0, reason, EcoAdminActionsData.Action.RESET));
    }

}
