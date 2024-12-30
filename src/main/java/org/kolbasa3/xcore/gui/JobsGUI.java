package org.kolbasa3.xcore.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.kolbasa3.xcore.utils.ItemUtil;

import java.util.ArrayList;
import java.util.List;

import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class JobsGUI {

    public JobsGUI(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, Component.text("              §0⁘ Работы ⁘"));

        ItemStack is = new ItemUtil(Material.LIME_STAINED_GLASS_PANE, "§f", null).build(null);
        inv.setItem(0, is);
        inv.setItem(9, is);
        inv.setItem(18, is);

        inv.setItem(8, is);
        inv.setItem(17, is);
        inv.setItem(26, is);

        List<String> lore = new ArrayList<>();

        lore.add("§7/warp wood");
        lore.add("");
        lore.add("§fРубите деревья");
        lore.add("§fи зарабатывайте деньги.");
        lore.add("");
        lore.add("§7Нажмите для телепортации.");
        inv.setItem(12, new ItemUtil(Material.PLAYER_HEAD, orange+"\uD83E\uDE93 Лесорубка \uD83E\uDE93", lore).build("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjBjZDEzMjIzYThkOWMxNzNjZWRjZTZjNGJlYmViYTA2YTI0YTFiYTI3NWRkM2ViNWM3OTMzZjlhNzRiYTAxMSJ9fX0="));

        lore.clear();
        lore.add("§7/warp fish");
        lore.add("");
        lore.add("§fРыбу можно продать");
        lore.add("§fу скупщика §7» "+azure+"/buyer");
        lore.add("");
        lore.add("§7Нажмите для телепортации.");
        inv.setItem(14, new ItemUtil(Material.PLAYER_HEAD, orange+"\uD83C\uDFA3 Рыбалка \uD83C\uDFA3", lore).build("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDBjZDcxZmJiYmJiNjZjN2JhZjc4ODFmNDE1YzY0ZmE4NGY2NTA0OTU4YTU3Y2NkYjg1ODkyNTI2NDdlYSJ9fX0="));

        p.openInventory(inv);
    }
}
