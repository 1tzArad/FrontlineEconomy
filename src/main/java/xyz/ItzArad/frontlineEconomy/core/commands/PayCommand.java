package xyz.ItzArad.frontlineEconomy.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.ItzArad.frontlineEconomy.core.gui.PayGui;
import xyz.ItzArad.frontlineEconomy.core.Colors;

import java.util.List;

public class PayCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage(Colors.color("<red>Only players can use this command!"));
            return true;
        }

        if (args.length < 2) {
            player.sendMessage(Colors.color("<red>Usage: /pay <player> <amount> [reason]"));
            return true;
        }

        Player to = Bukkit.getOnlinePlayers().stream()
                .filter(p -> !p.equals(player))
                .filter(p -> p.getLocation().distance(player.getLocation()) <= 5)
                .filter(p -> p.getName().equalsIgnoreCase(args[0]))
                .findFirst()
                .orElse(null);

        if (to == null) {
            player.sendMessage(Colors.color("<red>Player not found or too far away!"));
            return true;
        }

        long amount;
        try {
            amount = Long.parseLong(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage(Colors.color("<red>Amount must be a number!"));
            return true;
        }

        String reason = args.length >= 3 ? String.join(" ", java.util.Arrays.copyOfRange(args, 2, args.length)) : "No Reason Has Entered!";

        new PayGui().openPayGui(player, to, amount, reason).open(player);

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) return List.of();

        if (args.length == 1) {
            return Bukkit.getOnlinePlayers().stream()
                    .filter(p -> !p.equals(player))
                    .filter(p -> p.getLocation().distance(player.getLocation()) <= 5)
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase()))
                    .toList();
        }

        return List.of();
    }
}
