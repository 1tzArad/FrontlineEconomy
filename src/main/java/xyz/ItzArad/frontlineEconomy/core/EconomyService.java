package xyz.ItzArad.frontlineEconomy.core;

import xyz.ItzArad.frontlineEconomy.api.EconomyAPI;
import xyz.ItzArad.frontlineEconomy.core.managers.EcoManager;

import java.util.UUID;

public class EconomyService implements EconomyAPI {

    private final EcoManager manager;

    public EconomyService(EcoManager manager) {
        this.manager = manager;
    }

    @Override
    public double getBalance(UUID uuid) {
        return EcoManager.getPlayer(uuid).getBalance();
    }

    @Override
    public boolean has(UUID uuid, long amount) {
        return getBalance(uuid) >= amount;
    }

    @Override
    public void deposit(UUID uuid, long amount) {
        EcoManager.getPlayer(uuid).deposit(amount);
    }

    @Override
    public boolean withdraw(UUID uuid, long amount) {
        return EcoManager.getPlayer(uuid).withdraw(amount);
    }
}
