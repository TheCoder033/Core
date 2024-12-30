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

public class ClanTopGUI {

    public ClanTopGUI(Player p) {
        Inventory inv = Bukkit.createInventory(null, 54, Component.text("            §0⁘ Топ кланы ⁘"));

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

        clanDB.getClans(null).forEach(clan -> {
            boolean canJoin = Bukkit.getPlayer(clanDB.getOwner(clan)) != null;

            if(!canJoin) {
                for (String pl : clanDB.getList(clan, true)) {
                    if (Bukkit.getPlayer(pl) != null) {
                        canJoin = true;
                        break;
                    }
                }
            }

            lore.clear();
            lore.add("");
            lore.add("§fУчастников: "+azure+clanDB.getList(clan, true).size());
            if(canJoin && clanDB.getClans(p.getName()).isEmpty()) lore.add("§7Нажмите чтобы вступить.");
            lore.add("");
            inv.addItem(new ItemUtil(clanDB.getIcon(clan), orange+clan, lore).build(null));
        });

        p.openInventory(inv);
    }
}
