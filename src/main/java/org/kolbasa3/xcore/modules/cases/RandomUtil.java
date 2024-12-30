package org.kolbasa3.xcore.modules.cases;

import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.kolbasa3.xcore.utils.PluginUtil.getRandom;

public class RandomUtil {

    private final List<ItemStack> items = new ArrayList<>();
    private final List<Integer> moneyList = Lists.newArrayList(20000
    , 25000, 30000, 35000, 40000, 45000, 50000);

    public void loadItems() {
        items.add(new ItemStack(Material.OAK_LOG, getRandom(10, 20)));
        items.add(new ItemStack(Material.IRON_BLOCK, getRandom(2, 4)));
        items.add(new ItemStack(Material.GOLD_BLOCK, getRandom(1, 3)));
        items.add(new ItemStack(Material.REDSTONE_BLOCK, getRandom(1, 3)));
        items.add(new ItemStack(Material.DIAMOND, getRandom(2, 4)));
        items.add(new ItemStack(Material.EMERALD, getRandom(1, 3)));
        items.add(new ItemStack(Material.OBSIDIAN, getRandom(5, 8)));
        items.add(new ItemStack(Material.GOLDEN_APPLE, getRandom(2, 4)));
        items.add(new ItemStack(Material.SLIME_BLOCK, getRandom(2, 4)));
        items.add(new ItemStack(Material.PISTON, getRandom(2, 4)));
        items.add(new ItemStack(Material.STICKY_PISTON, getRandom(1, 2)));
        items.add(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, getRandom(1, 2)));
    }

    public String getPrize(CaseType caseType) {
        Random random = new Random();
        int randInt;

        switch (caseType) {
            case DONATE -> {
                randInt = random.nextInt(100);
                if(randInt >= 60 && randInt < 70) return "prem";
                else if(randInt >= 70 && randInt < 80) return "gold";
                else if(randInt >= 80 && randInt < 85) return "admin";
                else if(randInt >= 85 && randInt < 95) return "boss";
                else if(randInt >= 95) return "ultra";
                else return "lite";
            }
            case MONEY -> {
                return moneyList.get(random.nextInt(moneyList.size()))+"";
            }
        }
        return null;
    }

    public ItemStack getItemPrize() {
        loadItems();
        int randInt = new Random().nextInt(items.size());
        return items.get(randInt);
    }

    public int getTime() {
        int random = new Random().nextInt(100);
        if(random > 30 && random < 80) return 1;
        else if(random >= 80 && random < 95) return 5;
        else return -1;
    }
}
