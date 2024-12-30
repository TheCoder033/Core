package org.kolbasa3.xcore.modules.smith;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.kolbasa3.xcore.utils.ItemUtil;

import static org.kolbasa3.xcore.utils.PluginUtil.orange;

public class SmithGUI {

    public SmithGUI(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, Component.text("               §0Кузнец"));

        ItemStack is = new ItemUtil(Material.BLACK_STAINED_GLASS_PANE, "§f", null).build(null);
        for (int i = 0; i < 27; i++) {
            if (i == 2) i = 9;
            else if (i == 11) i = 18;
            else if (i == 20) i = 7;
            else if (i == 9) i = 16;
            else if (i == 18) i = 25;
            inv.setItem(i, is);
        }
        inv.setItem(5, is);
        inv.setItem(14, is);
        inv.setItem(23, is);
        inv.setItem(6, is);
        inv.setItem(24, is);

        inv.setItem(15, new ItemUtil(Material.RED_STAINED_GLASS_PANE, orange+"Крафт не найден.", null).build(null));

        p.openInventory(inv);
    }
}
