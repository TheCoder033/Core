package org.kolbasa3.xcore.enums;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.kolbasa3.xcore.utils.ItemUtil;

import java.util.ArrayList;
import java.util.List;

import static org.kolbasa3.xcore.utils.PluginUtil.*;

public enum Items {

    RG_BLOCK,
    PLAST,
    PLATE,
    ARTIFACT,

    COFFEE,
    TEA,
    BEER,

    EXP;

    public ItemStack get(String val) {
        ItemStack is = null;
        List<String> lore = new ArrayList<>();
        if(this.equals(RG_BLOCK)) {
            if(val != null) lore.add("§fРегион: " + azure + val);
            is = new ItemUtil(Material.TARGET, orange + "Блок региона", lore).build(null);
            is.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
            is.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        } else if(this.equals(PLAST)) {
            lore.add("");
            lore.add("§fСоздаёт под вами");
            lore.add(azure + "обсидиановую");
            lore.add("§fплатформу.");
            is = new ItemUtil(Material.SHULKER_SHELL, orange + "Пласт", lore).build(null);
            is.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
            is.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        } else if(this.equals(PLATE)) {
            lore.add("");
            lore.add(hex("§fПодкидывает сущности"));
            lore.add(hex("§fнад плитой вверх."));
            is = new ItemUtil(Material.HEAVY_WEIGHTED_PRESSURE_PLATE, hex(orange+"Отталкивающая плита"), lore).build(null);
            is.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
            is.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        } else if(this.equals(ARTIFACT)) {
            lore.add("");
            lore.add("§fДобыт на "+blue+"/warp mine");
            lore.add("§fПродать §7» "+azure+"/buyer");
            lore.add("");
            is = new ItemUtil(Material.HAY_BLOCK, hex(orange+"Артефакт"), lore).build(null);
            is.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
            is.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        } else if(this.equals(COFFEE)) {
            lore.add("");
            lore.add("§fЭффекты §7(5 мин.):");
            lore.add(lime+"Скорость II");
            lore.add(lime+"Сила II");
            lore.add("");

            is = new ItemUtil(Material.POTION, hex(orange+"Кофе"), lore).build(null);
            is.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

            PotionMeta potionMeta = (PotionMeta) is.getItemMeta();
            potionMeta.setColor(Color.fromBGR(175, 120, 60));
            is.setItemMeta(potionMeta);

        } else if(this.equals(TEA)) {
            lore.add("");
            lore.add("§fЭффекты §7(5 мин.):");
            lore.add(lime+"Скорость I");
            lore.add(lime+"Сила I");
            lore.add("");

            is = new ItemUtil(Material.POTION, hex(orange+"Чай"), lore).build(null);
            is.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

            PotionMeta potionMeta = (PotionMeta) is.getItemMeta();
            potionMeta.setColor(Color.fromBGR(70, 45, 0));
            is.setItemMeta(potionMeta);

        } else if(this.equals(BEER)) {
            lore.add("");
            lore.add("§fЭффекты §7(15 мин.):");
            lore.add(lime+"Тошнота II");
            lore.add(lime+"Сила I");
            lore.add(red+"Слабость I");
            lore.add("");

            is = new ItemUtil(Material.POTION, hex(orange+"Пиво"), lore).build(null);
            is.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

            PotionMeta potionMeta = (PotionMeta) is.getItemMeta();
            potionMeta.setColor(Color.fromBGR(235, 160, 20));
            is.setItemMeta(potionMeta);

        } else if(this.equals(EXP)) {
            lore.add("§fПолучен с помощью "+azure+"/exp");
            is = new ItemUtil(Material.EXPERIENCE_BOTTLE, hex(orange+"§l"+val+" "+orange+" уровень"), lore).build(null);
        }
        return is;
    }
}
