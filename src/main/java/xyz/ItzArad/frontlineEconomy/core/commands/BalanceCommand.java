package xyz.ItzArad.frontlineEconomy.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.ItzArad.frontlineEconomy.core.Colors;
import xyz.ItzArad.frontlineEconomy.core.managers.EcoManager;
import xyz.ItzArad.frontlineEconomy.core.models.EcoPlayer;

public class BalanceCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if(!(commandSender instanceof Player player)) return true;
        if(strings.length == 1){
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(strings[0]);

            EcoPlayer ecoPlayer = EcoManager.getPlayer(Bukkit.getPlayerUniqueId(strings[0]));
            if(ecoPlayer == null){
                player.sendMessage(Colors.color("<red>This player doesn't exists!"));
                return true;
            }
            player.sendMessage(Colors.color("<gold>"+ strings[0] +"'s Balance:</gold> <yellow>" + ecoPlayer.getBalance()));
            return true;
        }
        player.sendMessage(Colors.color("<gold>Your Balance:</gold> <yellow>" + EcoManager.getPlayer(player.getUniqueId()).getBalance()));
        return true;
    }
}
