package xyz.ItzArad.frontlineEconomy.core.models;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.key.KeyPattern;

import java.util.UUID;

@Getter
public class EcoPlayer {
    private final UUID uuid;
    private final String name;
    @Setter
    @Getter
    private long balance;

    public EcoPlayer(UUID uuid, long balance, String name) {
        this.uuid = uuid;
        this.balance = balance;
        this.name = name;
    }

    public void deposit(long amount) {
        balance += amount;
    }

    public boolean withdraw(long amount) {
        if (balance < amount) return false;
        balance -= amount;
        return true;
    }

}
