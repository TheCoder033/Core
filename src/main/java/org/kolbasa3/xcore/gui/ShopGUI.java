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

import static org.kolbasa3.xcore.XCore.playerDB;
import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class ShopGUI {

    public ShopGUI(Player p) {
        Inventory inv = Bukkit.createInventory(null
                , 27, Component.text("              §0⁘ Магазин ⁘"));

        ItemStack is = new ItemUtil(Material.LIME_STAINED_GLASS_PANE, "§f", null).build(null);
        inv.setItem(0, is);
        inv.setItem(9, is);
        inv.setItem(18, is);

        inv.setItem(8, is);
        inv.setItem(17, is);
        inv.setItem(26, is);

        List<String> lore = new ArrayList<>();

        lore.add("");
        lore.add("§fЦена: "+yellow+"3000⛁");
        lore.add("§fБаланс: "+orange+playerDB.getMoney(p.getName())+"⛁");
        lore.add("");
        lore.add("§7Нажмите для покупки.");
        lore.add("");

        is = new ItemUtil(Material.EMERALD, orange+"§lИзумруд", lore).build(null);
        inv.setItem(11, is);

        lore.clear();
        lore.add("");
        lore.add("§fЦена: "+yellow+"200⛁");
        lore.add("§fБаланс: "+orange+playerDB.getMoney(p.getName())+"⛁");
        lore.add("");
        lore.add("§7Нажмите для покупки.");
        lore.add("");

        is = new ItemUtil(Material.OBSIDIAN, orange+"§lОбсидиан", lore).build(null);
        inv.setItem(13, is);

        lore.clear();
        lore.add("");
        lore.add("§fЦена: "+yellow+"1000⛁");
        lore.add("§fБаланс: "+orange+playerDB.getMoney(p.getName())+"⛁");
        lore.add("");
        lore.add("§7Нажмите для покупки.");
        lore.add("");

        is = new ItemUtil(Material.COMPASS, orange+"§lКомпас", lore).build(null);
        inv.setItem(15, is);

        p.openInventory(inv);
    }
}
