package org.kolbasa3.xcore.listener;

import com.destroystokyo.paper.event.block.TNTPrimeEvent;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.kolbasa3.xcore.XCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.kolbasa3.xcore.utils.custom.CustomBlock;

import java.util.Objects;
import java.util.Random;

import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class ExplodeEvent implements Listener {

    CustomBlock customBlock = new CustomBlock();

    @EventHandler
    public void onExplode(EntityExplodeEvent e) {
        for(Block b : e.blockList()) {
            /*
            if(blockDB.getWrited().contains(b.getLocation()))
                blockDB.delPiston(b.getLocation());
             */

            if(dirt.contains(b.getLocation())) {
                b.setType(Material.DIRT);
                dirt.remove(b.getLocation());
                for (ArmorStand a : b.getWorld().getNearbyEntitiesByType(ArmorStand.class, b.getLocation(), 1.5)) {
                    if(!a.hasGravity() && !a.isVisible()) a.remove();
                }
            }
            for (ArmorStand a : b.getWorld().getNearbyEntitiesByType(ArmorStand.class, b.getLocation(), 1.5)) {
                if(!a.hasGravity() && !a.isVisible()) a.remove();
            }
            if(b.getType().equals(Material.TNT)) {
                customBlock.delete(b.getLocation());
            }
        }

        if(!(e.getEntity() instanceof TNTPrimed)
        || (!Objects.equals(e.getEntity().getCustomName(), "Obs-tnt")
                && !Objects.equals(e.getEntity().getCustomName(), "Water-tnt"))) return;
        Location loc = e.getLocation();

        if(Objects.equals(e.getEntity().getCustomName(), "Obs-tnt")) {
            if(loc.clone().add(0, 1, 0).getBlock().getType().equals(Material.WATER)) return;
            for (int x = (int) loc.getX() - 1; x < loc.getX() + 2; x++) {
                for (int z = (int) loc.getZ() - 1; z < loc.getZ() + 2; z++) {
                    for (int y = (int) loc.getY() - 2; y < loc.getY() + 2; y++) {
                        Location pos = new Location(loc.getWorld(), x, y, z);
                        if(pos.getBlock().getType().equals(Material.WATER)) return;
                        int i = 70;
                        if(pos.getBlock().getType().equals(Material.CRYING_OBSIDIAN)) i = 30;
                        if (new Random().nextInt(100) < i
                                && (pos.getBlock().getType().equals(Material.OBSIDIAN)
                                || pos.getBlock().getType().equals(Material.CRYING_OBSIDIAN))) {
                            Material m = pos.getBlock().getType();
                            pos.getBlock().setType(Material.AIR);
                            pos.getWorld().dropItemNaturally(pos, new ItemStack(m));
                        }
                    }
                }
            }
        } else if(Objects.equals(e.getEntity().getCustomName(), "Water-tnt")) {
            for (int x = (int) loc.getX() - 2; x < loc.getX() + 3; x++) {
                for (int z = (int) loc.getZ() - 2; z < loc.getZ() + 3; z++) {
                    for (int y = (int) loc.getY() - 2; y < loc.getY() + 3; y++) {
                        Location pos = new Location(loc.getWorld(), x, y, z);
                        if (new Random().nextInt(100) < 70
                                && !pos.getBlock().getType().equals(Material.AIR)
                                && !pos.getBlock().getType().equals(Material.OBSIDIAN)
                                && !pos.getBlock().getType().equals(Material.CRYING_OBSIDIAN)
                                && !pos.getBlock().getType().equals(Material.BEDROCK)) {
                            pos.getBlock().breakNaturally();
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPrime(TNTPrimeEvent e) {
        Location l = e.getBlock().getLocation();

        customBlock.list().forEach((loc, type) -> {
            if (l.distance(loc) < 0.1) {
                Bukkit.getScheduler().runTaskLater(XCore.getInstance(), task -> {
                    for (TNTPrimed tnt : l.getWorld().getNearbyEntitiesByType(TNTPrimed.class, l, 0.2)) {
                        String str = "Obs";
                        if(type.equals("water-tnt")) str = "Water";
                        tnt.setCustomName(str+"-tnt");
                        customBlock.delete(loc);
                    }
                }, 1);
            }
        });
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if(!e.getBlockPlaced().getType().equals(Material.TNT)) return;
        ItemStack is = e.getItemInHand();
        if(is.getItemMeta() == null || is.getItemMeta().getDisplayName().isEmpty()
        || is.getLore() == null) return;

        ItemMeta meta = is.getItemMeta();
        Location l = e.getBlockPlaced().getLocation();

        String type = "water";
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(XCore.getInstance(), "custom-block");

        if(pdc.has(key, PersistentDataType.STRING)) type = "obsidian";
        customBlock.add(l, type+"-tnt");
    }
}
