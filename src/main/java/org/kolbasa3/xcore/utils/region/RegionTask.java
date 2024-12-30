package org.kolbasa3.xcore.utils.region;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.XCore;

import java.util.ArrayList;
import java.util.List;

import static org.kolbasa3.xcore.XCore.regionDB;

public class RegionTask {

    RegionUpgrade regionUpgrade = new RegionUpgrade();

    private final List<Location> radarList = new ArrayList<>();

    public void add(String owner, String name, Location loc) {
        if(!radarList.contains(loc)) radarList.add(loc);

        if(radarList.size() == 1) {
            Bukkit.getScheduler().runTaskTimer(XCore.getInstance(), task -> {
                if(Bukkit.getOnlinePlayers().isEmpty()) return;
                if(radarList.isEmpty()) {
                    task.cancel();
                    return;
                }

                radarList.forEach(radarLoc -> {
                    boolean nearbyPlayers = false;
                    for (Player nearby : radarLoc.getNearbyPlayers(regionUpgrade.getLvl(owner, name, "size"))) {
                        if(!owner.equals(nearby.getName()) && !regionDB.getList(owner, name, true).contains(nearby.getName())) {
                            nearbyPlayers = true;
                            break;
                        }
                    }
                    if(radarLoc.getBlock().getType().equals(Material.REDSTONE_BLOCK) && !nearbyPlayers)
                        radarLoc.getBlock().setType(Material.TARGET);
                    else if(nearbyPlayers)
                        radarLoc.getBlock().setType(Material.REDSTONE_BLOCK);
                });

            }, 0, 20);
        }
    }

    public void del(Location loc) {
        radarList.remove(loc);
    }
}
