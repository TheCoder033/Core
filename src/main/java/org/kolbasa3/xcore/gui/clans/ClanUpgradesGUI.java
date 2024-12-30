package org.kolbasa3.xcore.gui.clans;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.kolbasa3.xcore.utils.ClanUpgrade;
import org.kolbasa3.xcore.utils.ItemUtil;

import java.util.ArrayList;
import java.util.List;
import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class ClanUpgradesGUI {

    ClanUpgrade clanUpgrade = new ClanUpgrade();

    public ClanUpgradesGUI(Player p, String clan) {
        Inventory inv = Bukkit.createInventory(null, 27, Component.text("   §0⁘ Улучшения клана "+clan));

        ItemStack is = new ItemUtil(Material.LIME_STAINED_GLASS_PANE, "§f", null).build(null);
        inv.setItem(0, is);
        inv.setItem(9, is);
        inv.setItem(18, is);

        inv.setItem(8, is);
        inv.setItem(17, is);
        inv.setItem(26, is);

        List<String> lore = new ArrayList<>();

        int lvl = clanUpgrade.getLvl(clan, "players");
        lore.add("");
        lore.add("§fУровень: "+azure+lvl+"§7/14");
        lore.add("");
        if(lvl < 14) {
            lore.add("§fЦена: "+yellow+clanUpgrade.getPrice("players", lvl+1)+"⛁");
            lore.add("§7Нажмите для покупки.");
        }
        is = new ItemUtil(Material.PLAYER_HEAD, orange+"Участники", lore).build("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGRkMzk0NjJhMmM4YzIzMTFiZGE4ZGE5ZDA2NGQ0YTU1ODRmYjk5Nzc5ODliNjdmNGQ3Y2JiMTNlYzFhYiJ9fX0=");
        inv.setItem(12, is);

        lvl = clanUpgrade.getLvl(clan, "bal");
        lore.clear();
        lore.add("");
        lore.add("§fТекущий: "+azure+lvl+"§7/300000");
        lore.add("");
        if(lvl < 300000) {
            lore.add("§fЦена: "+yellow+clanUpgrade.getPrice("bal", lvl+15000)+"⛁");
            lore.add("§7Нажмите для покупки.");
        }
        is = new ItemUtil(Material.PLAYER_HEAD, orange+"Макс. баланс клана", lore).build("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjNjZWJlMjNkZjExYWE0ZDc1Y2YxNzI2MDA3ZjU4YTkzZTU0ZTg0Y2JlNDVhYzExZmIzZGM5OGFkMzIwOTgifX19");
        inv.setItem(14, is);

        p.openInventory(inv);
    }
}
