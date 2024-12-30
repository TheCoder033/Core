package org.kolbasa3.xcore.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.kolbasa3.xcore.utils.custom.CustomBlock;

import static org.kolbasa3.xcore.XCore.regionDB;
import static org.kolbasa3.xcore.utils.PluginUtil.dirt;

public class PistonEvent implements Listener {

    CustomBlock customBlock = new CustomBlock();

    @EventHandler
    public void onExtend(BlockPistonExtendEvent e) {
        for (Block b : e.getBlocks()) {
            if (b.getType().equals(Material.TARGET)) {
                for (String rgName : regionDB.getRegions(null)) {
                    String owner = regionDB.getOwner(rgName);
                    if (regionDB.getLoc(owner, rgName)
                            .equals(b.getLocation())) {
                        b.setType(Material.AIR);
                        regionDB.delRegion(owner, rgName);
                        return;
                    }
                }
            }

            if (dirt.contains(b.getLocation())) {
                b.setType(Material.DIRT);
                dirt.remove(b.getLocation());
                for (ArmorStand a : b.getWorld().getNearbyEntitiesByType(ArmorStand.class, b.getLocation(), 1.5)) {
                    if (!a.hasGravity() && !a.isVisible()) a.remove();
                }
            }

            if (customBlock.list().containsKey(b.getLocation())) {
                customBlock.list().forEach((l, type) -> {
                    if (l.equals(b.getLocation())) {
                        customBlock.delete(l);
                        BlockFace bf = e.getDirection();
                        if (bf.equals(BlockFace.EAST))
                            customBlock.add(l.clone().add(1, 0, 0), type);
                        else if (bf.equals(BlockFace.WEST))
                            customBlock.add(l.clone().add(-1, 0, 0), type);
                        else if (bf.equals(BlockFace.NORTH))
                            customBlock.add(l.clone().add(0, 0, -1), type);
                        else if (bf.equals(BlockFace.SOUTH))
                            customBlock.add(l.clone().add(0, 0, 1), type);
                    }
                });
            }
        }
    }

    @EventHandler
    public void onRetract(BlockPistonRetractEvent e) {
        for (Block b : e.getBlocks()) {
            if(b.getType().equals(Material.TARGET)) {
                for (String rgName : regionDB.getRegions(null)) {
                    String owner = regionDB.getOwner(rgName);
                    if(regionDB.getLoc(owner, rgName)
                            .equals(b.getLocation())) {
                        b.setType(Material.AIR);
                        regionDB.delRegion(owner, rgName);
                        return;
                    }
                }
            }

            if(dirt.contains(b.getLocation())) {
                b.setType(Material.DIRT);
                dirt.remove(b.getLocation());
                for (ArmorStand a : b.getWorld().getNearbyEntitiesByType(ArmorStand.class, b.getLocation(), 1.5)) {
                    if(!a.hasGravity() && !a.isVisible()) a.remove();
                }
            }

            if(customBlock.list().containsKey(b.getLocation())) {
                customBlock.list().forEach((l, type) -> {
                    if (l.equals(b.getLocation())) {
                        customBlock.delete(l);
                        BlockFace bf = e.getDirection();
                        if (bf.equals(BlockFace.EAST))
                            customBlock.add(l.clone().add(1, 0, 0), type);
                        else if (bf.equals(BlockFace.WEST))
                            customBlock.add(l.clone().add(-1, 0, 0), type);
                        else if (bf.equals(BlockFace.NORTH))
                            customBlock.add(l.clone().add(0, 0, -1), type);
                        else if (bf.equals(BlockFace.SOUTH))
                            customBlock.add(l.clone().add(0, 0, 1), type);
                    }
                });
            }
        }
    }
}
