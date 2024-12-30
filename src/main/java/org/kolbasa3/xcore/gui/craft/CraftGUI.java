package org.kolbasa3.xcore.gui.craft;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.kolbasa3.xcore.XCore;
import org.kolbasa3.xcore.utils.ItemUtil;
import org.kolbasa3.xcore.enums.Items;

import java.util.ArrayList;

import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class CraftGUI {
    
    public CraftGUI(Player p) {
        Inventory inv = Bukkit.createInventory(null, 45, Component.text("          §0⁘ Новые крафты ⁘"));
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

        is = new ItemUtil(Material.DIAMOND_PICKAXE, blue+"Кирка Тора", null).build(null);
        is.addEnchantment(Enchantment.DIG_SPEED, 1);
        is.addEnchantment(Enchantment.DURABILITY, 2);
        inv.setItem(10, is);

        is = new ItemUtil(Material.DIAMOND_AXE, blue+"Топор Тора", null).build(null);
        is.addEnchantment(Enchantment.DIG_SPEED, 1);
        is.addEnchantment(Enchantment.DURABILITY, 2);
        inv.setItem(11, is);

        is = new ItemUtil(Material.DIAMOND_SWORD, blue+"Меч Тора", null).build(null);
        is.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        is.addEnchantment(Enchantment.DURABILITY, 2);
        inv.setItem(12, is);

        inv.setItem(13, new ItemUtil(Material.ENCHANTED_GOLDEN_APPLE, orange+"Зачарованное золотое яблоко", null).build(null));

        NamespacedKey key = new NamespacedKey(XCore.getInstance(), "custom-item");

        lore.add("");
        lore.add(hex("§fВзрывает обсидиан"));
        lore.add(hex("§fс шансом "+orange+"50%"));
        is = new ItemUtil(Material.TNT, hex("&#A800B5О&#A506B9б&#A20CBDс&#A012C1и&#9D18C5д&#9A1DC8и&#9723CCа&#9429D0н&#922FD4о&#8F35D8в&#8C3BDCы&#8941E0й &#844DE8д&#8153ECи&#7E58EFн&#7B5EF3а&#7964F7м&#766AFBи&#7370FFт"), lore).build(null);
        is.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        is.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        ItemMeta meta = is.getItemMeta();
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "obsidian-tnt");
        is.setItemMeta(meta);
        inv.setItem(14, is);

        inv.setItem(16, new ItemUtil(Material.EXPERIENCE_BOTTLE, orange+"Пузырёк опыта", null).build(null));

        lore.clear();
        lore.add("");
        lore.add(hex("§fВзрывает через воду"));
        lore.add(hex("§fс шансом "+orange+"50%"));
        is = new ItemUtil(Material.TNT, hex("&#00B1D1В&#08ACD4о&#10A8D8д&#19A3DBя&#219EDEн&#299AE1о&#3195E5й &#428CEBд&#4A87EFи&#5283F2н&#5A7EF5а&#6379F8м&#6B75FCи&#7370FFт"), lore).build(null);
        is.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        is.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta = is.getItemMeta();
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "water-tnt");
        is.setItemMeta(meta);
        inv.setItem(15, is);

        inv.setItem(19, Items.PLAST.get(null));

        inv.setItem(20, posoh(true));
        inv.setItem(21, posoh(false));

        inv.setItem(22, Items.PLATE.get(null));

        inv.setItem(23, Items.COFFEE.get(null));
        inv.setItem(24, Items.TEA.get(null));
        inv.setItem(25, Items.BEER.get(null));

        p.openInventory(inv);
    }
}
