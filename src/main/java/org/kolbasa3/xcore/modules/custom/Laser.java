package org.kolbasa3.xcore.modules.custom;

import org.bukkit.Color;
import org.kolbasa3.xcore.XCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

public class Laser {

    private Location from;
    private Vector direction;
    private double i;

    public Laser(Location from, Vector direction, boolean ruby) {
        direction.multiply(0.5);
        this.from = from;
        this.direction = direction;

        Bukkit.getScheduler().runTaskTimer(XCore.getInstance(), task -> {
            i += 2.5;
            Location particleLocation = from.clone().add(direction.clone().multiply(i));
            if (i >= 50 || particleLocation.getBlock().getType().isSolid()) {
                task.cancel();
                return;
            }
            Color color = Color.PURPLE;
            if(ruby) color = Color.RED;
            Particle.DustOptions dustOptions = new Particle.DustOptions(color, 1);
            from.getWorld().spawnParticle(Particle.REDSTONE, particleLocation, 0, dustOptions);
        }, 1, 0);
    }
}
