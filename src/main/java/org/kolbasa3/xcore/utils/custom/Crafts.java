package org.kolbasa3.xcore.utils.custom;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.kolbasa3.xcore.XCore;
import org.kolbasa3.xcore.enums.Items;
import org.kolbasa3.xcore.utils.ItemUtil;

import java.util.ArrayList;

import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class Crafts {

    public Crafts() {
        ItemStack is = new ItemUtil(Material.DIAMOND_PICKAXE, blue+"Кирка Тора", null).build(null);
        is.addEnchantment(Enchantment.DIG_SPEED, 1);
        is.addEnchantment(Enchantment.DURABILITY, 2);
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(XCore.getInstance(), "emerald_pickaxe"), is);
        recipe.shape("DDD", " E ", " E ");
        recipe.setIngredient('D', Material.EMERALD);
        recipe.setIngredient('E', Material.STICK);
        Bukkit.getServer().addRecipe(recipe);

        is = new ItemUtil(Material.DIAMOND_AXE, blue+"Топор Тора", null).build(null);
        is.addEnchantment(Enchantment.DIG_SPEED, 1);
        is.addEnchantment(Enchantment.DURABILITY, 2);
        recipe = new ShapedRecipe(new NamespacedKey(XCore.getInstance(), "emerald_axe"), is);
        recipe.shape("DD ", "DE ", " E ");
        recipe.setIngredient('D', Material.EMERALD);
        recipe.setIngredient('E', Material.STICK);
        Bukkit.getServer().addRecipe(recipe);

        is = new ItemUtil(Material.DIAMOND_SWORD, blue+"Меч Тора", null).build(null);
        is.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        is.addEnchantment(Enchantment.DURABILITY, 2);
        recipe = new ShapedRecipe(new NamespacedKey(XCore.getInstance(), "emerald_sword"), is);
        recipe.shape(" D ", " D ", " E ");
        recipe.setIngredient('D', Material.EMERALD);
        recipe.setIngredient('E', Material.STICK);
        Bukkit.getServer().addRecipe(recipe);

        is = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
        recipe = new ShapedRecipe(new NamespacedKey(XCore.getInstance(), "enchanted_golden_apple"), is);
        recipe.shape("DDD", "DED", "DDD");
        recipe.setIngredient('D', Material.GOLD_BLOCK);
        recipe.setIngredient('E', Material.GOLDEN_APPLE);
        Bukkit.getServer().addRecipe(recipe);

        is = new ItemStack(Material.EXPERIENCE_BOTTLE);
        recipe = new ShapedRecipe(new NamespacedKey(XCore.getInstance(), "experience_bottle"), is);
        recipe.shape("DED", "ECE", "DED");
        recipe.setIngredient('D', Material.GOLD_NUGGET);
        recipe.setIngredient('E', Material.REDSTONE);
        recipe.setIngredient('C', Material.GLASS_BOTTLE);
        Bukkit.getServer().addRecipe(recipe);

        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(hex("§fВзрывает обсидиан"));
        lore.add(hex("§fс шансом "+orange+"50%"));
        is = new ItemUtil(Material.TNT, hex("&#A800B5О&#A506B9б&#A20CBDс&#A012C1и&#9D18C5д&#9A1DC8и&#9723CCа&#9429D0н&#922FD4о&#8F35D8в&#8C3BDCы&#8941E0й &#844DE8д&#8153ECи&#7E58EFн&#7B5EF3а&#7964F7м&#766AFBи&#7370FFт"), lore).build(null);
        ItemMeta meta = is.getItemMeta();
        NamespacedKey key = new NamespacedKey(XCore.getInstance(), "custom-block");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "obsidian-tnt");
        is.setItemMeta(meta);
        is.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        is.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        recipe = new ShapedRecipe(new NamespacedKey(XCore.getInstance(), "tnt1"), is);
        recipe.shape("DED", "EDE", "DED");
        recipe.setIngredient('D', Material.OBSIDIAN);
        recipe.setIngredient('E', Material.TNT);
        Bukkit.getServer().addRecipe(recipe);

        lore.clear();
        lore.add("");
        lore.add(hex("§fВзрывает через воду"));
        lore.add(hex("§fс шансом "+orange+"50%"));
        is = new ItemUtil(Material.TNT, hex("&#00B1D1В&#08ACD4о&#10A8D8д&#19A3DBя&#219EDEн&#299AE1о&#3195E5й &#428CEBд&#4A87EFи&#5283F2н&#5A7EF5а&#6379F8м&#6B75FCи&#7370FFт"), lore).build(null);
        is.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        is.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        recipe = new ShapedRecipe(new NamespacedKey(XCore.getInstance(), "tnt2"), is);
        recipe.shape("DED", "EDE", "DED");
        recipe.setIngredient('D', Material.SPONGE);
        recipe.setIngredient('E', Material.TNT);
        Bukkit.getServer().addRecipe(recipe);

        recipe = new ShapedRecipe(new NamespacedKey(XCore.getInstance(), "jump-plate"), Items.PLATE.get(null));
        recipe.shape("   ", " E ", " D ");
        recipe.setIngredient('D', Material.HEAVY_WEIGHTED_PRESSURE_PLATE);

        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta pm = (PotionMeta) item.getItemMeta();
        pm.setBasePotionData(new PotionData(PotionType.JUMP, true, false));
        item.setItemMeta(pm);
        recipe.setIngredient('E', item);
        Bukkit.getServer().addRecipe(recipe);

        recipe = new ShapedRecipe(new NamespacedKey(XCore.getInstance(), "target"), Items.RG_BLOCK.get(null));
        recipe.shape("DED", "EBE", "DED");
        recipe.setIngredient('D', Material.SAND);
        recipe.setIngredient('E', Material.GLASS);
        recipe.setIngredient('B', Material.REDSTONE);
        Bukkit.getServer().addRecipe(recipe);

        recipe = new ShapedRecipe(new NamespacedKey(XCore.getInstance(), "plast"), Items.PLAST.get(null));
        recipe.shape("DED", "EBE", "DED");
        recipe.setIngredient('D', Material.OBSIDIAN);
        recipe.setIngredient('E', Material.QUARTZ);
        recipe.setIngredient('B', Material.ENDER_PEARL);
        Bukkit.getServer().addRecipe(recipe);

        recipe = new ShapedRecipe(new NamespacedKey(XCore.getInstance(), "coffee"), Items.COFFEE.get(null));
        recipe.shape(" D ", " E ", " B ");
        recipe.setIngredient('D', Material.MILK_BUCKET);
        recipe.setIngredient('E', Material.COCOA_BEANS);
        recipe.setIngredient('B', Material.POTION);
        Bukkit.getServer().addRecipe(recipe);

        recipe = new ShapedRecipe(new NamespacedKey(XCore.getInstance(), "tea"), Items.TEA.get(null));
        recipe.shape("   ", " D ", " E ");
        recipe.setIngredient('D', Material.SEAGRASS);
        recipe.setIngredient('E', Material.POTION);
        Bukkit.getServer().addRecipe(recipe);

        recipe = new ShapedRecipe(new NamespacedKey(XCore.getInstance(), "beer"), Items.BEER.get(null));
        recipe.shape("   ", "DED", " B ");
        recipe.setIngredient('D', Material.BREAD);
        recipe.setIngredient('E', Material.SUGAR);
        recipe.setIngredient('B', Material.POTION);
        Bukkit.getServer().addRecipe(recipe);
    }
}
