package xyz.ItzArad.frontlineEconomy.core.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.Objects;
import java.util.UUID;

@Getter
public class EcoAdminActionsData {

    UUID uuid;
    Player admin;
    Player player;
    Action action;
    double amount;
    String reason;
    private long timestamp;

    public EcoAdminActionsData(EcoPlayer admin, EcoPlayer player, double amount, String reason, Action action){
        this.uuid = UUID.randomUUID();
        this.admin = new Player(admin.getUuid(), Objects.requireNonNull(Bukkit.getPlayer(admin.getUuid())).getName());
        this.player = new Player(player.getUuid(), Objects.requireNonNull(Bukkit.getPlayer(player.getUuid())).getName());
        this.amount = amount;
        this.action = action;
        this.reason = reason;
        this.timestamp = System.currentTimeMillis();
    }

    public enum Action{
        SET,
        TAKE,
        ADD,
        RESET
    }

    @AllArgsConstructor
    @Getter
    public static class Player{
        UUID uuid;
        String name;
    }

}
