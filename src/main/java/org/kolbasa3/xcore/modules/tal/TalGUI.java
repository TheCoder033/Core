package org.kolbasa3.xcore.modules.tal;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.kolbasa3.xcore.utils.ItemUtil;

import java.util.List;

import static org.kolbasa3.xcore.XCore.playerDB;
import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class TalGUI {

    TalUtil talUtil = new TalUtil();

    public TalGUI(Player p) {
        Inventory inv = Bukkit.createInventory(null, 45, Component.text("             §0Талисманы"));
        List<String> lore;

        ItemStack is = new ItemUtil(Material.LIME_STAINED_GLASS_PANE, "§f", null).build(null);
        for(int i = 0; i < 5; i++) {
            inv.setItem((i * 9), is);
        }
        inv.setItem(8, is);
        inv.setItem(17, is);
        inv.setItem(26, is);
        inv.setItem(35, is);
        inv.setItem(44, is);

        is = talUtil.getTal(TalType.FEED);
        lore = is.getItemMeta().getLore();
        if(lore != null) {
            lore.removeIf(str -> str.contains("Прочность"));
            lore.add(hex("§fЦена: "+orange+"200К"));
            lore.add(hex("§fБаланс: "+yellow+playerDB.getCoin(p.getName())+"К"));
            lore.add("");
            lore.add(hex("§7Нажмите для покупки."));
        }
        is.setLore(lore);
        inv.setItem(12, is);

        is = talUtil.getTal(TalType.HEALTH);
        lore = is.getItemMeta().getLore();
        if(lore != null) {
            lore.removeIf(str -> str.contains("Прочность"));
            lore.add(hex("§fЦена: "+orange+"300К"));
            lore.add(hex("§fБаланс: "+yellow+playerDB.getCoin(p.getName())+"К"));
            lore.add("");
            lore.add(hex("§7Нажмите для покупки."));
        }
        is.setLore(lore);
        inv.setItem(20, is);

        is = talUtil.getTal(TalType.SPEED);
        lore = is.getItemMeta().getLore();
        if(lore != null) {
            lore.removeIf(str -> str.contains("Прочность"));
            lore.add(hex("§fЦена: "+orange+"400К"));
            lore.add(hex("§fБаланс: "+yellow+playerDB.getCoin(p.getName())+"К"));
            lore.add("");
            lore.add(hex("§7Нажмите для покупки."));
        }
        is.setLore(lore);
        inv.setItem(22, is);

        is = talUtil.getTal(TalType.LOOT);
        lore = is.getItemMeta().getLore();
        if(lore != null) {
            lore.removeIf(str -> str.contains("Прочность"));
            lore.add(hex("§fЦена: "+orange+"500К"));
            lore.add(hex("§fБаланс: "+yellow+playerDB.getCoin(p.getName())+"К"));
            lore.add("");
            lore.add(hex("§7Нажмите для покупки."));
        }
        is.setLore(lore);
        inv.setItem(30, is);

        is = talUtil.getTal(TalType.DURABILITY);
        lore = is.getItemMeta().getLore();
        if(lore != null) {
            lore.removeIf(str -> str.contains("Прочность"));
            lore.add(hex("§fЦена: "+orange+"800К"));
            lore.add(hex("§fБаланс: "+yellow+playerDB.getCoin(p.getName())+"К"));
            lore.add("");
            lore.add(hex("§7Нажмите для покупки."));
        }
        is.setLore(lore);
        inv.setItem(14, is);

        is = talUtil.getTal(TalType.DAMAGE);
        lore = is.getItemMeta().getLore();
        if(lore != null) {
            lore.removeIf(str -> str.contains("Прочность"));
            lore.add(hex("§fЦена: "+orange+"1000К"));
            lore.add(hex("§fБаланс: "+yellow+playerDB.getCoin(p.getName())+"К"));
            lore.add("");
            lore.add(hex("§7Нажмите для покупки."));
        }
        is.setLore(lore);
        inv.setItem(24, is);

        is = talUtil.getTal(TalType.SKY);
        lore = is.getItemMeta().getLore();
        if(lore != null) {
            lore.removeIf(str -> str.contains("Прочность"));
            lore.add(hex("§fЦена: "+orange+"1500К"));
            lore.add(hex("§fБаланс: "+yellow+playerDB.getCoin(p.getName())+"К"));
            lore.add("");
            lore.add(hex("§7Нажмите для покупки."));
        }
        is.setLore(lore);
        inv.setItem(32, is);

        p.openInventory(inv);
    }
}
