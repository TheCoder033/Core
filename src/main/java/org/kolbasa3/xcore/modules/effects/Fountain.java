package org.kolbasa3.xcore.modules.effects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.util.Vector;
import org.kolbasa3.xcore.XCore;

public class Fountain {

    public Fountain(boolean second) {
        Location loc;
        Vector vector;
        if(!second) {
            loc = new Location(Bukkit.getWorld("air"), -69, 45, 1096);
            vector = new Vector(0, 1, 0.65);
        } else {
            loc = new Location(Bukkit.getWorld("air"), -69, 45, 1114);
            vector = new Vector(0, 1, -0.65);
        }

        FallingBlock fb = loc.getWorld().spawnFallingBlock(loc.clone().add(0, 1, 0), Material.SEA_LANTERN.createBlockData());
        fb.setSilent(true);
        fb.setVelocity(vector);
        fb.setInvulnerable(true);
        fb.setDropItem(false);

        Bukkit.getScheduler().runTaskLater(XCore.getInstance(), task -> fb.remove(), 40);
    }
}
