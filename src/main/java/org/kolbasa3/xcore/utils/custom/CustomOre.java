package org.kolbasa3.xcore.utils.custom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;
import org.kolbasa3.xcore.XCore;
import org.kolbasa3.xcore.utils.Schematic;

import java.util.Random;

import static org.kolbasa3.xcore.utils.PluginUtil.customOre;

public class CustomOre {

    private  final Schematic schematic = new Schematic();

    public void spawn(Location center) {
        if(Bukkit.getTPS()[0] > 15 && center.getY() < 30
                && new Random().nextInt(100) > 98) {
            for (int x = (int) center.getX() - 8; x < center.getX() + 8; x++) {
                for (int z = (int) center.getZ() - 8; z < center.getZ() + 8; z++) {
                    for (int y = (int) center.getY() - 8; y < center.getY() + 8; y++) {
                        Location pos = new Location(center.getWorld(), x, y, z);
                        if (pos.equals(center)
                                || !pos.getBlock().getType().equals(Material.STONE)) continue;

                        String type = "ruby";
                        if(new Random().nextInt(100) > 70) type = "ametist";
                        schematic.paste(pos, type);
                        pos.getBlock().setMetadata(type, new FixedMetadataValue(XCore.getInstance(), type));
                        return;
                    }
                }
            }
        }
    }

    public boolean delete(Location center) {
        Block block = center.getBlock();
        if(block.getType().equals(Material.PLAYER_HEAD)
                && (block.hasMetadata("ruby") || block.hasMetadata("ametist"))) {
            center.getWorld().dropItemNaturally(center, customOre(block.hasMetadata("ruby")));
            return true;
        }
        return false;
    }
}
