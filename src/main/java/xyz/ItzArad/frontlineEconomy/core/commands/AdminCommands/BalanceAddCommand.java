package xyz.ItzArad.frontlineEconomy.core.commands.AdminCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.ItzArad.frontlineEconomy.core.Colors;
import xyz.ItzArad.frontlineEconomy.core.interfaces.AdminCmd;
import xyz.ItzArad.frontlineEconomy.core.interfaces.AdminTab;
import xyz.ItzArad.frontlineEconomy.core.managers.EcoAdminManager;
import xyz.ItzArad.frontlineEconomy.core.managers.EcoManager;
import xyz.ItzArad.frontlineEconomy.core.models.EcoPlayer;

import java.util.List;

public class BalanceAddCommand implements AdminCmd, AdminTab {
    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "add player balance";
    }

    @Override
    public String getPermission() {
        return "FrontlineEconomy.Admin.Add";
    }

    @Override
    public String getUsage() {
        return "/ecoadmin add <player> <amount> <reason>";
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if(args.length < 3){
            sender.sendMessage(Colors.color("<gold>Usage: <yellow>" + getUsage()));
            return true;
        }
        String target_player = args[0];
        long amount = Long.parseLong(args[1]);
        String reason = String.join(" ", java.util.Arrays.copyOfRange(args, 2, args.length));

        Player p = (Player) sender;

        EcoPlayer player = EcoManager.getPlayer(Bukkit.getPlayerUniqueId(target_player));
        EcoPlayer admin = EcoManager.getPlayer(p.getUniqueId());
        EcoAdminManager.addPlayerBalance(admin, player, amount, reason);
        p.sendActionBar(Colors.color("<yellow>Be Balance player e <gold>" + target_player + "<yellow> ba movafaghiyat ba dalil <gold>" + reason + "<yellow> be meghadr <gold>" + amount + "<yellow> ezafe shod!"));
        return true;
    }

    @Override
    public List<String> onTab(CommandSender commandSender, String[] args) {
        if (args.length == 1) {
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .sorted()
                    .toList();
        }
        return List.of();
    }
}
