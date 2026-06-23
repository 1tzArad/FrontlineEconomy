package xyz.ItzArad.frontlineEconomy.core.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class TransactionData {
    private UUID id;
    private Player from;
    private Player to;
    private long amount;
    private long timestamp;
    private String reason;

    public TransactionData(UUID fromUUID, UUID toUUID, String reason, long amount) {
        this.id = UUID.randomUUID();
        this.from = new Player(fromUUID, Objects.requireNonNull(Bukkit.getPlayer(fromUUID)).getName());
        this.to = new Player(toUUID, Objects.requireNonNull(Bukkit.getPlayer(toUUID)).getName());
        this.amount = amount;
        this.reason = reason;
        this.timestamp = Instant.now().toEpochMilli();
    }

    @AllArgsConstructor
    @Getter
    public static class Player{
        UUID uuid;
        String name;
    }
}
