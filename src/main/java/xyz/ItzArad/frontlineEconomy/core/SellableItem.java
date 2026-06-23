package xyz.ItzArad.frontlineEconomy.core;
import org.bukkit.Material;

public enum SellableItem {

        DIAMOND(Material.DIAMOND, 15.0),

        IRON_INGOT(Material.IRON_INGOT, 5.0),
        RAW_IRON(Material.RAW_IRON, 4.0),
        IRON_BLOCK(Material.IRON_BLOCK, 40.0),

        GOLD_INGOT(Material.GOLD_INGOT, 10.0),
        RAW_GOLD(Material.RAW_GOLD, 8.0),
        GOLD_BLOCK(Material.GOLD_BLOCK, 90.0),

        COPPER_INGOT(Material.COPPER_INGOT, 7.0),
        RAW_COPPER(Material.RAW_COPPER, 7.0),
        COPPER_BLOCK(Material.COPPER_BLOCK, 63.0),

        LAPIS_LAZULI(Material.LAPIS_LAZULI, 7.0),
        LAPIS_BLOCK(Material.LAPIS_BLOCK, 7.0),

        EMERALD(Material.EMERALD, 5.0),
        EMERALD_BLOCK(Material.EMERALD, 45.0),

        CACTUS(Material.CACTUS, 2),
        SUGAR_CANE(Material.SUGAR_CANE, 2),
        BAMBOO(Material.BAMBOO, 0.5),
        SWEET_BERRIES(Material.SWEET_BERRIES, 1),
        HEAVY_CORE(Material.HEAVY_CORE, 50000.0),
        CREAKING_HEART(Material.CREAKING_HEART, 50.0),
        HEART_OF_THE_SEA(Material.HEART_OF_THE_SEA, 10.0),
        BREEZE_ROD(Material.BREEZE_ROD, 10.0),
        END_CRYSTAL(Material.END_CRYSTAL, 20.0),
        VINE(Material.VINE, 2.0),
        CLAY(Material.CLAY, 2),
        CLAY_BALL(Material.CLAY_BALL, 0.5),

        // Farming
        WHEAT(Material.WHEAT, 2.0),
        CARROT(Material.CARROT, 2.0),
        POTATO(Material.POTATO, 2.0),
        MELON(Material.MELON, 4.5),
        MELON_SLICE(Material.MELON_SLICE, 0.5),
        PUMPKIN(Material.PUMPKIN, 4.5),

        ;

        private final Material material;
        private final double price;

        SellableItem(Material material, double price) {
                this.material = material;
                this.price = price;
        }

        public Material getMaterial() {
                return material;
        }

        public double getPrice() {
                return price;
        }

        public static SellableItem fromMaterial(Material material) {
                for (SellableItem item : values()) {
                        if (item.material == material) return item;
                }
                return null;
        }
}