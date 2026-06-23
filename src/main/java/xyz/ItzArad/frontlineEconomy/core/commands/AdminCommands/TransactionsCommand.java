package xyz.ItzArad.frontlineEconomy.core.commands.AdminCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ItzArad.frontlineEconomy.core.Colors;
import xyz.ItzArad.frontlineEconomy.core.interfaces.AdminCmd;
import xyz.ItzArad.frontlineEconomy.core.interfaces.AdminTab;
import xyz.ItzArad.frontlineEconomy.core.managers.TransactionsManager;
import xyz.ItzArad.frontlineEconomy.core.models.TransactionData;

import java.util.List;
import java.util.UUID;

public class TransactionsCommand implements AdminCmd, AdminTab {
    @Override
    public String getName() {
        return "transactions";
    }

    @Override
    public String getDescription() {
        return "get player's last transactions!";
    }

    @Override
    public String getPermission() {
        return "FrontlineEconomy.Admin.Transactions";
    }

    @Override
    public String getUsage() {
        return "/ecoadmin transactions <player>";
    }

    @Override
    public boolean isPlayerOnly() {
        return false;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if(args.length < 1){
            sender.sendMessage(Colors.color("<gold>Usage: <yellow>" + getUsage()));
            return true;
        }
        String target_player = args[0];
        UUID player_uuid = Bukkit.getPlayerUniqueId(target_player);
        if(player_uuid == null){
            sender.sendMessage(Colors.color("This player doesn't exists!"));
            return true;
        }
        sender.sendMessage(Colors.color("<gold>" + target_player + "<yellow>'s transactions:"));
        sender.sendMessage(Colors.color("<dark_gray>------------------------------"));
        if(TransactionsManager.getTransactionsForPlayer(player_uuid).isEmpty()){
            sender.sendMessage(Colors.color("<gray>There is no transactions!"));
        }
        for (TransactionData transactionData : TransactionsManager.getTransactionsForPlayer(player_uuid)){
            sender.sendMessage(Colors.color("<gold>ID: <yellow>" + transactionData.getId()));
            sender.sendMessage(Colors.color("<gold>FROM: <yellow>" + transactionData.getFrom().getName()));
            sender.sendMessage(Colors.color("<gold>TO: <yellow>" + transactionData.getTo().getName()));
            sender.sendMessage(Colors.color("<gold>AMOUNT: <yellow>" + transactionData.getAmount()));
            sender.sendMessage(Colors.color("<gold>REASON: <yellow>" + transactionData.getReason()));
            sender.sendMessage(Colors.color("<gold>TIME: <yellow>" + transactionData.getTimestamp()));
            sender.sendMessage(Colors.color("<dark_gray>------------------------------"));
        }
        return true;
    }

    @Override
    public List<String> onTab(CommandSender commandSender, String[] args) {
        if(args.length == 1){
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .sorted()
                    .toList();
        }
        return List.of();
    }
}
