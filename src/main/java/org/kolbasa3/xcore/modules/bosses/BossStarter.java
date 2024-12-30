package org.kolbasa3.xcore.modules.bosses;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.kolbasa3.xcore.XCore;
import org.kolbasa3.xcore.enums.SoundType;

import java.util.HashMap;
import java.util.Random;

import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class BossStarter {

    public static int time = 0;
    public static final Location BOSS_SPAWN_LOC = new Location(Bukkit.getWorld("air"), -31, 56, 18);
    private BossType bossType;
    private final Random rand = new Random();

    private final HashMap<String, LivingEntity> netherBosses = new HashMap<>();
    private final HashMap<String, LivingEntity> endBosses = new HashMap<>();

    public BossStarter() {}

    public void check(boolean force) {
        time++;
        if(time >= 2 || force) {
            time = 0;
            int random = rand.nextInt(BossType.values().length);
            BossType bossType = BossType.values()[random];
            this.bossType = bossType;

            BossUtil bossUtil = new BossUtil(bossType, BOSS_SPAWN_LOC);
            broadcast();

            Bukkit.getScheduler().runTaskLater(XCore.getInstance(), task ->
                    bossUtil.remove(), 12000);
        }
    }

    public void spawnNether(String player, Location loc) {
        if(!netherBosses.containsKey(player)) {
            if(netherBosses.size() >= 10) {
                netherBosses.get(netherBosses.keySet().stream().toList().get(netherBosses.size()-1)).remove();
                netherBosses.remove(netherBosses.keySet().stream().toList().get(netherBosses.size()-1));
            }

            BossType bossType1 = BossType.NETHER_ZOMBIE;
            if(rand.nextBoolean()) bossType1 = BossType.NETHER_MAGE;
            BossUtil bossUtil = new BossUtil(bossType1, loc);
            netherBosses.put(player, bossUtil.get());

            Bukkit.getScheduler().runTaskLater(XCore.getInstance(), task -> {
                bossUtil.remove();
                netherBosses.remove(player);
            }, 7200);
        }
    }

    public void spawnEnd(String player, Location loc) {
        if(!endBosses.containsKey(player)) {
            if(endBosses.size() >= 10) {
                endBosses.get(endBosses.keySet().stream().toList().get(endBosses.size()-1)).remove();
                endBosses.remove(endBosses.keySet().stream().toList().get(endBosses.size()-1));
            }

            BossUtil bossUtil = new BossUtil(BossType.END_SPIDER, loc);
            endBosses.put(player, bossUtil.get());

            Bukkit.getScheduler().runTaskLater(XCore.getInstance(), task -> {
                bossUtil.remove();
                endBosses.remove(player);
            }, 7200);
        }
    }

    public void broadcast() {
        Bukkit.getOnlinePlayers().forEach(player -> {
           player.sendMessage("");
           player.sendMessage("На "+red+"PvP-Арене §fпоявился босс "+bossType.getCustomName());
           player.sendMessage("Телепортация §7» "+orange+"/warp pvp");
           player.sendMessage("");
           sound(player, SoundType.SELECT);
        });
    }
}
