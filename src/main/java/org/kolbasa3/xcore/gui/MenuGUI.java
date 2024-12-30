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

public class MenuGUI {

    public MenuGUI(Player p) {
        Inventory inv = Bukkit.createInventory(null, 45, Component.text("               §0⁘ Меню ⁘"));

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
        lore.add("§fPvP-Арена, кейсы,");
        lore.add("§fказино, лесорубка");
        lore.add("");
        lore.add("§7Нажмите чтобы открыть.");
        inv.setItem(13, new ItemUtil(Material.PLAYER_HEAD, orange+"§lВарпы §7(/warps)", lore).build("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWZlMjRjNmY1MmRkYzA4MzU5Zjc5MjMwMWVjYTk2YWVjZjY2OTc5ZGM4OTljNDMyNDJkMjU4N2ViY2I3MGU2In19fQ=="));

        lore.clear();
        lore.add("");
        lore.add("§fПосохи, кирка тора,");
        lore.add("§fобсидиановый тнт и др.");
        lore.add("");
        lore.add("§7Нажмите чтобы открыть.");
        inv.setItem(21, new ItemUtil(Material.PLAYER_HEAD, orange+"§lНовые крафты §7(/crafts)", lore).build("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjNjZWJlMjNkZjExYWE0ZDc1Y2YxNzI2MDA3ZjU4YTkzZTU0ZTg0Y2JlNDVhYzExZmIzZGM5OGFkMzIwOTgifX19"));

        lore.clear();
        lore.add("");
        lore.add("§fБур, магнетизм,");
        lore.add("§fлаваход и др.");
        lore.add("");
        lore.add("§7Нажмите чтобы открыть.");
        inv.setItem(23, new ItemUtil(Material.PLAYER_HEAD, orange+"§lНовые чары §7(/ench)", lore).build("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjJmNzkwMTZjYWQ4NGQxYWUyMTYwOWM0ODEzNzgyNTk4ZTM4Nzk2MWJlMTNjMTU2ODI3NTJmMTI2ZGNlN2EifX19"));

        lore.clear();
        lore.add("");
        lore.add("§fПомогая проекту,");
        lore.add("§fвы получаете новые");
        lore.add("§fвозможности :D");
        lore.add("");
        lore.add("§7Нажмите чтобы открыть.");
        inv.setItem(31, new ItemUtil(Material.PLAYER_HEAD, orange+"§lПривилегии §7(/don)", lore).build("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWM5NmJlNzg4NmViN2RmNzU1MjVhMzYzZTVmNTQ5NjI2YzIxMzg4ZjBmZGE5ODhhNmU4YmY0ODdhNTMifX19"));

        p.openInventory(inv);
    }
}
