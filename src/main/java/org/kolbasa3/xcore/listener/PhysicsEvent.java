package org.kolbasa3.xcore.listener;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.kolbasa3.xcore.XCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.inventory.ItemStack;
import org.kolbasa3.xcore.modules.custom.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static org.kolbasa3.xcore.cmds.SitCMD.sit;
import static org.kolbasa3.xcore.utils.PluginUtil.models;

public class PhysicsEvent implements Listener {

    private final HashMap<Location, Integer> redstoneDetect = new HashMap<>();
    public static ArrayList<Entity> angryMobs = new ArrayList<>();

    Models modelUtil = new Models();

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        if(models.containsKey(p.getName()))
            models.get(p.getName()).teleport(modelUtil.getLoc(p));
    }

    @EventHandler
    public void onProjectile(ProjectileHitEvent e) {
        if(e.getHitBlock() != null) {
            Block b = e.getHitBlock();
            if(b.getType().toString().contains("LEAVES")) {
                b.breakNaturally(new ItemStack(Material.DIAMOND_PICKAXE), true);
                e.getEntity().remove();

            } else if(e.getEntity() instanceof Arrow && e.getEntity().getFireTicks() != 0
            && (b.getType().toString().contains("LOG")
            || b.getType().toString().contains("WOOD"))) {
                if(!e.getEntity().getLocation().clone().toBlockLocation().equals(b.getLocation()))
                    e.getEntity().getLocation().getBlock().setType(Material.FIRE);
            }
        }
    }

    @EventHandler
    public void onRedstone(BlockRedstoneEvent e) {
        Block block = e.getBlock();

        if (Bukkit.getTPS()[0] < 19.9) {
            Location loc = block.getLocation();

            if (!redstoneDetect.containsKey(loc)) {
                Bukkit.getScheduler().runTaskLater(XCore.getInstance(), () -> {
                    if (redstoneDetect.get(loc) >= 6) loc.getBlock().setType(Material.AIR);
                    redstoneDetect.remove(loc);
                }, 20);
            }

            redstoneDetect.put(loc, redstoneDetect.getOrDefault(loc, 0) + 1);
        } else redstoneDetect.remove(e.getBlock().getLocation());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onTarget(EntityTargetEvent e) {
        e.setCancelled(!(e.getTarget() instanceof Player) && angryMobs.contains(e.getEntity()));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onTarget(EntityTargetLivingEntityEvent e) {
        e.setCancelled(!(e.getTarget() instanceof Player) && angryMobs.contains(e.getEntity()));
    }

    @EventHandler
    public void onSpawn(EntitySpawnEvent e) {
        if((e.getEntity() instanceof Fox || e.getEntity() instanceof Wolf)
                && !e.getEntity().getWorld().getName().equals("air")
        && new Random().nextInt(100) > 90) {
            if(angryMobs.size() > 10) {
                ((Animals) e.getEntity()).removePotionEffect(PotionEffectType.SLOW);
                angryMobs.remove(angryMobs.size()-1);
            }
            angryMobs.add(e.getEntity());
            ((Animals) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10000, 1, false, false));
        }

        if (e.getEntity().getWorld().getName().equals("air")
                && !e.getEntity().getEntitySpawnReason().equals(CreatureSpawnEvent.SpawnReason.COMMAND)
        && e.getEntity().getCustomName() == null
                && !e.getEntity().isInvulnerable()
                && e.getEntity().hasGravity()
        && !e.getEntityType().equals(EntityType.ARROW) && !e.getEntityType().equals(EntityType.ENDER_PEARL)
        && !e.getEntityType().equals(EntityType.DROPPED_ITEM) && !e.getEntityType().equals(EntityType.EXPERIENCE_ORB)
        && !e.getEntityType().equals(EntityType.THROWN_EXP_BOTTLE) && !e.getEntityType().equals(EntityType.SNOWBALL)
        && !e.getEntityType().equals(EntityType.EGG) && !e.getEntityType().equals(EntityType.FIREWORK)
        && !e.getEntityType().equals(EntityType.FIREBALL) && !e.getEntityType().equals(EntityType.FISHING_HOOK)
        && !e.getEntityType().equals(EntityType.LEASH_HITCH) && !e.getEntityType().equals(EntityType.LIGHTNING)
        && !e.getEntityType().equals(EntityType.LLAMA_SPIT) && !e.getEntityType().equals(EntityType.ITEM_FRAME)
        && !e.getEntityType().equals(EntityType.SHULKER_BULLET) && !e.getEntityType().equals(EntityType.SPECTRAL_ARROW)
        && !e.getEntityType().equals(EntityType.TRIDENT) && !e.getEntityType().equals(EntityType.SPLASH_POTION)
        && !e.getEntityType().equals(EntityType.FALLING_BLOCK) && !e.getEntityType().equals(EntityType.SMALL_FIREBALL)) {
            e.setCancelled(true);
            e.getEntity().remove();
        }

        List<Entity> list = e.getLocation().getWorld().getEntities();
        list.removeIf(ent -> ent instanceof Player
                || e.getLocation().getWorld().getName().equals("air")
        || (Bukkit.getTPS()[0] > 17 && ent instanceof ArmorStand
                && !ent.hasGravity() && !((ArmorStand) ent).isVisible()));

        if(Bukkit.getTPS()[0] <= 18
                && !(e.getEntity() instanceof Player)
        && list.size() > 20) {
            if(list.size() > 40) {
                for (int i = 0; i < (list.size() / 2); i++) {
                    list.get(i).remove();
                }
            } else list.forEach(Entity::remove);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if(sit.containsKey(p.getName())) {
            sit.get(p.getName()).remove();
            sit.remove(p.getName());
            p.teleport(p.getLocation().clone().add(0, 1, 0));
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        if(sit.containsKey(p.getName())) {
            sit.get(p.getName()).remove();
            sit.remove(p.getName());
            p.teleport(p.getLocation().clone().add(0, 1, 0));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if(sit.containsKey(p.getName())) {
            sit.get(p.getName()).remove();
            sit.remove(p.getName());
            p.teleport(p.getLocation().clone().add(0, 1, 0));
        }
    }

    @EventHandler
    public void onJump(PlayerJumpEvent e) {
        Block b = e.getFrom().getBlock();
        Material mat = b.getType();

        if(!b.getWorld().getName().equals("air") && new Random().nextInt(100) > 80 && (mat.equals(Material.DANDELION) || mat.equals(Material.POPPY)
        || mat.equals(Material.BLUE_ORCHID) || mat.equals(Material.ALLIUM)
        || mat.equals(Material.AZURE_BLUET) || mat.equals(Material.RED_TULIP)
        || mat.toString().contains("TULIP") || mat.equals(Material.OXEYE_DAISY)
        || mat.equals(Material.CORNFLOWER) || mat.equals(Material.LILY_OF_THE_VALLEY)
        || mat.toString().contains("MUSHROOM") || mat.toString().contains("FUNGUS"))) {
            b.breakNaturally();
        }
    }

    /*
    @EventHandler
    public void onEntityMove(EntityMoveEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Minecart) {
            Minecart minecart = (Minecart) entity;
            if (minecart.rai()) {
                // Increase the minecart's speed by 2 times
                Vector velocity = minecart.getVelocity();
                velocity.multiply(2);
                minecart.setVelocity(velocity);
            }
        }
    }
     */
}
