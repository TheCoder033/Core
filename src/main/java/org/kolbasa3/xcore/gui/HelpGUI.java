package org.kolbasa3.xcore.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.kolbasa3.xcore.modules.bosses.BossType;
import org.kolbasa3.xcore.utils.ItemUtil;

import java.util.ArrayList;
import java.util.List;

import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class HelpGUI {

    public HelpGUI(Player p) {
        Inventory inv = Bukkit.createInventory(null
                , 27, Component.text("       §0⁘ Помощь по серверу ⁘"));

        ItemStack is = new ItemUtil(Material.LIME_STAINED_GLASS_PANE, "§f", null).build(null);
        inv.setItem(0, is);
        inv.setItem(9, is);
        inv.setItem(18, is);

        inv.setItem(8, is);
        inv.setItem(17, is);
        inv.setItem(26, is);

        List<String> lore = new ArrayList<>();

        lore.add("§7(оружия)");
        lore.add("");
        lore.add("§fСтреляют частицами,");
        lore.add("§fнаносят большой урон.");
        lore.add("");
        lore.add("§fКрафт §7» "+azure+"/crafts§f, "+azure+"/smith");
        lore.add("");

        is = new ItemUtil(Material.TRIDENT, orange+"\uD83D\uDD31 Посохи \uD83D\uDD31", lore).build(null);
        is.addEnchantment(Enchantment.DURABILITY, 1);
        is.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        inv.setItem(10, is);

        lore.clear();
        lore.add("§7(ловушки)");
        lore.add("");
        lore.add("§fДля создания,");
        lore.add("§fнужно кликнуть "+azure+"ПКМ");
        lore.add("§fбутылочкой воды");
        lore.add("§fпо блоку земли.");
        lore.add("");
        lore.add("§fЕсли кликнуть зажигалкой");
        lore.add("§fили ведром лавы,");
        lore.add("§fгрязь превратится");
        lore.add("§fв землю.");
        lore.add("");
        lore.add(orange+"*Сквозь него можно");
        lore.add(orange+"провалиться.");
        lore.add("");

        is = new ItemUtil(Material.DIRT, orange+"Блок грязи", lore).build(null);
        inv.setItem(12, is);

        lore.clear();
        lore.add("§fКаждые "+azure+"2 §fчаса");
        lore.add("§fспавнятся на "+red+"PvP-Арене");
        lore.add("");
        lore.add("§fВиды боссов §7» "+ BossType.GUARDIAN.getCustomName()+"§f, "
                +BossType.HAMSTER.getCustomName() +"§f,");
        lore.add(BossType.FLAME.getCustomName()+"§f, "+BossType.SHULKER.getCustomName()+"§f,");
        lore.add(BossType.SLIME.getCustomName());
        lore.add("");
        lore.add("§fПри убийстве");
        lore.add("§fили нанесении урона,");
        lore.add("§fвыдаются призы.");
        lore.add("§7*В зависимости от");
        lore.add("§7вида босса.");
        lore.add("");

        is = new ItemUtil(Material.SPAWNER, orange+"Боссы", lore).build(null);
        inv.setItem(14, is);

        p.openInventory(inv);
    }
}
