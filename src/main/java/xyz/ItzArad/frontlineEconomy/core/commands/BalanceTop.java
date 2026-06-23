package xyz.ItzArad.frontlineEconomy.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import xyz.ItzArad.frontlineEconomy.core.Colors;
import xyz.ItzArad.frontlineEconomy.core.managers.EcoManager;
import xyz.ItzArad.frontlineEconomy.core.models.EcoPlayer;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class BalanceTop implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        int page = 1;
        if(args.length >= 1){
            page = Integer.parseInt(args[0]);
            if(page < 1) page = 1;
        }

        int pageSize = 10;
        int place = 1;

        List<EcoPlayer> sorted = EcoManager.getPlayersWithBalance();
        List<EcoPlayer> playersPage = getPlayersPage(page, pageSize);

        int maxPage = (int) Math.ceil((double) sorted.size() / pageSize);

        if(playersPage.isEmpty()){
            commandSender.sendMessage(Colors.color("<red>Page not found. Max page: " + maxPage));
            return true;
        }

        commandSender.sendMessage(Colors.color("<yellow>--------<gold>| <yellow>FrontlineEconomy Baltop <gold>- <yellow> Page "+ page +" <gold>|<yellow>--------"));

        int startRank = (page -1) * pageSize + 1;

        for (int i = 0; i < playersPage.size(); i++){
            EcoPlayer p = playersPage.get(i);
            commandSender.sendMessage(Colors.color("<gold>" + (startRank + i) + ". <yellow>" + Objects.requireNonNull(Bukkit.getOfflinePlayer(p.getUuid())).getName() + " <gold>- <yellow>" + p.getBalance()));
        }
        return true;
    }
    private List<EcoPlayer> getPlayersPage(int page, int pageSize){
        List<EcoPlayer> sorted = EcoManager.getPlayersWithBalance();

        int from = (page - 1) * pageSize;
        if(from >= sorted.size()) return Collections.emptyList();

        int to = Math.min(from + pageSize, sorted.size());

        return sorted.subList(from, to);
    }
}
