package xyz.ItzArad.frontlineEconomy.core.interfaces;

import org.bukkit.command.CommandSender;

public interface AdminCmd {
    String getName();
    String getDescription();
    String getPermission();
    String getUsage();
    boolean isPlayerOnly();
    boolean execute(CommandSender sender, String[] args);
}
