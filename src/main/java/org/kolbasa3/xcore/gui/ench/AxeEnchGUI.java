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

public class AxeEnchGUI {

    public AxeEnchGUI(Player p) {
        Inventory inv = Bukkit.createInventory(null,
                45, Component.text("§0⁘ Новые чары на инструменты ⁘"));

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

        lore.add("§7(на кирку)");
        lore.add("");
        lore.add(hex("§fЛомает &#51CDFF3x3x3"));
        lore.add("§fкуб за раз.");
        lore.add("");
        inv.setItem(13, new ItemUtil(Material.PLAYER_HEAD, hex("&#0064D4&lБ&#1E7EEA&lу&#3B98FF&lр"), lore).build("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmRlNGFiMTI5ZTEzN2Y5ZjRjYmY3MDYwMzE4ZWUxNzQ4ZGMzOWRhOWI1ZDEyOWE4ZGEwZTYxNGUyMzM3NjkzIn19fQ=="));

        lore.clear();
        lore.add("§7(на кирку)");
        lore.add("");
        lore.add("§fПереплавляет");
        lore.add("§fдобытую руду.");
        lore.add("");
        inv.setItem(20, new ItemUtil(Material.PLAYER_HEAD, hex("§l&#0064D4&lП&#076BD9&lл&#0F71DF&lа&#1678E4&lв&#1E7EEA&lи&#2585EF&lл&#2C8BF4&lь&#3492FA&lн&#3B98FF&lя"), lore).build("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmRlNGFiMTI5ZTEzN2Y5ZjRjYmY3MDYwMzE4ZWUxNzQ4ZGMzOWRhOWI1ZDEyOWE4ZGEwZTYxNGUyMzM3NjkzIn19fQ=="));

        lore.clear();
        lore.add("§7(на кирку, топор и лопату)");
        lore.add("");
        lore.add("§fСкладывает добытое");
        lore.add("§fв инвентарь.");
        lore.add("");
        inv.setItem(24, new ItemUtil(Material.PLAYER_HEAD, hex("§l&#0064D4&lМ&#076BD9&lа&#0F71DF&lг&#1678E4&lн&#1E7EEA&lе&#2585EF&lт&#2C8BF4&lи&#3492FA&lз&#3B98FF&lм"), lore).build("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmRlNGFiMTI5ZTEzN2Y5ZjRjYmY3MDYwMzE4ZWUxNzQ4ZGMzOWRhOWI1ZDEyOWE4ZGEwZTYxNGUyMzM3NjkzIn19fQ=="));

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
