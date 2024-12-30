package org.kolbasa3.xcore.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.kolbasa3.xcore.utils.ItemUtil;

import java.util.ArrayList;
import java.util.List;

import static org.kolbasa3.xcore.XCore.homesDB;
import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class HomesGUI {
    
    public HomesGUI(Player p) {
        Inventory inv = Bukkit.createInventory(null
                , 27, Component.text("            §0⁘ Точки дома ⁘"));

        ItemStack is = new ItemUtil(Material.LIME_STAINED_GLASS_PANE, "§f", null).build(null);
        inv.setItem(0, is);
        inv.setItem(9, is);
        inv.setItem(18, is);

        inv.setItem(8, is);
        inv.setItem(17, is);
        inv.setItem(26, is);

        final int[] i = {10};
        List<String> lore = new ArrayList<>();
        homesDB.getHomes(p.getName()).forEach((name, loc) -> {
            lore.add("");
            lore.add("§fПозиция: "+azure + loc.getBlockX() + "x " + loc.getBlockY() + "y " + loc.getBlockZ() + "z");
            lore.add("");
            lore.add("§7ЛКМ - телепортация.");
            lore.add("§7ПКМ - смена иконки.");
            lore.add("");
            ItemStack item = new ItemUtil(homesDB.getIcon(p.getName(), name), orange+"§l"+name, lore).build(null);
            item.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            item.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            inv.setItem(i[0], item);
            i[0]++;
        });

        p.openInventory(inv);
    }
}
