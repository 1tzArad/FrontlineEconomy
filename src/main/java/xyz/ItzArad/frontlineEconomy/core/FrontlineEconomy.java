package xyz.ItzArad.frontlineEconomy.core;

import lombok.Getter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.ItzArad.frontlineEconomy.api.EconomyAPI;
import xyz.ItzArad.frontlineEconomy.core.commands.*;
import xyz.ItzArad.frontlineEconomy.core.managers.EcoAdminManager;
import xyz.ItzArad.frontlineEconomy.core.managers.EcoManager;
import xyz.ItzArad.frontlineEconomy.core.managers.TransactionsManager;

import java.util.Map;

public final class FrontlineEconomy extends JavaPlugin {

    @Getter private EcoManager economyManager;
    @Getter private static FrontlineEconomy instance;

    @Override
    public void onEnable() {
        instance = this;
        if (!getDataFolder().exists())
            getDataFolder().mkdirs();

        economyManager = new EcoManager(getDataFolder());
        economyManager.load();

        getServer().getServicesManager().register(
                EconomyAPI.class,
                new EconomyService(economyManager),
                this,
                ServicePriority.High
        );

        TransactionsManager.init();

        EcoAdminManager.init();

        // register commands
        registerCommands(Map.of(
                "balance", new BalanceCommand(),
                "pay", new PayCommand(),
                "ecoadmin", new AdminCommand(),
                "sell", new SellCommand(),
                "balancetop", new BalanceTop()
        ));

        getLogger().info("FrontlineEconomy enabled");
    }

    private void registerCommands(Map<String, CommandExecutor> commandExecutorMap) {
        commandExecutorMap.forEach((s, commandExecutor) -> {
            final PluginCommand pluginCommand = getCommand(s.toLowerCase());
            if (pluginCommand == null) {
                getLogger().info("Failed to register command " + s);
                return;
            }
            pluginCommand.setExecutor(commandExecutor);
            getLogger().info(s + " command loaded successfully!");
        });
    }
    @Override
    public void onDisable() {

        if (economyManager != null)
            economyManager.save();

        getServer().getServicesManager().unregisterAll(this);

        getLogger().info("FrontlineEconomy disabled");
    }
}
