package org.kolbasa3.xcore.modules.tal;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.kolbasa3.xcore.utils.ItemUtil;

import java.util.ArrayList;
import java.util.List;

import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class TalUtil {

    public TalType getType(List<String> lore) {
        String s = lore.get(0);
        return switch (s) {
            case "§0." -> TalType.FEED;
            case "§0.." -> TalType.HEALTH;
            case "§0:." -> TalType.SPEED;
            case "§0.:" -> TalType.LOOT;
            case "§0," -> TalType.DURABILITY;
            case "§0,," -> TalType.DAMAGE;
            case "§0:," -> TalType.SKY;
            default -> TalType.NULL;
        };
    }

    public ItemStack getTal(TalType type) {
        ArrayList<String> lore = new ArrayList<>();
        String n = "";
        if(type.equals(TalType.FEED)) {
            n = "&#FF882FТ&#FF8E2Fа&#FF9530л&#FF9B30и&#FFA230с&#FFA830м&#FFAF31а&#FFB531н &#FFBB31с&#FFC232ы&#FFC832т&#FFCF32о&#FFD532с&#FFDC33т&#FFE233и";
            lore.add("§0.");
            lore.add(lime+"+30% устойчивость к голоду");
            lore.add(red+"-10% скорость");
            lore.add(red+"-10% макс. здоровье");
            lore.add("");

        } else if(type.equals(TalType.HEALTH)) {
            n = "&#FF882FТ&#FF8E2Fа&#FF9430л&#FF9A30и&#FFA030с&#FFA630м&#FFAC31а&#FFB231н &#FFB831з&#FFBE31д&#FFC432о&#FFCA32р&#FFD032о&#FFD632в&#FFDC33ь&#FFE233я";
            lore.add("§0..");
            lore.add(lime+"+30% макс. здоровье");
            lore.add(red+"-20% броня");
            lore.add(red+"-10% прочность");
            lore.add("");

        } else if(type.equals(TalType.SPEED)) {
            n = "&#FF882FТ&#FF8E2Fа&#FF9430л&#FF9A30и&#FFA030с&#FFA630м&#FFAC31а&#FFB231н &#FFB831с&#FFBE31к&#FFC432о&#FFCA32р&#FFD032о&#FFD632с&#FFDC33т&#FFE233и";
            lore.add("§0:.");
            lore.add(lime+"+30% скорость");
            lore.add(red+"-10% макс. здоровье");
            lore.add(red+"-20% броня");
            lore.add("");

        } else if(type.equals(TalType.LOOT)) {
            n = "&#FF882FТ&#FF902Fа&#FF9730л&#FF9F30и&#FFA630с&#FFAE31м&#FFB531а&#FFBD31н &#FFC432у&#FFCC32д&#FFD332а&#FFDB33ч&#FFE233и";
            lore.add("§0.:");
            lore.add(lime+"+30% удача");
            lore.add(red+"-10% прочность");
            lore.add(red+"-20% скорость");
            lore.add("");

        } else if(type.equals(TalType.DURABILITY)) {
            n = "&#FF882FТ&#FF8E2Fа&#FF9330л&#FF9930и&#FF9F30с&#FFA430м&#FFAA31а&#FFAF31н &#FFB531п&#FFBB31р&#FFC032о&#FFC632ч&#FFCC32н&#FFD132о&#FFD733с&#FFDC33т&#FFE233и";
            lore.add("§0,");
            lore.add(lime+"+40% прочность");
            lore.add(red+"-20% скорость");
            lore.add(red+"-10% макс. здоровье");
            lore.add("");

        } else if(type.equals(TalType.DAMAGE)) {
            n = "&#FF882FТ&#FF902Fа&#FF9730л&#FF9F30и&#FFA630с&#FFAE31м&#FFB531а&#FFBD31н &#FFC432у&#FFCC32р&#FFD332о&#FFDB33н&#FFE233а";
            lore.add("§0,,");
            lore.add(lime+"+30% урон");
            lore.add(red+"-20% прочность");
            lore.add(red+"-10% макс. здоровье");
            lore.add("");

        } else if(type.equals(TalType.SKY)) {
            n = "&#33C3FF☁ &#084EFBН&#0B56FBе&#0E5EFCб&#1165FCе&#136DFCс&#1675FCн&#197DFDы&#1C85FDй &#1F8CFDт&#2294FDа&#259CFEл&#28A4FEи&#2AACFEс&#2DB3FEм&#30BBFFа&#33C3FFн &#33C3FF☁";
            lore.add("§0:,");
            lore.add(lime+"+30% макс. здоровье");
            lore.add(lime+"+20% скорость");
            lore.add(lime+"+30% прочность");
            lore.add(red+"-10% броня");
            lore.add("");
        }
        lore.add("§fПрочность: "+azure+"100§7/100");

        ItemStack is = new ItemUtil(Material.TOTEM_OF_UNDYING, hex(n), lore).build(null);
        is.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        is.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return is;
    }

    public void setDurability(ItemStack is, int v) {
        if(!is.hasItemMeta() || !is.getItemMeta().hasLore()) return;
        ItemMeta im = is.getItemMeta();
        List<String> lore = im.getLore();
        if(lore == null) return;
        lore.set((lore.size()-1), "§fПрочность: "+azure+v+"§7/100");
        im.setLore(lore);
        is.setItemMeta(im);
    }

    public int getDurability(List<String> lore) {
        return Integer.parseInt(lore.get(lore.size()-1)
                .replace("§fПрочность: §x§3§5§C§B§F§F", "")
                .replace("§7/100", ""));
    }
}
