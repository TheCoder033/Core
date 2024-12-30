package org.kolbasa3.xcore.modules.kits;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.kolbasa3.xcore.utils.ItemUtil;

public class KitsGUI {

    Kits kits = new Kits();

    public KitsGUI(Player p, String name) {
        String invname, space = "            ";
        if(name.length() == 5) space = "           ";
        invname = space+"§0⁘ Набор "+name+" ⁘";
        if(name.equals("Start")) invname = "        §0⁘ Стартовый набор ⁘";

        Inventory inv = Bukkit.createInventory(null, 45, invname);

        ItemStack is = new ItemUtil(Material.LIME_STAINED_GLASS_PANE, "§f", null).build(null);
        for(int i = 0; i < 5; i++) {
            inv.setItem((i * 9), is);
        }
        inv.setItem(8, is);
        inv.setItem(17, is);
        inv.setItem(26, is);
        inv.setItem(35, is);
        inv.setItem(44, is);

        final int[] i = {10};
        kits.getKit(name.toLowerCase()).forEach(item -> {
            if(i[0] == 17) i[0] = 19;
            else if(i[0] == 26) i[0] = 28;
            inv.setItem(i[0], item);
            i[0]++;
        });

        p.openInventory(inv);
    }
}
