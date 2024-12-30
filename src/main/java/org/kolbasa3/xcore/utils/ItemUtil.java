package org.kolbasa3.xcore.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

public class ItemUtil {

    private final Material mat;
    private final String name;
    private final List<String> lore;

    public ItemUtil(Material mat, String name, List<String> lore) {
        this.mat = mat;
        this.name = name;
        this.lore = lore;
    }

    public ItemStack build(String data) {
        ItemStack is;
        if(data == null) is = new ItemStack(mat);
        else is = createSkull(data);

        ItemMeta itemMeta = is.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        is.setItemMeta(itemMeta);
        return is;
    }

    public ItemStack createSkull(String data) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", data));

        Field profileField;
        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        skull.setItemMeta(skullMeta);

        return skull;
    }
}
