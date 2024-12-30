package org.kolbasa3.xcore.gui.ench;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.kolbasa3.xcore.utils.ItemUtil;

import java.util.ArrayList;
import java.util.List;

import static org.kolbasa3.xcore.utils.PluginUtil.orange;

public class EnchGUI {

    public EnchGUI(Player p) {
        Inventory inv = Bukkit.createInventory(null,
                45, Component.text("            §0⁘ Новые чары ⁘"));

        ItemStack is = new ItemUtil(Material.LIME_STAINED_GLASS_PANE, "§f", null).build(null);
        for(int i = 0; i < 5; i++) {
            inv.setItem((i * 9), is);
        }
        inv.setItem(8, is);
        inv.setItem(17, is);
        inv.setItem(26, is);
        inv.setItem(35, is);
        inv.setItem(44, is);

        List<String> lore = new ArrayList<>();

        lore.add("");
        lore.add("§fБур, Магнетизм, Опыт,");
        lore.add("§fПлавильня и др.");
        lore.add("");
        lore.add("§7Нажмите чтобы открыть.");

        is = new ItemUtil(Material.NETHERITE_PICKAXE, orange+"§lИнструменты", lore).build(null);
        is.addEnchantment(Enchantment.DURABILITY, 1);
        is.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        inv.setItem(20, is);

        lore.clear();
        lore.add("");
        lore.add("§fОтравление, Гнев Зевса,");
        lore.add("§fЗаражение и др.");
        lore.add("");
        lore.add("§7Нажмите чтобы открыть.");

        is = new ItemUtil(Material.NETHERITE_SWORD, orange+"§lОружие", lore).build(null);
        is.addEnchantment(Enchantment.DURABILITY, 1);
        is.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        inv.setItem(22, is);

        lore.clear();
        lore.add("");
        lore.add("§fЗащитная аура, Лаваход,");
        lore.add("§fГромоотвод и др.");
        lore.add("");
        lore.add("§7Нажмите чтобы открыть.");

        is = new ItemUtil(Material.NETHERITE_CHESTPLATE, orange+"§lБроня", lore).build(null);
        is.addEnchantment(Enchantment.DURABILITY, 1);
        is.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        inv.setItem(24, is);


        p.openInventory(inv);
    }
}
