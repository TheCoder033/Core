package org.kolbasa3.xcore.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.kolbasa3.xcore.utils.ItemUtil;

import java.util.ArrayList;
import java.util.List;

import static org.kolbasa3.xcore.utils.PluginUtil.orange;

public class ExpGUI {

    public ExpGUI(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, Component.text("                §0⁘ Опыт ⁘"));

        ItemStack is = new ItemUtil(Material.LIME_STAINED_GLASS_PANE, "§f", null).build(null);
        inv.setItem(0, is);
        inv.setItem(9, is);
        inv.setItem(18, is);

        inv.setItem(8, is);
        inv.setItem(17, is);
        inv.setItem(26, is);

        List<String> lore = new ArrayList<>();

        lore.add("§7Нажмите чтобы получить.");
        is = new ItemUtil(Material.EXPERIENCE_BOTTLE, orange+"§l15 "+orange+"уровень", lore).build(null);
        is.setAmount(15);
        inv.setItem(11, is);

        is = new ItemUtil(Material.EXPERIENCE_BOTTLE, orange+"§l20 "+orange+"уровень", lore).build(null);
        is.setAmount(20);
        inv.setItem(13, is);

        is = new ItemUtil(Material.EXPERIENCE_BOTTLE, orange+"§l30 "+orange+"уровень", lore).build(null);
        is.setAmount(30);
        inv.setItem(15, is);

        p.openInventory(inv);
    }
}
