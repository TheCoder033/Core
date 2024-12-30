package org.kolbasa3.xcore.modules.custom;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class Models {

    public static HashMap<String, ArmorStand> models = new HashMap<>();

    public void spawn(Player p, String nm, ItemStack is) {
        ArmorStand a = (ArmorStand) p.getWorld().spawnEntity(getLoc(p), EntityType.ARMOR_STAND);
        a.setSilent(true);
        a.setGravity(false);
        a.setCanMove(false);
        a.setVisible(false);
        a.setCollidable(false);
        a.setInvulnerable(true);
        a.setAI(false);
        a.setMarker(false);
        a.setCanPickupItems(false);
        a.setCustomNameVisible(false);
        a.setCustomName(nm);
        if(a.getEquipment() != null) a.getEquipment().setHelmet(is);
        models.put(p.getName(), a);
    }

    public Location getLoc(Player p) {
        Location loc = p.getLocation().clone();
        Vector direction = p.getLocation().getDirection().normalize().crossProduct(new Vector(0, 1, 0)).normalize();

        loc.add(direction.multiply(-1.1)); // Расстояние от игрока
        loc.setY(p.getLocation().getY()+0.2); // Y координата игрока
        return loc;
    }
}
