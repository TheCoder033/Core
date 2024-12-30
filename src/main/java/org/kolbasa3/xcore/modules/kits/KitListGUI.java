package org.kolbasa3.xcore.modules.kits;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.kolbasa3.xcore.utils.ItemUtil;

import java.util.ArrayList;

import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class KitListGUI {

    public KitListGUI(Player p) {
        Inventory inv = Bukkit.createInventory(null, 45, Component.text("          §0⁘ Донат-наборы ⁘"));
        ArrayList<String> lore = new ArrayList<>();

        ItemStack is = new ItemUtil(Material.LIME_STAINED_GLASS_PANE, "§f", null).build(null);
        for(int i = 0; i < 5; i++) {
            inv.setItem((i * 9), is);
        }
        inv.setItem(8, is);
        inv.setItem(17, is);
        inv.setItem(26, is);
        inv.setItem(35, is);
        inv.setItem(44, is);

        lore.add("§7Нажмите для просмотра.");
        inv.setItem(20, new ItemUtil(Material.RED_SHULKER_BOX, hex(orange+"Стартовый набор"), lore).build(null));

        inv.setItem(12, new ItemUtil(Material.PURPLE_SHULKER_BOX, hex("§fНабор &#5988FFʟ&#59A2FFɪ&#59BBFFᴛ&#59D5FFᴇ"), lore).build(null));

        inv.setItem(22, new ItemUtil(Material.MAGENTA_SHULKER_BOX, hex("§fНабор &#00AF7Bᴘ&#1ECA85ʀ&#3BE48Fᴇ&#59FF99ᴍ"), lore).build(null));

        inv.setItem(30, new ItemUtil(Material.PINK_SHULKER_BOX, hex("§fНабор &#FFE85Bɢ&#FFD65Cᴏ&#FFC55Cʟ&#FFB35Dᴅ"), lore).build(null));

        inv.setItem(14, new ItemUtil(Material.YELLOW_SHULKER_BOX, hex("§fНабор &#EE3600ᴀ&#F24B0Cᴅ&#F75F18ᴍ&#FB7423ɪ&#FF882Fɴ"), lore).build(null));

        inv.setItem(24, new ItemUtil(Material.GREEN_SHULKER_BOX, hex("§fНабор &#EE6C00ʙ&#F4821Aᴏ&#F99933ꜱ&#FFAF4Dꜱ"), lore).build(null));

        inv.setItem(32, new ItemUtil(Material.LIME_SHULKER_BOX, hex("§fНабор &#4FFF1Eᴜ&#6DFF2Bʟ&#8BFF39ᴛ&#A8FF46ʀ&#C6FF53ᴀ"), lore).build(null));

        p.openInventory(inv);
    }
}
