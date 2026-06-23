package xyz.ItzArad.frontlineEconomy.core.interfaces;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface AdminTab {
    List<String> onTab(CommandSender commandSender, String[] args);
}
