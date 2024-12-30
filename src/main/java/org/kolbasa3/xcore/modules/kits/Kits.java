package org.kolbasa3.xcore.modules.kits;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

public class Kits {

    public List<ItemStack> getKit(String kit) {
        List<ItemStack> list = new ArrayList<>();
        switch (kit) {
            case "start": {
                ItemStack is = new ItemStack(Material.LEATHER_HELMET);
                LeatherArmorMeta m = (LeatherArmorMeta) is.getItemMeta();
                m.setColor(Color.AQUA);
                is.setItemMeta(m);
                list.add(is);

                is = new ItemStack(Material.LEATHER_CHESTPLATE);
                m = (LeatherArmorMeta) is.getItemMeta();
                m.setColor(Color.AQUA);
                is.setItemMeta(m);
                list.add(is);

                is = new ItemStack(Material.LEATHER_LEGGINGS);
                m = (LeatherArmorMeta) is.getItemMeta();
                m.setColor(Color.AQUA);
                is.setItemMeta(m);
                list.add(is);

                is = new ItemStack(Material.LEATHER_BOOTS);
                m = (LeatherArmorMeta) is.getItemMeta();
                m.setColor(Color.AQUA);
                is.setItemMeta(m);
                list.add(is);

                list.add(new ItemStack(Material.STONE_PICKAXE));
                list.add(new ItemStack(Material.STONE_AXE));
                list.add(new ItemStack(Material.STONE_SWORD));
                list.add(new ItemStack(Material.FISHING_ROD));
                list.add(new ItemStack(Material.TORCH, 5));

                break;
            }
            case "lite":
                list.add(new ItemStack(Material.CHAINMAIL_HELMET));
                list.add(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                list.add(new ItemStack(Material.CHAINMAIL_LEGGINGS));
                list.add(new ItemStack(Material.CHAINMAIL_BOOTS));

                list.add(new ItemStack(Material.IRON_PICKAXE));
                list.add(new ItemStack(Material.IRON_AXE));
                list.add(new ItemStack(Material.IRON_SWORD));
                list.add(new ItemStack(Material.COOKED_PORKCHOP, 10));
                list.add(new ItemStack(Material.TORCH, 10));

                break;
            case "prem": {
                ItemStack is = new ItemStack(Material.CHAINMAIL_HELMET);
                is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                list.add(is);

                is = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
                is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                list.add(is);

                is = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                list.add(is);

                is = new ItemStack(Material.CHAINMAIL_BOOTS);
                is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                list.add(is);

                list.add(new ItemStack(Material.IRON_PICKAXE));
                list.add(new ItemStack(Material.IRON_AXE));
                list.add(new ItemStack(Material.IRON_SWORD));
                list.add(new ItemStack(Material.COOKED_PORKCHOP, 20));
                list.add(new ItemStack(Material.TORCH, 20));
                list.add(new ItemStack(Material.ENDER_PEARL));
                list.add(new ItemStack(Material.CHORUS_FRUIT, 3));

                break;
            }
            case "gold": {
                list.add(new ItemStack(Material.IRON_HELMET));
                list.add(new ItemStack(Material.IRON_CHESTPLATE));
                list.add(new ItemStack(Material.IRON_LEGGINGS));
                list.add(new ItemStack(Material.IRON_BOOTS));

                ItemStack is = new ItemStack(Material.IRON_PICKAXE);
                is.addEnchantment(Enchantment.DIG_SPEED, 1);
                list.add(is);

                is = new ItemStack(Material.IRON_AXE);
                is.addEnchantment(Enchantment.DIG_SPEED, 1);
                list.add(is);

                is = new ItemStack(Material.IRON_SWORD);
                is.addEnchantment(Enchantment.DAMAGE_ALL, 1);
                list.add(is);

                list.add(new ItemStack(Material.COOKED_PORKCHOP, 30));
                list.add(new ItemStack(Material.TORCH, 30));
                list.add(new ItemStack(Material.ENDER_PEARL, 2));
                list.add(new ItemStack(Material.CHORUS_FRUIT, 5));

                break;
            }
            case "admin": {
                ItemStack is = new ItemStack(Material.IRON_HELMET);
                is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                is.addEnchantment(Enchantment.DURABILITY, 2);
                list.add(is);

                is = new ItemStack(Material.IRON_CHESTPLATE);
                is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                is.addEnchantment(Enchantment.DURABILITY, 2);
                list.add(is);

                is = new ItemStack(Material.IRON_LEGGINGS);
                is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                is.addEnchantment(Enchantment.DURABILITY, 2);
                list.add(is);

                is = new ItemStack(Material.IRON_BOOTS);
                is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                is.addEnchantment(Enchantment.DURABILITY, 2);
                list.add(is);

                is = new ItemStack(Material.IRON_PICKAXE);
                is.addEnchantment(Enchantment.DIG_SPEED, 2);
                is.addEnchantment(Enchantment.DURABILITY, 1);
                list.add(is);

                is = new ItemStack(Material.IRON_AXE);
                is.addEnchantment(Enchantment.DIG_SPEED, 2);
                is.addEnchantment(Enchantment.DURABILITY, 1);
                list.add(is);

                is = new ItemStack(Material.IRON_SWORD);
                is.addEnchantment(Enchantment.DAMAGE_ALL, 2);
                is.addEnchantment(Enchantment.DURABILITY, 1);
                list.add(is);

                list.add(new ItemStack(Material.COOKED_PORKCHOP, 40));
                list.add(new ItemStack(Material.TORCH, 40));
                list.add(new ItemStack(Material.ENDER_PEARL, 3));
                list.add(new ItemStack(Material.CHORUS_FRUIT, 10));

                break;
            }
            case "boss": {
                ItemStack is = new ItemStack(Material.DIAMOND_HELMET);
                is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                is.addEnchantment(Enchantment.DURABILITY, 3);
                is.addEnchantment(Enchantment.THORNS, 1);
                list.add(is);

                is = new ItemStack(Material.DIAMOND_CHESTPLATE);
                is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                is.addEnchantment(Enchantment.DURABILITY, 3);
                is.addEnchantment(Enchantment.THORNS, 1);
                list.add(is);

                is = new ItemStack(Material.DIAMOND_LEGGINGS);
                is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                is.addEnchantment(Enchantment.DURABILITY, 3);
                is.addEnchantment(Enchantment.THORNS, 1);
                list.add(is);

                is = new ItemStack(Material.DIAMOND_BOOTS);
                is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                is.addEnchantment(Enchantment.DURABILITY, 3);
                is.addEnchantment(Enchantment.THORNS, 1);
                list.add(is);

                is = new ItemStack(Material.DIAMOND_PICKAXE);
                is.addEnchantment(Enchantment.DIG_SPEED, 3);
                is.addEnchantment(Enchantment.DURABILITY, 2);
                list.add(is);

                is = new ItemStack(Material.DIAMOND_AXE);
                is.addEnchantment(Enchantment.DIG_SPEED, 3);
                is.addEnchantment(Enchantment.DURABILITY, 2);
                list.add(is);

                is = new ItemStack(Material.DIAMOND_SWORD);
                is.addEnchantment(Enchantment.DAMAGE_ALL, 3);
                is.addEnchantment(Enchantment.DURABILITY, 2);
                list.add(is);

                is = new ItemStack(Material.BOW);
                is.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
                list.add(is);

                list.add(new ItemStack(Material.ARROW, 32));
                list.add(new ItemStack(Material.COOKED_PORKCHOP, 50));
                list.add(new ItemStack(Material.TORCH, 50));
                list.add(new ItemStack(Material.ENDER_PEARL, 5));
                list.add(new ItemStack(Material.CHORUS_FRUIT, 20));
                list.add(new ItemStack(Material.GOLDEN_APPLE, 3));
                list.add(new ItemStack(Material.EXPERIENCE_BOTTLE, 32));
                list.add(new ItemStack(Material.TOTEM_OF_UNDYING));

                break;
            }
            case "ultra": {
                ItemStack is = new ItemStack(Material.NETHERITE_HELMET);
                is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                is.addEnchantment(Enchantment.DURABILITY, 3);
                is.addEnchantment(Enchantment.THORNS, 2);
                list.add(is);

                is = new ItemStack(Material.NETHERITE_CHESTPLATE);
                is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                is.addEnchantment(Enchantment.DURABILITY, 3);
                is.addEnchantment(Enchantment.THORNS, 2);
                list.add(is);

                is = new ItemStack(Material.NETHERITE_LEGGINGS);
                is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                is.addEnchantment(Enchantment.DURABILITY, 3);
                is.addEnchantment(Enchantment.THORNS, 2);
                list.add(is);

                is = new ItemStack(Material.NETHERITE_BOOTS);
                is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                is.addEnchantment(Enchantment.DURABILITY, 3);
                is.addEnchantment(Enchantment.THORNS, 2);
                list.add(is);

                is = new ItemStack(Material.DIAMOND_PICKAXE);
                is.addEnchantment(Enchantment.DIG_SPEED, 4);
                is.addEnchantment(Enchantment.DURABILITY, 3);
                is.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 2);
                list.add(is);

                is = new ItemStack(Material.DIAMOND_AXE);
                is.addEnchantment(Enchantment.DIG_SPEED, 4);
                is.addEnchantment(Enchantment.DURABILITY, 3);
                is.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 2);
                is.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 2);
                list.add(is);

                is = new ItemStack(Material.DIAMOND_SWORD);
                is.addEnchantment(Enchantment.DAMAGE_ALL, 4);
                is.addEnchantment(Enchantment.DURABILITY, 3);
                is.addEnchantment(Enchantment.LOOT_BONUS_MOBS, 2);
                is.addEnchantment(Enchantment.FIRE_ASPECT, 2);
                list.add(is);

                is = new ItemStack(Material.BOW);
                is.addEnchantment(Enchantment.ARROW_DAMAGE, 3);
                is.addEnchantment(Enchantment.ARROW_FIRE, 1);
                list.add(is);

                list.add(new ItemStack(Material.ARROW, 64));
                list.add(new ItemStack(Material.COOKED_PORKCHOP, 50));
                list.add(new ItemStack(Material.TORCH, 50));
                list.add(new ItemStack(Material.ENDER_PEARL, 5));
                list.add(new ItemStack(Material.CHORUS_FRUIT, 20));
                list.add(new ItemStack(Material.GOLDEN_APPLE, 5));
                list.add(new ItemStack(Material.EXPERIENCE_BOTTLE, 64));
                list.add(new ItemStack(Material.TOTEM_OF_UNDYING));
                list.add(new ItemStack(Material.TOTEM_OF_UNDYING));
                break;
            }
        }
        return list;
    }
}
