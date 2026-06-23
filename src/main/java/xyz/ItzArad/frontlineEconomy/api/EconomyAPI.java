package xyz.ItzArad.frontlineEconomy.api;

import java.util.UUID;

public interface EconomyAPI {

    double getBalance(UUID uuid);

    boolean has(UUID uuid, long amount);

    void deposit(UUID uuid, long amount);

    boolean withdraw(UUID uuid, long amount);

}
