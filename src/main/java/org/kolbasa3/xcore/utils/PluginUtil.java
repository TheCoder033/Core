package org.kolbasa3.xcore.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.kolbasa3.xcore.XCore;
import org.kolbasa3.xcore.enums.SoundType;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PluginUtil {

    public static FileConfiguration cfg() {
        return XCore.getInstance().getConfig();
    }

    public static String red = hex("&#FF5135");
    public static String orange = hex("&#FF9D35"); // §x§F§F§9§D§3§5
    public static String yellow = hex("&#FFDA33");
    public static String lime = hex("&#D4FF33");
    public static String azure = hex("&#35CBFF"); // §x§3§5§C§B§F§F
    public static String blue = hex("&#35A6FF");

    public static HashMap<String, String> clanInvite = new HashMap<>();
    public static ArrayList<Location> dirt = new ArrayList<>();
    public static ArrayList<Location> dirtPlaced = new ArrayList<>();
    public static ArrayList<Entity> doubleExp = new ArrayList<>();
    public static HashMap<String, ArmorStand> models = new HashMap<>();

    public static HashMap<String, Integer> casino = new HashMap<>();

    public static HashMap<String, Long> chatCd = new HashMap<>();

    public static String componentToString(Component component) {
        return new PlainComponentSerializer().serialize(component);
    }

    public static ItemStack posoh(boolean ruby) {
        String type = "ametist";
        String name = "&#FF2CCDА&#FB33D0м&#F73AD3е&#F341D6т&#EF48DAи&#EB4FDDс&#E756E0т&#E35DE3о&#E064E6в&#DC6AE9ы&#D871ECй &#D07FF3п&#CC86F6о&#C88DF9с&#C494FCо&#C09BFFх";
        if(ruby) {
            type = "ruby";
            name = "&#FF552CР&#FF5C34у&#FF633Cб&#FF6A44и&#FF714Cн&#FF7854о&#FF7F5Cв&#FF8764ы&#FF8E6Bй &#FF9C7Bп&#FFA383о&#FFAA8Bс&#FFB193о&#FFB89Bх";
        }
        ItemStack is = new ItemUtil(Material.TRIDENT, hex(name), null).build(null);
        is.addEnchantment(Enchantment.DURABILITY, 1);
        is.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        is.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        NamespacedKey key = new NamespacedKey(XCore.getInstance(), "custom-item");
        ItemMeta meta = is.getItemMeta();
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, type + "-posoh");
        is.setItemMeta(meta);
        return is;
    }

    public static ItemStack customOre(boolean ruby) {
        String type = "ametist";
        String name = "&#972CFFА&#A33FFFм&#AF51FFе&#BB64FFт&#C676FFи&#D289FFс&#DE9BFFт";
        String data = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTlmMjlmNGFjZDRlNGIyOGVjMGMxYjcyMjU4ZGEzZDM1ZTNiNmE3MWI1Yjk4ZmNlZWFlYjhiYTllMmE2In19fQ==";
        if(ruby) {
            type = "ruby";
            name = "&#FB4508Р&#FC5812у&#FD6A1Dб&#FE7D27и&#FF8F31н";
            data = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjgzMjM2NjM5NjA3MDM2YzFiYTM5MWMyYjQ2YTljN2IwZWZkNzYwYzhiZmEyOTk2YTYwNTU1ODJiNGRhNSJ9fX0=";
        }
        ItemStack is = new ItemUtil(Material.PLAYER_HEAD, hex(name), null).build(data);
        NamespacedKey key = new NamespacedKey(XCore.getInstance(), "custom-item");
        ItemMeta meta = is.getItemMeta();
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, type);
        is.setItemMeta(meta);
        return is;
    }

    public static void addPlayerInv(Player p, ItemStack is) {
        if(p.getInventory().firstEmpty() == -1) p.getWorld().dropItemNaturally(p.getLocation(), is);
        else p.getInventory().addItem(is);
    }

    public static boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void sound(Player p, SoundType soundType) {
        p.playSound(p.getLocation(), switch (soundType) {
            case SUCCESS -> Sound.ENTITY_PLAYER_LEVELUP;
            case WRONG -> Sound.BLOCK_NOTE_BLOCK_PLING;
            case SELECT -> Sound.UI_BUTTON_CLICK;
            case LOCK -> Sound.BLOCK_CHEST_LOCKED;
        }, 0.3F, 0.5F);
    }

    public static String hex(String from) {
        Pattern pattern = Pattern.compile("&#[a-fA-F\0-9]{6}");
        Matcher matcher = pattern.matcher(from);
        while (matcher.find()) {
            String hexCode = from.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace("&#", "x");
            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (char c : ch) builder.append("&").append(c);
            from = from.replace(hexCode, builder.toString());
            matcher = pattern.matcher(from);
        }
        return ChatColor.translateAlternateColorCodes('&', from);
    }

    public static TextComponent hexTextComponent(String from) {
        BaseComponent[] text = TextComponent.fromLegacyText(hex(from));
        TextComponent output = new TextComponent();
        Arrays.stream(text).forEach(output::addExtra);
        return output;
    }

    public static BaseComponent[] hexBaseComponent(String from) {
        return TextComponent.fromLegacyText(hex(from));
    }

    public static int getRandom(int min, int max) {
        int random = new Random().nextInt(max);
        if(random < min) return getRandom(min, max);
        return random;
    }

    public static String locToStr(Location loc, String delimiter, boolean block) {
        String str = loc.getWorld().getName()+delimiter;
        if(!block) str = str+loc.getX()+delimiter+loc.getY()+delimiter+loc.getZ()+delimiter+loc.getYaw()
                +delimiter+loc.getPitch();
        else str = str+loc.getBlockX()+delimiter+loc.getBlockY()+delimiter+loc.getBlockZ();
        return str;
    }

    public static Location strToLoc(String str, String delimiter, boolean block) {
        String[] str2 = str.split(delimiter);
        if(!block) return new Location(Bukkit.getWorld(str2[0]), Double.parseDouble(str2[1])
        , Double.parseDouble(str2[2]), Double.parseDouble(str2[3]), Float.parseFloat(str2[4])
        , Float.parseFloat(str2[5]));
        else return new Location(Bukkit.getWorld(str2[0]), Integer.parseInt(str2[1])
        , Integer.parseInt(str2[2]), Integer.parseInt(str2[3]));
    }
}
