package org.kolbasa3.xcore.gui.craft;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.kolbasa3.xcore.XCore;
import org.kolbasa3.xcore.utils.ItemUtil;

import java.util.Objects;

import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class ItemCraftGUI {

    public ItemCraftGUI(Player p, ItemStack item) {
        NamespacedKey key = new NamespacedKey(XCore.getInstance(), "custom-item");
        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();
        String nm = item.getItemMeta().getDisplayName();

        if(pdc.has(key, PersistentDataType.STRING)) {
            if (Objects.equals(pdc.get(key, PersistentDataType.STRING), "obsidian-tnt"))
                nm = "Обсидиановый динамит";
            else if (Objects.equals(pdc.get(key, PersistentDataType.STRING), "water-tnt"))
                nm = "Водяной динамит";
            else if (Objects.equals(pdc.get(key, PersistentDataType.STRING), "ruby-posoh"))
                nm = "Рубиновый посох (/smith)";
            else if (Objects.equals(pdc.get(key, PersistentDataType.STRING), "ametist-posoh"))
                nm = "Аметистовый посох (/smith)";

        } else nm = nm.replace("§x§F§F§9§D§3§5", "")
                    .replace("§x§3§5§A§6§F§F", "");
        Inventory inv = Bukkit.createInventory(null, 27, Component.text("   §0⁘ " + nm));

        ItemStack is = new ItemUtil(Material.BLACK_STAINED_GLASS_PANE, "§f", null).build(null);
        for (int i = 0; i < 27; i++) {
            if (i == 2) i = 9;
            else if (i == 11) i = 18;
            else if (i == 20) i = 7;
            else if (i == 9) i = 16;
            else if (i == 18) i = 25;
            inv.setItem(i, is);
        }
        inv.setItem(5, is);
        inv.setItem(14, is);
        inv.setItem(23, is);
        inv.setItem(6, is);
        inv.setItem(24, is);

        inv.setItem(15, item);

        if (item.getType().equals(Material.DIAMOND_PICKAXE)) {
            for (int i = 2; i < 5; i++) {
                inv.setItem(i, new ItemStack(Material.EMERALD));
            }

            is = new ItemStack(Material.STICK);
            inv.setItem(12, is);
            inv.setItem(21, is);

        } else if (item.getType().equals(Material.DIAMOND_AXE)) {
            is = new ItemStack(Material.EMERALD);
            inv.setItem(11, is);
            inv.setItem(2, is);
            inv.setItem(3, is);

            is = new ItemStack(Material.STICK);
            inv.setItem(12, is);
            inv.setItem(21, is);

        } else if (item.getType().equals(Material.DIAMOND_SWORD)) {
            is = new ItemStack(Material.EMERALD);
            inv.setItem(3, is);
            inv.setItem(12, is);

            is = new ItemStack(Material.STICK);
            inv.setItem(21, is);

        } else if (item.getType().equals(Material.ENCHANTED_GOLDEN_APPLE)) {
            for (int i = 2; i < 23; i++) {
                if (i == 5) i = 11;
                if (i == 14) i = 20;
                if (i == 12) i++;
                inv.setItem(i, new ItemStack(Material.GOLD_BLOCK));
            }

            is = new ItemStack(Material.GOLDEN_APPLE);
            inv.setItem(12, is);

        } else if (item.getType().equals(Material.EXPERIENCE_BOTTLE)) {
            is = new ItemStack(Material.GOLD_NUGGET);
            inv.setItem(2, is);
            inv.setItem(4, is);
            inv.setItem(20, is);
            inv.setItem(22, is);

            is = new ItemStack(Material.REDSTONE);
            inv.setItem(3, is);
            inv.setItem(11, is);
            inv.setItem(13, is);
            inv.setItem(21, is);

            inv.setItem(12, new ItemStack(Material.GLASS_BOTTLE));

        } else if(item.getType().equals(Material.TARGET)) {
            is = new ItemStack(Material.SAND);
            inv.setItem(2, is);
            inv.setItem(4, is);
            inv.setItem(20, is);
            inv.setItem(22, is);

            is = new ItemStack(Material.GLASS);
            inv.setItem(3, is);
            inv.setItem(11, is);
            inv.setItem(13, is);
            inv.setItem(21, is);

            inv.setItem(12, new ItemStack(Material.REDSTONE));

        } else if(item.isSimilar(posoh(true))) {
            is = customOre(true);
            is.setAmount(3);
            inv.setItem(3, is);
            inv.setItem(11, is);
            inv.setItem(13, is);

            is = new ItemStack(Material.STICK);
            inv.setItem(12, is);
            inv.setItem(21, is);

        } else if(item.isSimilar(posoh(false))) {
            is = customOre(false);
            is.setAmount(3);
            inv.setItem(3, is);
            inv.setItem(11, is);
            inv.setItem(13, is);

            is = new ItemStack(Material.STICK);
            inv.setItem(12, is);
            inv.setItem(21, is);

        } else {
            switch (nm) {
                case "Обсидиановый динамит" -> {
                    is = new ItemStack(Material.OBSIDIAN);
                    inv.setItem(2, is);
                    inv.setItem(4, is);
                    inv.setItem(12, is);
                    inv.setItem(20, is);
                    inv.setItem(22, is);

                    is = new ItemStack(Material.TNT);
                    inv.setItem(3, is);
                    inv.setItem(11, is);
                    inv.setItem(13, is);
                    inv.setItem(21, is);
                }
                case "Водяной динамит" -> {
                    is = new ItemStack(Material.SPONGE);
                    inv.setItem(2, is);
                    inv.setItem(4, is);
                    inv.setItem(12, is);
                    inv.setItem(20, is);
                    inv.setItem(22, is);

                    is = new ItemStack(Material.TNT);
                    inv.setItem(3, is);
                    inv.setItem(11, is);
                    inv.setItem(13, is);
                    inv.setItem(21, is);
                }
                case "Пласт" -> {
                    is = new ItemStack(Material.OBSIDIAN);
                    inv.setItem(2, is);
                    inv.setItem(4, is);
                    inv.setItem(20, is);
                    inv.setItem(22, is);

                    is = new ItemStack(Material.QUARTZ);
                    inv.setItem(3, is);
                    inv.setItem(11, is);
                    inv.setItem(13, is);
                    inv.setItem(21, is);

                    inv.setItem(12, new ItemStack(Material.ENDER_PEARL));
                }

                case "Отталкивающая плита" -> {
                    is = new ItemStack(Material.POTION);
                    PotionMeta pm = (PotionMeta) is.getItemMeta();
                    pm.setBasePotionData(new PotionData(PotionType.JUMP, true, false));
                    is.setItemMeta(pm);
                    inv.setItem(12, is);

                    inv.setItem(21, new ItemStack(Material.HEAVY_WEIGHTED_PRESSURE_PLATE));
                }

                case "Кофе" -> {
                    inv.setItem(3, new ItemStack(Material.MILK_BUCKET));
                    inv.setItem(12, new ItemStack(Material.COCOA_BEANS));
                    inv.setItem(21, new ItemStack(Material.POTION));
                }

                case "Чай" -> {
                    inv.setItem(12, new ItemStack(Material.SEAGRASS));
                    inv.setItem(21, new ItemStack(Material.POTION));
                }

                case "Пиво" -> {
                    is = new ItemStack(Material.BREAD);
                    inv.setItem(11, is);
                    inv.setItem(13, is);

                    inv.setItem(12, new ItemStack(Material.SUGAR));
                    inv.setItem(21, new ItemStack(Material.POTION));
                }
            }
        }

        p.openInventory(inv);
    }
}
