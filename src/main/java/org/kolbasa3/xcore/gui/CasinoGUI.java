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

public class CasinoGUI {

    public CasinoGUI(Player p) {
        Inventory inv = Bukkit.createInventory(null, 54, Component.text("§0Казино » Выберите слот"));

        List<String> lore = new ArrayList<>();

        lore.add("§7Нажмите чтобы выбрать.");
        ItemStack is = new ItemUtil(Material.BLACK_STAINED_GLASS_PANE, "§f", lore).build(null);

        for(int i = 0; i < 54; i++) {
            inv.setItem(i, is);
        }

        p.openInventory(inv);
    }
}
