package xyz.ItzArad.frontlineEconomy.core.gui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.ItzArad.frontlineEconomy.core.managers.EcoManager;
import xyz.ItzArad.frontlineEconomy.core.utils.PlayerUtils;
import xyz.ItzArad.frontlineEconomy.core.Colors;

import java.util.List;

public class PayGui {

    public Gui openPayGui(Player from, Player to, long amount, String reason){
        Gui payGui = Gui.gui()
                .title(Colors.color("<gradient:#55ff55:#246b24>Pay To " + to.getName() +"</gradient>"))
                .rows(3)
                .disableAllInteractions()
                .create();
        ItemStack pane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = pane.getItemMeta();
        meta.displayName(Component.text(" "));
        pane.setItemMeta(meta);

        GuiItem grayPane = new GuiItem(pane);

        for (int slot = 0; slot <= 26; slot++) {
            if (slot >= 10 && slot <= 16) continue;

            payGui.setItem(slot, grayPane);
        }

        ItemStack receiverSkull = PlayerUtils.getPlayerHead(to.getName());
        ItemMeta reciverSkullItemMeta = receiverSkull.getItemMeta();
        reciverSkullItemMeta.setLore(List.of(
                "Player Name: " + to.getName(),
                "Status? " + (to.isOnline() ? "Online" : "Offline")
        ));
        receiverSkull.setItemMeta(reciverSkullItemMeta);
        payGui.setItem(10, new GuiItem(receiverSkull));

        ItemStack amountItem = new ItemStack(Material.PAPER);
        ItemMeta amountMeta = amountItem.getItemMeta();
        amountMeta.displayName(Colors.color("<yellow>Amount: " + amount));
        amountItem.setItemMeta(amountMeta);

        payGui.setItem(11, new GuiItem(amountItem));

        ItemStack reasonItem = new ItemStack(Material.BOOK);
        ItemMeta reasonItemItemMeta = reasonItem.getItemMeta();
        reasonItemItemMeta.displayName(Colors.color("<yellow>Pay Reason"));
        reasonItemItemMeta.setLore(List.of(reason));
        reasonItem.setItemMeta(reasonItemItemMeta);

        payGui.setItem(12, new GuiItem(reasonItem));

        ItemStack cancelItem = new ItemStack(Material.RED_DYE);
        ItemMeta cancelItemMeta = cancelItem.getItemMeta();
        cancelItemMeta.displayName(Colors.color("<red>Cancel"));
        cancelItem.setItemMeta(cancelItemMeta);

        payGui.setItem(15, new GuiItem(cancelItem, event -> {
            event.setCancelled(true);
            event.getInventory().close();
        }));

        ItemStack acceptItem = new ItemStack(Material.TURTLE_SCUTE);
        ItemMeta accpetItemMeta = acceptItem.getItemMeta();
        accpetItemMeta.displayName(Colors.color("<green>Accept"));
        acceptItem.setItemMeta(accpetItemMeta);

        payGui.setItem(16, new GuiItem(acceptItem, event -> {
            event.setCancelled(true);
            EcoManager.pay(from, to, amount, reason);
            event.getInventory().close();
        }));

        return payGui;
    }

}
