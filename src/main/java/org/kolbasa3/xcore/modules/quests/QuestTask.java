package org.kolbasa3.xcore.modules.quests;

import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.kolbasa3.xcore.XCore.playerDB;
import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class QuestTask {

    final List<String> tasks = Lists.newArrayList("jump", "kill", "mine", "wood");
    static final HashMap<String, Integer> questAmount = new HashMap<>();
    static final HashMap<String, HashMap<String, String>> questTaskList = new HashMap<>();
    static final HashMap<String, Integer> questTaskAmount = new HashMap<>();
    static final HashMap<String, Integer> questProgress = new HashMap<>();
    static final HashMap<String, Integer> questTaskPrize = new HashMap<>();

    List<Integer> jumpBlocks = Lists.newArrayList(600, 700, 800, 900);

    List<Integer> killAmount = Lists.newArrayList(20, 25, 30, 35, 40, 45);
    Map<EntityType, String> killMobs = Map.of(
            EntityType.ZOMBIE, "Зомби",
            EntityType.SPIDER, "Паук",
            EntityType.CREEPER, "Крипер",
            EntityType.SKELETON, "Скелет"
    );

    List<Integer> mineAmount = Lists.newArrayList(30, 35, 40, 45, 50);
    Map<Material, String> mineBlocks = Map.of(
            Material.SAND, "Песок",
            Material.IRON_ORE, "Железная руда",
            Material.GOLD_ORE, "Золотая руда",
            Material.REDSTONE, "Редстоун",
            Material.DIAMOND, "Алмаз"
    );

    List<Integer> woodAmount = Lists.newArrayList(50, 70, 90, 110, 130);

    public int getAmount(String p) {
        return questAmount.getOrDefault(p, 0);
    }

    public HashMap<String, String> getTaskList(String p) {
        return questTaskList.get(p);
    }

    public int getTaskAmount(String p) {
        if(getTaskList(p).containsKey("jump")) return Integer.parseInt(getTaskList(p).get("jump"));
        return questTaskAmount.getOrDefault(p, 0);
    }

    public int getProgress(String p) {
        return questProgress.getOrDefault(p, 0);
    }

    public void addProgress(Player p) {
        int i = questProgress.getOrDefault(p.getName(), 0)+1;

        if(i >= getTaskAmount(p.getName())) {
            int prize = getPrize(p.getName());
            completeQuest(p.getName());
            playerDB.setMoney(p.getName(), (playerDB.getMoney(p.getName())+prize));
            p.sendMessage("");
            p.sendMessage(lime+"Вы выполнили квест.");
            p.sendMessage("Приз §7» "+yellow+prize+"⛁");
            p.sendMessage("");
            return;
        }
        questProgress.put(p.getName(), i);
    }

    public void completeQuest(String p) {
        questTaskList.remove(p);
        questTaskAmount.remove(p);
        questProgress.remove(p);
        questTaskPrize.remove(p);
    }

    public Map<EntityType, String> getKillMobs() {
        return killMobs;
    }

    public Map<Material, String> getMineBlocks() {
        return mineBlocks;
    }

    public int getPrize(String p) {
        return questTaskPrize.get(p);
    }

    public void startQuest(String p) {
        Random random = new Random();
        String task = tasks.get(random.nextInt(tasks.size()));

        AtomicReference<String> type = new AtomicReference<>();
        int amount = 0;
        int prize = 0;
        switch (task) {
            case "jump":
                type.set(jumpBlocks.get(random.nextInt(jumpBlocks.size())) + "");
                prize = 15000;
                break;
            case "kill":
                amount = killAmount.get(random.nextInt(killAmount.size()));
                prize = 25000;
                int selected = random.nextInt(killMobs.size());
                AtomicInteger i2 = new AtomicInteger();
                killMobs.keySet()
                        .stream().filter(entityType -> type.get() == null)
                        .forEach(entityType -> {
                            if (i2.getAndIncrement() == selected) {
                                type.set(entityType.toString());
                            }
                        });
                break;
            case "mine":
                amount = mineAmount.get(random.nextInt(mineAmount.size()));
                prize = 20000;
                selected = random.nextInt(mineBlocks.size());
                i2 = new AtomicInteger();
                mineBlocks.keySet()
                        .stream().filter(entityType -> type.get() == null)
                        .forEach(mat -> {
                            if (i2.getAndIncrement() == selected)
                                type.set(mat.toString());
                        });
                break;
            case "wood":
                amount = woodAmount.get(random.nextInt(woodAmount.size()));
                prize = 15000;
                break;
        }
        questAmount.put(p, (questAmount.getOrDefault(p, 0)+1));

        questTaskPrize.put(p, prize);
        HashMap<String, String> map = new HashMap<>();
        map.put(task, type.get());
        questTaskList.put(p, map);
        questTaskAmount.put(p, amount);
    }

    public String getFormattedTask(String p) {
        HashMap<String, String> task = getTaskList(p);
        int amount = questTaskAmount.getOrDefault(p, 0);
        String str = "";

        if(task.containsKey("jump")) str = "прыгнуть "+blue+task.get("jump")+azure+" раз";
        else if(task.containsKey("kill")) str = "убить "+blue+task.get("kill")+" x"+amount;
        else if(task.containsKey("mine")) str = "добыть "+blue+task.get("mine")+" x"+amount;
        else if(task.containsKey("wood")) str = "срубить деревья "+blue+"x"+amount+" §7(/warp wood)";

        return hex(azure+str);
    }
}
