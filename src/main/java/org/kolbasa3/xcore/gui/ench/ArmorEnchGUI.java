package org.kolbasa3.xcore.gui.ench;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.kolbasa3.xcore.utils.ItemUtil;

import java.util.ArrayList;
import java.util.List;

import static org.kolbasa3.xcore.utils.PluginUtil.hex;

public class ArmorEnchGUI {

    // #0064D4 #3B98FF

    public ArmorEnchGUI(Player p) {
        Inventory inv = Bukkit.createInventory(null,
                45, Component.text("§0⁘ Новые чары на броню ⁘"));

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

        lore.add("§7(на нагрудник)");
        lore.add("");
        lore.add("§fБлокирует урон от врагов");
        lore.add("§fпри малом здоровье");
        lore.add(hex("§fс шансом &#51CDFF30%"));
        lore.add("");
        inv.setItem(20, new ItemUtil(Material.PLAYER_HEAD, hex("§l&#0064D4&lЗ&#0568D8&lа&#0A6DDB&lщ&#0F71DF&lи&#1475E2&lт&#197AE6&lн&#1E7EEA&lа&#2282ED&lя &#2C8BF4&lа&#318FF8&lу&#3694FB&lр&#3B98FF&lа"), lore).build("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmRlNGFiMTI5ZTEzN2Y5ZjRjYmY3MDYwMzE4ZWUxNzQ4ZGMzOWRhOWI1ZDEyOWE4ZGEwZTYxNGUyMzM3NjkzIn19fQ=="));

        lore.clear();
        lore.add("§7(на ботинки)");
        lore.add("");
        lore.add(hex("§fСоздаёт &#51CDFFобсидиан"));
        lore.add("§fпри хождении по лаве.");
        lore.add("");
        inv.setItem(22, new ItemUtil(Material.PLAYER_HEAD, hex("§l&#0064D4&lЛ&#0A6DDB&lа&#1475E2&lв&#1E7EEA&lа&#2787F1&lх&#318FF8&lо&#3B98FF&lд"), lore).build("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmRlNGFiMTI5ZTEzN2Y5ZjRjYmY3MDYwMzE4ZWUxNzQ4ZGMzOWRhOWI1ZDEyOWE4ZGEwZTYxNGUyMzM3NjkzIn19fQ=="));

        lore.clear();
        lore.add("§7(на шлем)");
        lore.add("");
        lore.add("§fЗащищает от молний");
        lore.add(hex("§fс шансом &#51CDFF30%"));
        lore.add("");
        inv.setItem(24, new ItemUtil(Material.PLAYER_HEAD, hex("§l&#0064D4&lГ&#076AD9&lр&#0D70DE&lо&#1475E2&lм&#1A7BE7&lо&#2181EC&lо&#2787F1&lт&#2E8CF5&lв&#3492FA&lо&#3B98FF&lд"), lore).build("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmRlNGFiMTI5ZTEzN2Y5ZjRjYmY3MDYwMzE4ZWUxNzQ4ZGMzOWRhOWI1ZDEyOWE4ZGEwZTYxNGUyMzM3NjkzIn19fQ=="));


        p.openInventory(inv);
    }
}
