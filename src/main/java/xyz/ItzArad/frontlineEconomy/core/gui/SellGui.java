package xyz.ItzArad.frontlineEconomy.core.gui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.ItzArad.frontlineEconomy.core.Colors;
import xyz.ItzArad.frontlineEconomy.core.SellableItem;
import xyz.ItzArad.frontlineEconomy.core.managers.EcoManager;

import java.util.Map;

public class SellGui {



    public void openSellGUI(Player player){
        Gui sellGui = Gui.gui()
                .title(Colors.color("Sell GUI"))
                .rows(6)
                .create();

        ItemStack pane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = pane.getItemMeta();
        meta.displayName(Component.text(" "));
        pane.setItemMeta(meta);

        GuiItem grayPane = new GuiItem(pane);

        for (int slot = 45; slot <= 53; slot++) {
            if (slot == 49) continue;
            sellGui.setItem(slot, grayPane);
        }

        ItemStack tpd = new ItemStack(Material.PAPER);
        ItemMeta tpd_meta = tpd.getItemMeta();
        tpd_meta.displayName(Colors.color("<gold>Total: 0$"));
        tpd.setItemMeta(tpd_meta);

        GuiItem totalPriceDisplay = new GuiItem(tpd);

        sellGui.setItem(49, totalPriceDisplay);

        sellGui.setDefaultClickAction(event -> {
            ItemStack clicked = event.getCurrentItem();
            if (clicked == null) return;
            SellableItem sellable = SellableItem.fromMaterial(clicked.getType());
            if (sellable == null) {
                event.setCancelled(true);
            } else {
                updateTotalPrice(sellGui);
            }
        });

        sellGui.setDragAction(event -> {
            for (Map.Entry<Integer, ItemStack> stack : event.getNewItems().entrySet()) {
                SellableItem sellable = SellableItem.fromMaterial(stack.getValue().getType());
                if (sellable == null) {
                    event.setCancelled(true);
                    break;
                }
            }
            updateTotalPrice(sellGui);
        });
        sellGui.setCloseGuiAction(event -> {
            double total = calculateTotal(sellGui);
            if (total <= 0) return;
            EcoManager.getPlayer(player.getUniqueId()).deposit((long) total);
            event.getPlayer().sendMessage(Colors.color("<green>Total sell price: $" + total));
        });

        sellGui.open(player);
    }

    private void updateTotalPrice(Gui gui) {
        ItemStack tpd = new ItemStack(Material.PAPER);
        ItemMeta tpd_meta = tpd.getItemMeta();
        tpd_meta.displayName(Colors.color("<gold>Total: "+ calculateTotal(gui) +"$"));
        tpd.setItemMeta(tpd_meta);

        GuiItem totalPriceDisplay = new GuiItem(tpd);
        gui.updateItem(49, totalPriceDisplay);
    }

    private double calculateTotal(Gui gui) {
        double total = 0;
        for (ItemStack stack : gui.getInventory().getContents()) {
            if (stack == null || stack.getType() == Material.AIR) continue;
            SellableItem sellable = SellableItem.fromMaterial(stack.getType());
            if (sellable != null) {
                total += sellable.getPrice() * stack.getAmount();
            }
        }
        return total;
    }

}
