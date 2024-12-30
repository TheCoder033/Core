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

public class RegionUpgradesGUI {

    RegionUpgrade regionUpgrade = new RegionUpgrade();

    public RegionUpgradesGUI(Player p,  String rgName) {
        Inventory inv = Bukkit.createInventory(null, 27, Component.text("   §0⁘ Улучшения региона "+rgName));

        ItemStack is = new ItemUtil(Material.LIME_STAINED_GLASS_PANE, "§f", null).build(null);
        inv.setItem(0, is);
        inv.setItem(9, is);
        inv.setItem(18, is);

        inv.setItem(8, is);
        inv.setItem(17, is);
        inv.setItem(26, is);

        List<String> lore = new ArrayList<>();

        int lvl = regionUpgrade.getLvl(p.getName(), rgName, "size");
        lore.add("");
        lore.add("§fУровень: "+azure+lvl+"§7/16");
        lore.add("");
        if(lvl < 16) {
            lore.add("§fЦена: "+yellow+regionUpgrade.getPrice("size", lvl+1)+"⛁");
            lore.add("§7Нажмите для покупки.");
        }
        is = new ItemUtil(Material.PLAYER_HEAD, orange+"Размер", lore).build("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2NhNGQyMThkZjlkMzJjZDQ3ZDljMWQyOTQ4NzcxMjJiZTU5MTliNDE4YTZjYzNkMDg5MTYyYjEzM2YyZGIifX19");
        inv.setItem(11, is);

        lvl = regionUpgrade.getLvl(p.getName(), rgName, "players");
        lore.clear();
        lore.add("");
        lore.add("§fУровень: "+azure+lvl+"§7/14");
        lore.add("");
        if(lvl < 14) {
            lore.add("§fЦена: "+yellow+regionUpgrade.getPrice("players", lvl+1)+"⛁");
            lore.add("§7Нажмите для покупки.");
        }
        is = new ItemUtil(Material.PLAYER_HEAD, orange+"Участники", lore).build("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGRkMzk0NjJhMmM4YzIzMTFiZGE4ZGE5ZDA2NGQ0YTU1ODRmYjk5Nzc5ODliNjdmNGQ3Y2JiMTNlYzFhYiJ9fX0=");
        inv.setItem(13, is);

        boolean radarBuyed = regionDB.getList(p.getName(), rgName, false).contains("radar");
        String st = red+"Не куплен";
        if(radarBuyed) st = lime+"Куплен";
        lore.clear();
        lore.add("");
        lore.add("§fСтатус: "+st);
        lore.add("");
        if(!radarBuyed) {
            lore.add("§fЦена: "+yellow+regionUpgrade.getPrice("radar", 0)+"⛁");
            lore.add("§7Нажмите для покупки.");
        }
        is = new ItemUtil(Material.PLAYER_HEAD, orange+"Радар", lore).build("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2I3YzliNmEyM2YyMWNjYTJiMzYyYjg1YjM2ZGVjZTNkODM4OWUzNjMwMTRkZWZlNWI5MmZmNmVlNjRmMWFlIn19fQ==");
        inv.setItem(15, is);

        p.openInventory(inv);
    }
}
