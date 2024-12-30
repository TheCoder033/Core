package org.kolbasa3.xcore.listener;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.kolbasa3.xcore.XCore;
import org.kolbasa3.xcore.enums.SoundType;
import org.kolbasa3.xcore.modules.events.EventType;
import org.kolbasa3.xcore.modules.events.Events;
import org.kolbasa3.xcore.modules.jobs.WoodJob;
import org.kolbasa3.xcore.utils.*;
import org.kolbasa3.xcore.utils.custom.CustomBlock;
import org.kolbasa3.xcore.utils.custom.CustomOre;
import org.kolbasa3.xcore.enums.Items;
import org.kolbasa3.xcore.utils.region.RegionUpgrade;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.kolbasa3.xcore.XCore.regionDB;
import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class BlockEvent implements Listener {

    RegionUpgrade regionUpgrade = new RegionUpgrade();
    Events events = new Events();
    EnchUtil enchUtil = new EnchUtil();
    WoodJob woodJob = new WoodJob();
    CustomOre customOre = new CustomOre();
    CustomBlock customBlock = new CustomBlock();

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();

        if(b.getWorld().getName().equals("air") && b.getType().equals(Material.HAY_BLOCK)) {
            e.setDropItems(false);
            p.getInventory().addItem(Items.ARTIFACT.get(null));
            return;
        }

        AtomicBoolean find = new AtomicBoolean(false);
        if (b.getType().equals(Material.OAK_LOG)) {
            woodJob.getLocList().stream()
                    .filter(loc -> !find.get())
                    .forEach(loc -> {
                        if (loc.equals(b.getLocation())) {
                            find.set(true);
                            e.setDropItems(false);
                            woodJob.breakTree(loc, p);
                        }
                    });
        }
        if (!find.get() && !p.hasPermission("air.build") && b.getWorld().getName().equals("air")) e.setCancelled(true);

        if (!events.getEvent().equals(EventType.NULL) && events.getLoc() != null
                && b.getWorld().getName().equals(events.getLoc().getWorld().getName())
                && events.getLoc().distance(b.getLocation()) < 30) {
            e.setCancelled(true);
            return;
        }

        for (String nearbyRg : regionDB.getRegions(null)) {
            String owner = regionDB.getOwner(nearbyRg);
            if (regionDB.getLoc(owner, nearbyRg).getWorld().getName().equals(b.getWorld().getName())
                    && regionDB.getLoc(owner, nearbyRg)
                    .distance(b.getLocation()) <= regionUpgrade.getLvl(owner, nearbyRg, "size")) {
                if (!owner.equals(p.getName()) && !regionDB.getList(owner, nearbyRg, true)
                        .contains(p.getName())) {
                    e.setCancelled(true);
                    return;
                } else if (b.getType().equals(Material.TARGET)
                        && regionDB.getLoc(owner, nearbyRg).getBlockX() == b.getLocation().getBlockX()
                        && regionDB.getLoc(owner, nearbyRg).getBlockY() == b.getLocation().getBlockY()
                        && regionDB.getLoc(owner, nearbyRg).getBlockZ() == b.getLocation().getBlockZ()) {
                    regionDB.delRegion(owner, nearbyRg);
                } else {
                    List<String> upgrades = regionDB.getList(owner, nearbyRg, false);
                    if (upgrades.contains("destroyOff")) {
                        e.setCancelled(true);
                        return;
                    }
                }
            }
        }

        if (dirt.contains(e.getBlock().getLocation())) {
            e.getBlock().setType(Material.DIRT);
            dirt.remove(e.getBlock().getLocation());
            for (ArmorStand a : e.getBlock().getWorld().getNearbyEntitiesByType(ArmorStand.class, e.getBlock().getLocation(), 1.5)) {
                if (!a.hasGravity() && !a.isVisible()) a.remove();
            }
        }

        if (customBlock.list().containsKey(b.getLocation()))
            customBlock.delete(b.getLocation());

        if(b.hasMetadata("plast")) {
            e.setCancelled(true);
            return;
        }

        ItemStack is = p.getInventory().getItemInMainHand();
        Location loc = b.getLocation();
        if (!is.getType().equals(Material.AIR) && is.getLore() != null && !e.isCancelled()) {
            List<String> ench = enchUtil.list(is);
            String type = is.getType().toString();

            if (type.contains("AXE")
                    && !type.contains("PICKAXE")
                    && ench.contains("магнетизм")) {
                e.setDropItems(false);
                e.getBlock().getDrops(is).forEach(item -> p.getInventory().addItem(item));
            }

            if (type.contains("PICKAXE")
                    && ench.contains("опыт"))
                e.setExpToDrop(e.getExpToDrop() * 2);

            if (type.contains("PICKAXE")
                    && ench.contains("бур")) {
                for (int x = (int) loc.getX() - 1; x < loc.getX() + 2; x++) {
                    for (int z = (int) loc.getZ() - 1; z < loc.getZ() + 2; z++) {
                        for (int y = (int) loc.getY() - 1; y < loc.getY() + 2; y++) {
                            Location pos = new Location(loc.getWorld(), x, y, z);
                            if (pos.equals(loc)) continue;
                            if (!pos.getBlock().getType().equals(Material.BEDROCK) && !pos.getBlock().hasMetadata("plast")) {
                                if (ench.contains("магнетизм")
                                        && p.getInventory().firstEmpty() != -1) {
                                    pos.getBlock().getDrops(is).forEach(item -> {
                                        if (ench.contains("плавильня")) {
                                            if (item.getType().equals(Material.COAL_ORE))
                                                item.setType(Material.COAL);
                                            else if (item.getType().equals(Material.IRON_ORE))
                                                item.setType(Material.IRON_INGOT);
                                            else if (item.getType().equals(Material.GOLD_ORE))
                                                item.setType(Material.GOLD_INGOT);
                                        }
                                        p.getInventory().addItem(item);
                                        pos.getBlock().setType(Material.AIR);
                                    });

                                } else {
                                    pos.getBlock().getDrops(is).forEach(item -> {
                                        if (ench.contains("плавильня")) {
                                            if (item.getType().equals(Material.COAL_ORE))
                                                item.setType(Material.COAL);
                                            else if (item.getType().equals(Material.IRON_ORE))
                                                item.setType(Material.IRON_INGOT);
                                            else if (item.getType().equals(Material.GOLD_ORE))
                                                item.setType(Material.GOLD_INGOT);
                                        }
                                        pos.getWorld().dropItemNaturally(pos, item);
                                        pos.getBlock().setType(Material.AIR);
                                    });
                                }
                            }
                        }
                    }
                }
            } else if (type.contains("PICKAXE")
                    && ench.contains("магнетизм")
                    && p.getInventory().firstEmpty() != -1) {
                e.getBlock().getDrops(is).forEach(item -> {
                    if (ench.contains("плавильня")) {
                        if (item.getType().equals(Material.COAL_ORE)) item.setType(Material.COAL);
                        else if (item.getType().equals(Material.IRON_ORE)) item.setType(Material.IRON_INGOT);
                        else if (item.getType().equals(Material.GOLD_ORE)) item.setType(Material.GOLD_INGOT);
                    }
                    p.getInventory().addItem(item);
                });
            } else if (type.contains("PICKAXE")) {
                List<ItemStack> list = new ArrayList<>();
                e.getBlock().getDrops(is).forEach(item -> {
                    if (ench.contains("плавильня")) {
                        if (item.getType().equals(Material.COAL_ORE))
                            item.setType(Material.COAL);
                        else if (item.getType().equals(Material.IRON_ORE))
                            item.setType(Material.IRON_INGOT);
                        else if (item.getType().equals(Material.GOLD_ORE))
                            item.setType(Material.GOLD_INGOT);
                    }
                    list.add(item);
                });
                e.setDropItems(false);
                list.forEach(item -> loc.getWorld().dropItemNaturally(loc, item));
            }
        }

        customOre.spawn(p.getLocation());
        if (customOre.delete(b.getLocation())) e.setDropItems(false);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();
        ItemStack is = e.getItemInHand();

        if (!p.hasPermission("air.build") && b.getWorld().getName().equals("air")) {
            e.setCancelled(true);
            return;
        }

        if (!events.getEvent().equals(EventType.NULL) && events.getLoc() != null
                && b.getWorld().getName().equals(events.getLoc().getWorld().getName())
                && events.getLoc().distance(b.getLocation()) < 30) {
            e.setCancelled(true);
            return;
        }

        for (String nearbyRg : regionDB.getRegions(null)) {
            String owner = regionDB.getOwner(nearbyRg);
            if (regionDB.getLoc(owner, nearbyRg).getWorld().getName().equals(b.getWorld().getName())
                && regionDB.getLoc(owner, nearbyRg)
                    .distance(b.getLocation()) < regionUpgrade.getLvl(owner, nearbyRg, "size")) {
                if (!owner.equals(p.getName()) && !regionDB.getList(owner, nearbyRg, true)
                        .contains(p.getName())) {
                    e.setCancelled(true);
                    return;
                } else {
                    List<String> upgrades = regionDB.getList(owner, nearbyRg, false);
                    if (upgrades.contains("buildOff")) {
                        e.setCancelled(true);
                        return;
                    }
                }
            }
        }

        if (!e.isCancelled() && is.hasItemMeta()
                && is.getType().equals(Material.HEAVY_WEIGHTED_PRESSURE_PLATE)) {
            BlockState blockState = b.getState();
            blockState.setMetadata("jump-plate", new FixedMetadataValue(XCore.getInstance(), "jump-plate"));
            blockState.update();
            return;
        }

        if (is.getType().equals(Material.PLAYER_HEAD)
                && (is.isSimilar(customOre(true))
                || is.isSimilar(customOre(false)))) {
            e.setCancelled(true);
            return;
        }

        if (!e.isCancelled() && is.getType().equals(Material.TARGET)
                && is.getItemMeta() != null
                && is.getItemMeta().getLore() != null
                && is.containsEnchantment(Enchantment.DURABILITY)) {

            for (String nearbyRg : regionDB.getRegions(null)) {
                String owner = regionDB.getOwner(nearbyRg);
                if (regionDB.getLoc(owner, nearbyRg).getWorld().getName().equals(b.getWorld().getName())
                        && regionDB.getLoc(owner, nearbyRg)
                        .distance(b.getLocation()) < regionUpgrade.getLvl(owner, nearbyRg, "size")) {
                    e.setCancelled(true);
                    p.sendMessage(red + "Рядом есть какой-то регион, выберите другое место.");
                    sound(p, SoundType.WRONG);
                    return;
                }
            }

            String rgName = is.getItemMeta().getLore().get(0).split("\\s")[1]
                    .replace("§x§3§5§C§B§F§F", "");

            int maxSize = 2;
            if (p.hasPermission("rg.3")) maxSize = 3;
            else if (p.hasPermission("rg.4")) maxSize = 4;
            else if (p.hasPermission("rg.5")) maxSize = 5;
            else if (p.hasPermission("rg.6")) maxSize = 6;
            else if (p.hasPermission("rg.7")) maxSize = 7;

            if (regionDB.getRegions(p.getName()).size() > maxSize) {
                e.setCancelled(true);
                p.sendMessage("");
                p.sendMessage(red + "Вы превысили максимальное количество регионов.");
                p.sendMessage("Расширить ограничение §7» " + azure + "/don");
                p.sendMessage("");
                sound(p, SoundType.WRONG);
                return;
            }

            if (regionDB.getRegions(p.getName()).contains(rgName)) {
                e.setCancelled(true);
                p.sendMessage("");
                p.sendMessage(red + "Регион " + orange + rgName + red + " уже существует.");
                p.sendMessage("Удалить регион §7» " + azure + "/rg del " + rgName);
                p.sendMessage("");
                sound(p, SoundType.WRONG);
                return;
            }

            regionDB.createRegion(p.getName(), rgName, b.getLocation());
            p.sendMessage("");
            p.sendMessage("Регион " + orange + rgName + " §fуспешно создан.");
            p.sendMessage("Список команд §7» " + azure + "/rg");
            p.sendMessage("");
            sound(p, SoundType.SUCCESS);
        }
    }

    @EventHandler
    public void onDestroy(BlockDestroyEvent e) {
        Block b = e.getBlock();

        if(b.hasMetadata("plast")) {
            e.setCancelled(true);
            b.setType(Material.AIR);
            return;
        }

        if (b.getType().equals(Material.TARGET)) {
            for (String rgName : regionDB.getRegions(null)) {
                String owner = regionDB.getOwner(rgName);
                if (regionDB.getLoc(owner, rgName)
                        .equals(b.getLocation())) {
                    regionDB.delRegion(owner, rgName);
                    return;
                }
            }
        }

        if (dirt.contains(b.getLocation())) {
            e.getBlock().setType(Material.DIRT);
            dirt.remove(b.getLocation());
            for (ArmorStand a : b.getWorld().getNearbyEntitiesByType(ArmorStand.class, b.getLocation(), 1.5)) {
                if (!a.hasGravity() && !a.isVisible()) a.remove();
            }
        }

        if (customBlock.list().containsKey(b.getLocation()))
            customBlock.delete(b.getLocation());
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent e) {
        for (Block b : e.blockList()) {
            if(b.hasMetadata("plast")) {
                e.setCancelled(true);
                b.setType(Material.AIR);
                continue;
            }

            if (b.getType().equals(Material.TARGET)) {
                for (String nearbyRg : regionDB.getRegions(null)) {
                    String owner = regionDB.getOwner(nearbyRg);
                    if (regionDB.getLoc(owner, nearbyRg).getBlockX() == b.getLocation().getBlockX()
                            && regionDB.getLoc(owner, nearbyRg).getBlockY() == b.getLocation().getBlockY()
                            && regionDB.getLoc(owner, nearbyRg).getBlockZ() == b.getLocation().getBlockZ())
                        regionDB.delRegion(owner, nearbyRg);
                }
            }
        }
    }
}
