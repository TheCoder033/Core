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

public class SwordEnchGUI {

    public SwordEnchGUI(Player p) {
        Inventory inv = Bukkit.createInventory(null,
                45, Component.text("§0⁘ Новые чары на оружие ⁘"));

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

        lore.add("§7(на меч)");
        lore.add("");
        lore.add("§fОтравляет врагов");
        lore.add(hex("§fс шансом &#51CDFF50%"));
        lore.add("");
        inv.setItem(13, new ItemUtil(Material.PLAYER_HEAD, hex("§l&#0064D4&lО&#076AD9&lт&#0D70DE&lр&#1475E2&lа&#1A7BE7&lв&#2181EC&lл&#2787F1&lе&#2E8CF5&lн&#3492FA&lи&#3B98FF&lе"), lore).build("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmRlNGFiMTI5ZTEzN2Y5ZjRjYmY3MDYwMzE4ZWUxNzQ4ZGMzOWRhOWI1ZDEyOWE4ZGEwZTYxNGUyMzM3NjkzIn19fQ=="));

        lore.clear();
        lore.add("§7(на меч)");
        lore.add("");
        lore.add("§fБьёт молнией врагов");
        lore.add(hex("§fс шансом &#51CDFF30%"));
        lore.add("");
        inv.setItem(20, new ItemUtil(Material.PLAYER_HEAD, hex("§l&#0064D4&lГ&#076AD9&lн&#0D70DE&lе&#1475E2&lв &#2181EC&lЗ&#2787F1&lе&#2E8CF5&lв&#3492FA&lс&#3B98FF&lа"), lore).build("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmRlNGFiMTI5ZTEzN2Y5ZjRjYmY3MDYwMzE4ZWUxNzQ4ZGMzOWRhOWI1ZDEyOWE4ZGEwZTYxNGUyMzM3NjkzIn19fQ=="));

        lore.clear();
        lore.add("§7(на меч)");
        lore.add("");
        lore.add("§fПередаётся");
        lore.add("§fмежду ближними");
        lore.add("§fврагами");
        lore.add(hex("§fс шансом &#51CDFF50%"));
        lore.add("");
        lore.add("§7(Вызывает слепоту и тошноту)");
        lore.add("");
        inv.setItem(24, new ItemUtil(Material.PLAYER_HEAD, hex("§l&#0064D4&lП&#076BD9&lо&#0F71DF&lр&#1678E4&lа&#1E7EEA&lж&#2585EF&lе&#2C8BF4&lн&#3492FA&lи&#3B98FF&lе"), lore).build("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmRlNGFiMTI5ZTEzN2Y5ZjRjYmY3MDYwMzE4ZWUxNzQ4ZGMzOWRhOWI1ZDEyOWE4ZGEwZTYxNGUyMzM3NjkzIn19fQ=="));

        lore.clear();
        lore.add("§7(на кирку и меч)");
        lore.add("");
        lore.add("§fУвеличивает");
        lore.add("§fполученный");
        lore.add("§fопыт.");
        lore.add("");
        inv.setItem(31, new ItemUtil(Material.PLAYER_HEAD, hex("§l&#0064D4&lО&#1475E2&lп&#2787F1&lы&#3B98FF&lт"), lore).build("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmRlNGFiMTI5ZTEzN2Y5ZjRjYmY3MDYwMzE4ZWUxNzQ4ZGMzOWRhOWI1ZDEyOWE4ZGEwZTYxNGUyMzM3NjkzIn19fQ=="));

        p.openInventory(inv);
    }
}
