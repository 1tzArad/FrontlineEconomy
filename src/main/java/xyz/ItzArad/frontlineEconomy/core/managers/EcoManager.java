package xyz.ItzArad.frontlineEconomy.core.managers;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import xyz.ItzArad.frontlineEconomy.core.Colors;
import xyz.ItzArad.frontlineEconomy.core.models.EcoPlayer;
import xyz.ItzArad.frontlineEconomy.core.models.TransactionData;
import xyz.ItzArad.frontlineEconomy.core.utils.SoundUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class EcoManager {
    @Getter
    private static final Map<UUID, EcoPlayer> players = new HashMap<>();

    private final File file;
    private final FileConfiguration config;

    public EcoManager(File dataFolder) {
        this.file = new File(dataFolder, "players.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void load() {
        if (!config.isConfigurationSection("players")) return;

        for (String key : config.getConfigurationSection("players").getKeys(false)) {
            UUID uuid = UUID.fromString(key);
            String name = config.getString("players." + key + ".name");
            long balance = config.getLong("players." + key + ".balance");
            players.put(uuid, new EcoPlayer(uuid, balance, name));
        }
    }

    public void save() {
        for (EcoPlayer player : players.values()) {
            String path = "players." + player.getUuid();
            config.set(path + ".balance", player.getBalance());
        }

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static EcoPlayer getPlayer(UUID uuid) {
        return players.computeIfAbsent(uuid, id -> new EcoPlayer(id, 0, Objects.requireNonNull(Bukkit.getPlayer(uuid)).getName()));
    }

    public static List<EcoPlayer> getPlayersWithBalance(){
        return getPlayers().values().stream()
                .sorted(Comparator.comparingLong(EcoPlayer::getBalance).reversed())
                .toList();
    }

    public static void pay(Player f, Player t, long amount, String reason){
        EcoPlayer from = getPlayer(f.getUniqueId());
        EcoPlayer to = getPlayer(t.getUniqueId());
        if (amount <= 0 ) return;
        if(from.getBalance() < amount){
            f.sendMessage(Colors.color("<red>You Don't Have Enough Money!")); // messages.yml: en.err.no_money
            return;
        }
        to.deposit(amount);
        from.withdraw(amount);
        TransactionsManager.logTransaction(new TransactionData(f.getUniqueId(), t.getUniqueId(), reason, amount));
        if (t.isOnline()) {
            SoundUtils.playReceiverSound(t);
            t.sendActionBar(Colors.color("<green>You received <white>" + amount + " <green>from <white>" + f.getName() + " <green>Reason: <white>" + reason)); // messages.yml: en.pay.receive
        }
        SoundUtils.playSenderSound(f);
        f.sendActionBar(Colors.color("<green>You paid <white>" + amount + " <green>to <white>" + t.getName() + " <green>Reason: <white>" + reason)); // messages.yml: en.pay.send
    }

}
