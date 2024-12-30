package org.kolbasa3.xcore.modules.bosses;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Random;

import static org.kolbasa3.xcore.XCore.playerDB;
import static org.kolbasa3.xcore.utils.PluginUtil.getRandom;
import static org.kolbasa3.xcore.utils.PluginUtil.yellow;

public class BossListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player player
                && e.getEntity().getCustomName() != null
                && e.getEntity().getWorld().getName().equals("air")) {
            switch (e.getEntityType()) {
                case HOGLIN -> {
                    playerDB.setMoney(player.getName(), (playerDB.getMoney(player.getName())+10));
                    player.sendActionBar("Вы получили: "+yellow+"10⛁");
                }
                case PILLAGER -> {
                    ShulkerBullet bullet = e.getEntity().getWorld().spawn(e.getEntity().getLocation(), ShulkerBullet.class);
                    bullet.setTarget(e.getDamager());
                }
                case SLIME -> {
                    Vector vector = e.getEntity().getLocation().toVector().subtract(player.getLocation().toVector());
                    vector.normalize();
                    vector.multiply(1.5);

                    player.setVelocity(vector);
                }

                case ZOMBIE -> player.setFireTicks(40);
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if(e.getEntity().getCustomName() != null
                && e.getEntity().getWorld().getName().equals("air")) {
            switch (e.getEntityType()) {
                case WITHER_SKELETON -> {
                    e.getDrops().clear();
                    e.getDrops().add(new ItemStack(Material.IRON_INGOT, getRandom(4, 8)));
                    e.getDrops().add(new ItemStack(Material.DIAMOND, getRandom(2, 4)));
                    if(new Random().nextBoolean())
                        e.getDrops().add(new ItemStack(Material.EMERALD, getRandom(1, 3)));
                }
                case HOGLIN -> {
                    e.getDrops().clear();
                    e.getDrops().add(new ItemStack(Material.GOLD_INGOT, getRandom(4, 8)));
                }
                case BLAZE -> {
                    if(new Random().nextBoolean())
                        e.getDrops().add(new ItemStack(Material.FLINT_AND_STEEL));
                }
                case PILLAGER -> {
                    e.getDrops().clear();
                    e.getDrops().add(new ItemStack(Material.EXPERIENCE_BOTTLE, getRandom(15, 30)));
                    e.getDrops().add(new ItemStack(Material.CHORUS_FRUIT, getRandom(3, 8)));
                    if(new Random().nextBoolean())
                        e.getDrops().add(new ItemStack(Material.NETHERITE_SCRAP, getRandom(1, 3)));
                }
                case SLIME -> {
                    e.getDrops().add(new ItemStack(Material.NETHERITE_INGOT, getRandom(1, 2)));
                    e.getDrops().add(new ItemStack(Material.DIAMOND, getRandom(3, 6)));
                    e.getDrops().add(new ItemStack(Material.GOLDEN_APPLE, getRandom(1, 3)));
                    if(new Random().nextBoolean())
                        e.getDrops().add(new ItemStack(Material.TNT, getRandom(1, 3)));
                    if(new Random().nextBoolean())
                        e.getDrops().add(new ItemStack(Material.TOTEM_OF_UNDYING));
                }
            }
        }
    }
}
