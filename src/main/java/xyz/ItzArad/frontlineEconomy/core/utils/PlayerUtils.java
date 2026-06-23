package xyz.ItzArad.frontlineEconomy.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import xyz.ItzArad.frontlineEconomy.core.Colors;

public class PlayerUtils {
    public static ItemStack getPlayerHead(String name) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();

        if (meta != null) {
            meta.setOwningPlayer(Bukkit.getOfflinePlayer(name));
            meta.displayName(Colors.color("<yellow>" + name));
            head.setItemMeta(meta);
        }

        return head;
    }
}
