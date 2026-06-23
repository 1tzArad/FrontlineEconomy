package xyz.ItzArad.frontlineEconomy.core.commands;

import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.ItzArad.frontlineEconomy.core.Colors;
import xyz.ItzArad.frontlineEconomy.core.commands.AdminCommands.*;
import xyz.ItzArad.frontlineEconomy.core.interfaces.AdminCmd;
import xyz.ItzArad.frontlineEconomy.core.interfaces.AdminTab;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminCommand implements CommandExecutor, TabCompleter {
    @Getter private final Map<String, AdminCmd> adminCommandMap = new HashMap<>();

    public AdminCommand(){
        registerCommand(new BalanceSetCommand());
        registerCommand(new BalanceAddCommand());
        registerCommand(new BalanceTakeCommand());
        registerCommand(new BalanceResetCommand());
        registerCommand(new TransactionsCommand());
    }

    private void registerCommand(AdminCmd ac){
        adminCommandMap.put(ac.getName().toLowerCase(), ac);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if(!(commandSender.hasPermission("FrontlineEconomy.Admin"))){
            commandSender.sendMessage(Colors.color("You don't have enough permission to use this command!"));
            return true;
        }
        if(args.length < 1){
            commandSender.sendMessage(Colors.color("<yellow>Admin Commands<gold>:"));
            for (AdminCmd adminCommand : getAdminCommandMap().values()){
                commandSender.sendMessage(Colors.color("<yellow>" + adminCommand.getName() + "<gold>:<yellow> " + adminCommand.getDescription() + " <gold>- <yellow>Usage<gold>: <yellow>" + adminCommand.getUsage()));
            }
            return true;
        }
        AdminCmd ac = getAdminCommandMap().get(args[0]);
        if(ac.isPlayerOnly() && !(commandSender instanceof Player player)){
         commandSender.sendMessage(Colors.color("<red>This Command is only for players!"));
         return true;
        }
        if(!(commandSender.hasPermission(ac.getPermission()))){
            commandSender.sendMessage(Colors.color("<red>You don't have enough permission to use this command!"));
            return true;
        }
        return ac.execute(commandSender, Arrays.copyOfRange(args, 1, args.length));
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if(args.length == 1){
            return getAdminCommandMap().values().stream()
                    .filter(cmd -> cmd.getName().startsWith(args[0].toLowerCase()))
                    .filter(cmd -> commandSender.hasPermission(cmd.getPermission()))
                    .map(AdminCmd::getName)
                    .sorted()
                    .toList();
        }
        AdminCmd cmd = getAdminCommandMap().get(args[0].toLowerCase());
        if(cmd instanceof AdminTab tab){
            return tab.onTab(commandSender, Arrays.copyOfRange(args, 1, args.length));
        }
        return List.of();
    }
}
