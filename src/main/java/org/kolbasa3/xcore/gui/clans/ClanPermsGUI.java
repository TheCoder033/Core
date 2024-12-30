package org.kolbasa3.xcore.gui.clans;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.kolbasa3.xcore.utils.ItemUtil;

import java.util.ArrayList;
import java.util.List;

import static org.kolbasa3.xcore.XCore.clanDB;
import static org.kolbasa3.xcore.utils.PluginUtil.*;
import static org.kolbasa3.xcore.utils.PluginUtil.orange;

public class ClanPermsGUI {

    public ClanPermsGUI(Player p, String clan, String target) {
        Inventory inv = Bukkit.createInventory(null, 27, Component.text("   §0⁘ Права участника "+target));

        ItemStack is = new ItemUtil(Material.LIME_STAINED_GLASS_PANE, "§f", null).build(null);
        inv.setItem(0, is);
        inv.setItem(9, is);
        inv.setItem(18, is);

        inv.setItem(8, is);
        inv.setItem(17, is);
        inv.setItem(26, is);

        List<String> lore = new ArrayList<>();

        List<String> perms = clanDB.getPerms(clan, target);

        String st = red+"Запрещено";
        String data = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjE4NTZjN2IzNzhkMzUwMjYyMTQzODQzZDFmOWZiYjIxOTExYTcxOTgzYmE3YjM5YTRkNGJhNWI2NmJlZGM2In19fQ==";
        if(!perms.contains("kick")) {
            st = lime+"Разрешено";
            data = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGI1OTljNjE4ZTkxNGMyNWEzN2Q2OWY1NDFhMjJiZWJiZjc1MTYxNTI2Mzc1NmYyNTYxZmFiNGNmYTM5ZSJ9fX0=";
        }
        lore.add("");
        lore.add("§fСтатус: "+st);
        lore.add("");
        is = new ItemUtil(Material.PLAYER_HEAD, orange+"Кикать участников", lore).build(data);
        inv.setItem(11, is);

        st = red+"Запрещено";
        data = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjE4NTZjN2IzNzhkMzUwMjYyMTQzODQzZDFmOWZiYjIxOTExYTcxOTgzYmE3YjM5YTRkNGJhNWI2NmJlZGM2In19fQ==";
        if(!perms.contains("invite")) {
            st = lime+"Разрешено";
            data = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGI1OTljNjE4ZTkxNGMyNWEzN2Q2OWY1NDFhMjJiZWJiZjc1MTYxNTI2Mzc1NmYyNTYxZmFiNGNmYTM5ZSJ9fX0=";
        }
        lore.clear();
        lore.add("");
        lore.add("§fСтатус: "+st);
        lore.add("");
        is = new ItemUtil(Material.PLAYER_HEAD, orange+"Приглашать в клан", lore).build(data);
        inv.setItem(13, is);

        st = red+"Запрещено";
        data = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjE4NTZjN2IzNzhkMzUwMjYyMTQzODQzZDFmOWZiYjIxOTExYTcxOTgzYmE3YjM5YTRkNGJhNWI2NmJlZGM2In19fQ==";
        if(!perms.contains("accept")) {
            st = lime+"Разрешено";
            data = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGI1OTljNjE4ZTkxNGMyNWEzN2Q2OWY1NDFhMjJiZWJiZjc1MTYxNTI2Mzc1NmYyNTYxZmFiNGNmYTM5ZSJ9fX0=";
        }
        lore.clear();
        lore.add("");
        lore.add("§fСтатус: "+st);
        lore.add("");
        is = new ItemUtil(Material.PLAYER_HEAD, orange+"Принимать запросы в клан", lore).build(data);
        inv.setItem(15, is);

        p.openInventory(inv);
    }
}
