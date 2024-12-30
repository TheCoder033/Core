package org.kolbasa3.xcore.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.kolbasa3.xcore.utils.ItemUtil;
import org.kolbasa3.xcore.enums.WarpsType;

import java.util.ArrayList;
import java.util.List;

import static org.kolbasa3.xcore.XCore.warpsDB;
import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class WarpsGUI {

    public WarpsGUI(Player p, WarpsType warpsType) {
        String str = "         §0⁘ Варпы сервера ⁘";
        if(warpsType.equals(WarpsType.PLAYER)) str = "            §0⁘ Мои варпы ⁘";
        else if(warpsType.equals(WarpsType.OTHER)) str = "         §0⁘ Варпы игроков ⁘";

        Inventory inv = Bukkit.createInventory(null
                , 54, Component.text(str));

        ItemStack is = new ItemUtil(Material.LIME_STAINED_GLASS_PANE, "§f", null).build(null);
        for(int i = 0; i < 5; i++) {
            inv.setItem((i * 9), is);
        }
        inv.setItem(8, is);
        inv.setItem(17, is);
        inv.setItem(26, is);
        inv.setItem(35, is);
        inv.setItem(44, is);
        inv.setItem(45, is);
        inv.setItem(53, is);

        List<String> lore = new ArrayList<>();

        if(warpsType.equals(WarpsType.SERVER)) lore.add("§7(Выбрано)");
        inv.setItem(47, new ItemUtil(Material.PLAYER_HEAD, orange+"§lВарпы сервера", lore).build("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWY0YzIxZDE3YWQ2MzYzODdlYTNjNzM2YmZmNmFkZTg5NzMxN2UxMzc0Y2Q1ZDliMWMxNWU2ZTg5NTM0MzIifX19"));

        lore.clear();
        if(warpsType.equals(WarpsType.PLAYER)) lore.add("§7(Выбрано)");
        inv.setItem(49, new ItemUtil(Material.PLAYER_HEAD, orange+"§lМои варпы", lore).build("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWNiN2MyMWNjNDNkYzE3Njc4ZWU2ZjE2NTkxZmZhYWIxZjYzN2MzN2Y0ZjZiYmQ4Y2VhNDk3NDUxZDc2ZGI2ZCJ9fX0="));

        lore.clear();
        if(warpsType.equals(WarpsType.OTHER)) lore.add("§7(Выбрано)");
        inv.setItem(51, new ItemUtil(Material.PLAYER_HEAD, orange+"§lВарпы игроков", lore).build("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWMyNWZlZmFiZjM4ODc0MmZjZDNiYTQxZmRkMzViZDk2NzRmNGRhMGZhYmQ1NWJjNGZiODZmZWExYWE0NzEzIn19fQ=="));

        if(!warpsType.equals(WarpsType.SERVER)) {
            final int[] i = {10};
            warpsDB.getWarps().forEach((name, loc) -> {
                if (!warpsType.equals(WarpsType.PLAYER)
                        || warpsDB.getOwner(name).equals(p.getName())) {

                    if (i[0] >= 44) return;

                    if (i[0] == 17) i[0] = 19;
                    else if (i[0] == 26) i[0] = 28;
                    else if (i[0] == 35) i[0] = 37;

                    lore.clear();
                    lore.add("");
                    lore.add("§fРейтинг: "+getRating(warpsDB.getRating(name)));
                    if (!warpsType.equals(WarpsType.PLAYER))
                        lore.add("§fВладелец: " + azure + warpsDB.getOwner(name));
                    lore.add("§fПозиция: " + orange + loc.getBlockX() + "x " + loc.getBlockY() + "y " + loc.getBlockZ() + "z");

                    lore.add("");
                    if (warpsType.equals(WarpsType.PLAYER)) {
                        lore.add("§7ЛКМ - телепортация.");
                        lore.add("§7ПКМ - смена иконки.");
                    } else {
                        lore.add("§7Нажмите для");
                        lore.add("§7телепортации.");
                    }
                    lore.add("");

                    ItemStack item = new ItemUtil(warpsDB.getIcon(name), azure+name, lore).build(null);
                    item.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    item.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    item.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                    inv.setItem(i[0], item);
                    i[0]++;
                }
            });

        } else {
            lore.clear();
            lore.add("§7Нажмите для");
            lore.add("§7телепортации.");
            is = new ItemUtil(Material.DIAMOND_SWORD, azure+"PvP-Арена", lore).build(null);
            is.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            inv.setItem(10, is);
            inv.setItem(11, new ItemUtil(Material.ENDER_CHEST, azure+"Кейсы", lore).build(null));
        }

        p.openInventory(inv);
    }

    private String getRating(int value) {
        if(value <= 20) {
            return yellow+"★§7★★★★";
        } else if(value <= 40) {
            return yellow+"★★§7★★★";
        } else if(value <= 60) {
            return yellow+"★★★§7★★";
        } else if(value <= 80) {
            return yellow+"★★★★§7★";
        } else {
            return yellow+"★★★★★";
        }
    }
}
