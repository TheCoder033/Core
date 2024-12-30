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
import java.util.concurrent.atomic.AtomicReference;

import static org.kolbasa3.xcore.XCore.clanDB;
import static org.kolbasa3.xcore.utils.PluginUtil.*;
import static org.kolbasa3.xcore.utils.PluginUtil.orange;

public class ClanPlayersGUI {

    public ClanPlayersGUI(Player p, String clan) {
        Inventory inv = Bukkit.createInventory(null, 54, Component.text("   §0⁘ Участники клана "+clan));

        ItemStack is = new ItemUtil(Material.LIME_STAINED_GLASS_PANE, "§f", null).build(null);
        inv.setItem(0, is);
        inv.setItem(9, is);
        inv.setItem(18, is);
        inv.setItem(27, is);
        inv.setItem(36, is);
        inv.setItem(45, is);

        inv.setItem(8, is);
        inv.setItem(17, is);
        inv.setItem(26, is);
        inv.setItem(35, is);
        inv.setItem(44, is);
        inv.setItem(53, is);

        List<String> lore = new ArrayList<>();

        String owner = clanDB.getOwner(clan);
        AtomicReference<String> st = new AtomicReference<>(red + "Оффлайн");
        if(Bukkit.getPlayer(owner) != null) st.set(lime + "Онлайн");
        lore.add("");
        lore.add("§fСтатус: "+st);
        lore.add("");
        inv.setItem(1, new ItemUtil(Material.PLAYER_HEAD, orange+owner+" §7(Лидер)", lore).build(""));

        clanDB.getList(clan, true).forEach(pl -> {
            String t = pl.split(":")[0];
            st.set(red + "Оффлайн");
            if(Bukkit.getPlayer(t) != null) st.set(lime + "Онлайн");
            lore.clear();
            lore.add("");
            lore.add("§fСтатус: "+st);
            lore.add("");
            if(p.getName().equals(owner)) {
                lore.add("§7ЛКМ » кикнуть из клана.");
                lore.add("§7ПКМ » сменить права.");
                lore.add("");
            } else if(clanDB.getPerms(clan, t).contains("kick")) {
                lore.add("§7ЛКМ » кикнуть из клана.");
                lore.add("");
            }
            inv.addItem(new ItemUtil(Material.PLAYER_HEAD, orange+t, lore).build(""));
        });

        p.openInventory(inv);
    }
}
