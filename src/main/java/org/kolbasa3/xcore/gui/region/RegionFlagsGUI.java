package org.kolbasa3.xcore.gui.region;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.kolbasa3.xcore.utils.ItemUtil;
import org.kolbasa3.xcore.utils.region.RegionUpgrade;

import java.util.ArrayList;
import java.util.List;

import static org.kolbasa3.xcore.XCore.regionDB;
import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class RegionFlagsGUI {

    RegionUpgrade regionUpgrade = new RegionUpgrade();

    public RegionFlagsGUI(Player p, String rgName) {
        Inventory inv = Bukkit.createInventory(null, 27, Component.text("   §0⁘ Флаги региона "+rgName));

        ItemStack is = new ItemUtil(Material.LIME_STAINED_GLASS_PANE, "§f", null).build(null);
        inv.setItem(0, is);
        inv.setItem(9, is);
        inv.setItem(18, is);

        inv.setItem(8, is);
        inv.setItem(17, is);
        inv.setItem(26, is);

        List<String> lore = new ArrayList<>();
        List<String> upgrades = regionDB.getList(p.getName(), rgName, false);

        String st = red+"Не куплено";
        String data = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjE4NTZjN2IzNzhkMzUwMjYyMTQzODQzZDFmOWZiYjIxOTExYTcxOTgzYmE3YjM5YTRkNGJhNWI2NmJlZGM2In19fQ==";
        boolean buyed = upgrades.contains("pvp");
        if(buyed) {
            if(upgrades.contains("pvpOff")) st = red+"Выключено";
            else {
                st = lime+"Включено";
                data = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGI1OTljNjE4ZTkxNGMyNWEzN2Q2OWY1NDFhMjJiZWJiZjc1MTYxNTI2Mzc1NmYyNTYxZmFiNGNmYTM5ZSJ9fX0=";
            }
        }
        lore.add("");
        lore.add("§fСтатус: "+st);
        lore.add("");
        if(!buyed) {
            lore.add("§fЦена: "+yellow+regionUpgrade.getPrice("pvp", 0)+"⛁");
            lore.add("§7Нажмите для покупки.");
        }
        is = new ItemUtil(Material.PLAYER_HEAD, orange+"⚔ PvP между участниками ⚔", lore).build(data);
        inv.setItem(11, is);

        st = red+"Не куплено";
        data = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjE4NTZjN2IzNzhkMzUwMjYyMTQzODQzZDFmOWZiYjIxOTExYTcxOTgzYmE3YjM5YTRkNGJhNWI2NmJlZGM2In19fQ==";
        buyed = upgrades.contains("build");
        if(buyed) {
            if(upgrades.contains("buildOff")) st = red+"Выключено";
            else {
                st = lime+"Включено";
                data = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGI1OTljNjE4ZTkxNGMyNWEzN2Q2OWY1NDFhMjJiZWJiZjc1MTYxNTI2Mzc1NmYyNTYxZmFiNGNmYTM5ZSJ9fX0=";
            }
        }
        lore.clear();
        lore.add("");
        lore.add("§fСтатус: "+st);
        lore.add("");
        if(!buyed) {
            lore.add("§fЦена: "+yellow+regionUpgrade.getPrice("build", 0)+"⛁");
            lore.add("§7Нажмите для покупки.");
        }
        is = new ItemUtil(Material.PLAYER_HEAD, orange+"▩ Строительство ▩", lore).build(data);
        inv.setItem(13, is);

        st = red+"Не куплено";
        data = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjE4NTZjN2IzNzhkMzUwMjYyMTQzODQzZDFmOWZiYjIxOTExYTcxOTgzYmE3YjM5YTRkNGJhNWI2NmJlZGM2In19fQ==";
        buyed = upgrades.contains("destroy");
        if(buyed) {
            if(upgrades.contains("destroyOff")) st = red+"Выключено";
            else {
                st = lime+"Включено";
                data = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGI1OTljNjE4ZTkxNGMyNWEzN2Q2OWY1NDFhMjJiZWJiZjc1MTYxNTI2Mzc1NmYyNTYxZmFiNGNmYTM5ZSJ9fX0=";
            }
        }
        lore.clear();
        lore.add("");
        lore.add("§fСтатус: "+st);
        lore.add("");
        if(!buyed) {
            lore.add("§fЦена: "+yellow+regionUpgrade.getPrice("destroy", 0)+"⛁");
            lore.add("§7Нажмите для покупки.");
        }
        is = new ItemUtil(Material.PLAYER_HEAD, orange+"⛏ Ломать блоки ⛏", lore).build(data);
        inv.setItem(15, is);

        p.openInventory(inv);
    }
}
