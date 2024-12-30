package org.kolbasa3.xcore.listener;

import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.kolbasa3.xcore.modules.tal.TalType;
import org.kolbasa3.xcore.modules.tal.TalUtil;
import org.kolbasa3.xcore.utils.EnchUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AnvilEvent implements Listener {

    EnchUtil enchUtil = new EnchUtil();
    TalUtil talUtil = new TalUtil();

    @EventHandler
    public void onAnvil(PrepareAnvilEvent e) {
        AnvilInventory inv = e.getInventory();
        if(inv.getFirstItem() == null || inv.getSecondItem() == null) return;
        ItemStack first = inv.getFirstItem();
        ItemStack second = inv.getSecondItem();

        String firstMat = first.getType().toString();

        if(first.getType().equals(Material.POTION) && second.getType().equals(Material.POTION)) {
            ItemStack first2 = first.clone();
            first2.setAmount(second.getAmount());
            if(first2.equals(second)) {
                int total = first.getAmount()+second.getAmount();
                if(total <= 64) {
                    first2.setAmount(total);
                    e.setResult(first2);
                    inv.setRepairCost(10);
                    return;
                }
            }
        }

        List<String> allowedEnch = new ArrayList<>();
        if(firstMat.contains("AXE")
                || firstMat.contains("SHOVEL")
                || firstMat.contains("HELMET")
                || firstMat.contains("CHESTPLATE")
                || firstMat.contains("BOOTS")
                || firstMat.contains("SWORD")) {

            if(firstMat.contains("PICKAXE"))
                allowedEnch.addAll(Lists.newArrayList("бур", "плавильня"
                        , "магнетизм", "опыт"));
            else if(firstMat.contains("SHOVEL")
                    || firstMat.contains("AXE")) allowedEnch.add("магнетизм");
            else if(firstMat.contains("HELMET")) allowedEnch.add("громоотвод");
            else if(firstMat.contains("CHESTPLATE")) allowedEnch.add("защитная_аура");
            else if(firstMat.contains("BOOTS")) allowedEnch.add("лаваход");
            else if(firstMat.contains("SWORD"))
                allowedEnch.addAll(Lists.newArrayList("отравление", "гнев_зевса"
                        , "заражение", "опыт"));

            if(second.getType().equals(Material.ENCHANTED_BOOK)
                    && second.getItemMeta().getLore() != null) {
                List<String> ench = enchUtil.list(second);
                if(ench.size() == 1 && allowedEnch.stream().noneMatch(allowed -> ench.get(0).equals(allowed))) return;

                ItemStack is = first.clone();
                boolean b = false;

                for(String s : ench) {
                    if(enchUtil.list(first).contains(s)
                            || allowedEnch.stream().noneMatch(s::equals)) continue;
                    if(!b) b = true;
                    enchUtil.add(is, s);
                }

                if(!b || is.getItemMeta().getLore() == null || enchUtil.list(is).isEmpty()) return;
                int cost = 15;
                if(ench.contains("бур")) cost = 25;

                EnchantmentStorageMeta esm = null;
                if(second.getType().equals(Material.ENCHANTED_BOOK)) {
                    esm = (EnchantmentStorageMeta) second.getItemMeta();
                }

                if(is.getEnchantments().isEmpty()
                        && !is.getType().equals(Material.ENCHANTED_BOOK)
                        && second.getEnchantments().isEmpty() && (esm == null || esm.getStoredEnchants().isEmpty())) {
                    is.addEnchantment(Enchantment.DURABILITY, 1);
                    is.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                } else if(e.getResult() != null) {
                    is.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
                    if(is.getEnchantmentLevel(Enchantment.DURABILITY) == 1) is.removeEnchantment(Enchantment.DURABILITY);
                    is.addEnchantments(e.getResult().getEnchantments());
                }
                e.setResult(is);
                inv.setRepairCost(cost);
                return;
            }
        }

        if(inv.getFirstItem().getType().equals(Material.TOTEM_OF_UNDYING)
                && inv.getSecondItem().getType().equals(Material.TOTEM_OF_UNDYING)
                && !inv.getSecondItem().containsEnchantment(Enchantment.DURABILITY)
                && inv.getFirstItem().containsEnchantment(Enchantment.DURABILITY)
                && inv.getFirstItem().hasItemMeta()
                && inv.getFirstItem().getItemMeta().hasLore()
                && !talUtil.getType(Objects.requireNonNull(inv.getFirstItem().getItemMeta().getLore())).equals(TalType.NULL)
                && talUtil.getDurability(inv.getFirstItem().getItemMeta().getLore()) < 100) {
            ItemStack is = inv.getFirstItem().clone();
            if(talUtil.getDurability(Objects.requireNonNull(is.getItemMeta().getLore()))+25 > 100) talUtil.setDurability(is, 100);
            else talUtil.setDurability(is, (talUtil.getDurability(is.getItemMeta().getLore())+25));
            e.setResult(is);
            inv.setRepairCost(5);
        }
    }
}
