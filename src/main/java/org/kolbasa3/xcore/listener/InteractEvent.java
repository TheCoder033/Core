package org.kolbasa3.xcore.listener;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.kolbasa3.xcore.XCore;
import org.kolbasa3.xcore.modules.custom.Laser;
import org.kolbasa3.xcore.modules.cases.Case;
import org.kolbasa3.xcore.modules.cases.CaseType;
import org.kolbasa3.xcore.modules.events.EventType;
import org.kolbasa3.xcore.modules.events.Events;
import org.kolbasa3.xcore.gui.region.RegionUpgradesGUI;
import org.kolbasa3.xcore.enums.Items;
import org.kolbasa3.xcore.enums.SoundType;
import org.kolbasa3.xcore.utils.region.RegionUpgrade;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.kolbasa3.xcore.XCore.playerDB;
import static org.kolbasa3.xcore.XCore.regionDB;
import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class InteractEvent implements Listener {

    Case caseUtil = new Case();
    Events events = new Events();
    RegionUpgrade regionUpgrade = new RegionUpgrade();
    private final List<String> interactCd = new ArrayList<>();
    private final ArrayList<String> plastCd = new ArrayList<>();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Block b = e.getClickedBlock();
        ItemStack is = e.getItem();
        if (interactCd.contains(player.getName())) return;

        interactCd.add(player.getName());
        Bukkit.getScheduler().runTaskLater(XCore.getInstance(), task ->
                interactCd.remove(player.getName()), 20);

        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)
                && !player.isSneaking()
                && b != null && b.getType().equals(Material.TARGET)) {
            for (String nearbyRg : regionDB.getRegions(null)) {
                String owner = regionDB.getOwner(nearbyRg);
                if ((owner.equals(player.getName()) || regionDB.getList(owner, nearbyRg, true).contains(player.getClientBrandName())) && regionDB.getLoc(owner, nearbyRg).getBlockX() == b.getLocation().getBlockX()
                        && regionDB.getLoc(owner, nearbyRg).getBlockY() == b.getLocation().getBlockY()
                        && regionDB.getLoc(owner, nearbyRg).getBlockZ() == b.getLocation().getBlockZ()) {
                    new RegionUpgradesGUI(player, nearbyRg);
                    return;
                }
            }
        }

        for (String nearbyRg : regionDB.getRegions(null)) {
            String owner = regionDB.getOwner(nearbyRg);
            if (b != null && regionDB.getLoc(owner, nearbyRg).getWorld().getName().equals(b.getWorld().getName())
                    && regionDB.getLoc(owner, nearbyRg)
                    .distance(b.getLocation()) <= regionUpgrade.getLvl(owner, nearbyRg, "size")) {
                if (!owner.equals(player.getName()) && !regionDB.getList(owner, nearbyRg, true)
                        .contains(player.getName())) {
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

        if (b != null && b.getWorld().getName().equals("air")) {
            CaseType caseType = null;
            Location caseLoc = null;
            for (CaseType type : CaseType.values()) {
                if (!cfg().contains("case." + type.toString().toLowerCase())) continue;
                caseLoc = strToLoc(Objects.requireNonNull(cfg().getString("case." + type.toString().toLowerCase()))
                        , "\\s", false)
                        .toBlockLocation().toCenterLocation();
                if (caseLoc.getBlockX() == b.getLocation().getBlockX()
                        && caseLoc.getBlockY() == b.getLocation().getBlockY()
                        && caseLoc.getBlockZ() == b.getLocation().getBlockZ()) {
                    caseType = type;
                    break;
                }
            }
            if (caseType == null) return;
            e.setCancelled(true);

            if (playerDB.getKey(player.getName(), caseType) <= 0) {
                player.sendMessage(red + "Покупка ключей: " + orange + "/donshop");
                sound(player, SoundType.LOCK);
                return;
            }

            if (caseUtil.getOpening(caseType) == null) {
                caseUtil.open(player, caseType, caseLoc);
            } else {
                player.sendMessage(red + "Кейс уже открывает " + orange + caseUtil.getOpening(caseType));
                sound(player, SoundType.WRONG);
            }
        }

        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)
                && is != null && b != null) {
            if (is.getType().equals(Material.POTION)
                    && !dirt.contains(b.getLocation())
                    && b.getType().equals(Material.DIRT)) {
                b.setType(Material.COARSE_DIRT);
                dirt.add(b.getLocation());

                int i = 0;
                for (ItemStack item : player.getInventory()) {
                    if (item != null && item.equals(is)) {
                        player.getInventory().setItem(i, new ItemStack(Material.GLASS_BOTTLE));
                        break;
                    }
                    i++;
                }

                Bukkit.getScheduler().runTaskLater(XCore.getInstance(), task -> {
                    dirt.remove(b.getLocation());
                    b.getWorld().getNearbyEntitiesByType(ArmorStand.class, b.getLocation(), 1.5).forEach(a -> {
                        if (!a.hasGravity() && !a.isVisible()) a.remove();
                    });
                    if (b.getType().equals(Material.COARSE_DIRT)) b.setType(Material.DIRT);
                }, 1200);

            } else if ((is.getType().equals(Material.FLINT_AND_STEEL)
                    || is.getType().equals(Material.FIRE_CHARGE)
                    || is.getType().equals(Material.LAVA_BUCKET))
                    && dirt.contains(b.getLocation())
                    && b.getType().equals(Material.COARSE_DIRT)) {
                if (is.getType().equals(Material.LAVA_BUCKET)) e.setCancelled(true);
                b.setType(Material.DIRT);
                dirt.remove(b.getLocation());
                b.getWorld().getNearbyEntitiesByType(ArmorStand.class, b.getLocation(), 1.5).forEach(a -> {
                    if (!a.hasGravity() && !a.isVisible()) a.remove();
                });

                int i = 0;
                for (ItemStack item : player.getInventory()) {
                    if (item != null && item.equals(is)) {
                        if (is.getType().equals(Material.LAVA_BUCKET)) {
                            player.getInventory().setItem(i, new ItemStack(Material.BUCKET));
                            break;
                        }
                    }
                    i++;
                }
            }
        }

        if (is != null && is.isSimilar(Items.PLAST.get(null))
                && !plastCd.contains(player.getName())) {
            e.setCancelled(true);
            plastCd.add(player.getName());
            is.setAmount(is.getAmount() - 1);

            Location loc = player.getLocation();
            ArrayList<Location> locList = new ArrayList<>();
            for (int x = loc.getBlockX() - 1; x <= loc.getBlockX() + 1; x++) {
                for (int z = loc.getBlockZ() - 1; z <= loc.getBlockZ() + 1; z++) {
                    Block block = loc.getWorld().getBlockAt(x, loc.getBlockY() - 1, z);
                    if (block.getType().equals(Material.AIR)) {
                        block.setType(Material.OBSIDIAN);
                        BlockState blockState = block.getState();
                        blockState.setMetadata("plast", new FixedMetadataValue(XCore.getInstance(), "plast"));
                        blockState.update();
                        locList.add(block.getLocation());
                    }
                }
            }

            Bukkit.getScheduler().runTaskLater(XCore.getInstance(), task ->
                    locList.forEach(loc2 -> {
                        plastCd.remove(player.getName());
                        if (loc2.getBlock().getType().equals(Material.OBSIDIAN))
                            loc2.getBlock().setType(Material.AIR);
                    }), 80);
        }

        if (b != null && is != null
                && b.getType().equals(Material.SPAWNER)
                && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)
                && (is.getType().equals(Material.CREEPER_SPAWN_EGG)
                || is.getType().equals(Material.VILLAGER_SPAWN_EGG)
                || is.getType().equals(Material.WANDERING_TRADER_SPAWN_EGG)))
            e.setCancelled(true);

        if (events.getLoc() != null && b != null
                && b.getType().equals(Material.CHEST)
                && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

            if (b.getLocation().equals(events.getLoc())
                    && events.getTime() < 301) {
                e.setCancelled(true);
                player.playSound(player.getLocation(), Sound.BLOCK_CHEST_LOCKED, 0.3F, 0.3F);

            } else if (events.getTime() >= 301 && events.getHologram(true) != null && !events.getHologram(true).isCustomNameVisible()) {
                if(events.getHologram(false) != null) events.getHologram(true).setCustomName(events.getHologram(false).getCustomName());
                events.getHologram(true).setCustomNameVisible(true);

                if (!events.itemsEmpty()) events.generateLoot();

                String str = "";
                if (events.getEvent().equals(EventType.WATERCHEST)) str = "о";
                events.getHologram(false).setCustomName("§7Открыт" + str + ": " + player.getName());
            }
        }

        if(b != null && b.getType().equals(Material.HEAVY_WEIGHTED_PRESSURE_PLATE)
                && b.getState().hasMetadata("jump-plate")
        && e.getAction().equals(Action.PHYSICAL)) {
            player.setVelocity(new Vector(0, 1, 0));
        }

        if((e.getAction().equals(Action.RIGHT_CLICK_AIR)
                || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
                && is != null
        && (is.isSimilar(posoh(true)) || is.isSimilar(posoh(false)))) {
            Location playerLocation = player.getEyeLocation();
            Vector direction = playerLocation.getDirection();
            new Laser(playerLocation, direction, is.isSimilar(posoh(true)));
        }
    }

    @EventHandler
    public void onInteract(EntityInteractEvent e) {
        Entity ent = e.getEntity();
        Block block = e.getBlock();
        if(block.getType().equals(Material.HEAVY_WEIGHTED_PRESSURE_PLATE)
                && block.getState().hasMetadata("jump-plate")) {
            ent.setVelocity(new Vector(0, 1, 0));
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e) {
        Player p = e.getPlayer();

        if(e.getRightClicked().getWorld().getName().equals("air")) {
            if(e.getRightClicked() instanceof ArmorStand a && !interactCd.contains(p.getName())) {
                NamespacedKey key = new NamespacedKey(XCore.getInstance(), "custom-head");
                if(a.getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
                    e.setCancelled(true);
                    interactCd.add(p.getName());
                    Bukkit.getScheduler().runTaskLater(XCore.getInstance(), task ->
                            interactCd.remove(p.getName()), 20);
                    String str = "";
                    if(Objects.equals(a.getPersistentDataContainer().get(key, PersistentDataType.STRING), "ds")) str = "Discord сервера §7» "+orange+"https://discord.gg/rGWEnjEu";
                    else if(Objects.equals(a.getPersistentDataContainer().get(key, PersistentDataType.STRING), "don")) str = "Сайт сервера §7» "+orange+"sw1.easydonate.ru";
                    p.sendMessage(str);
                    return;
                }
            }

            for(String str : cfg().getStringList("npc")) {
                String[] str2 = str.split(",");
                if(str2[0].equals(locToStr(e.getRightClicked().getLocation(), " ", false))) {
                    e.setCancelled(true);
                    p.performCommand(str2[2]);
                    return;
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInteract2(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Block b = e.getClickedBlock();
        ItemStack is = e.getItem();

        if(!e.isCancelled() && is != null && (b == null || b.getType().equals(Material.AIR)
                || !b.getType().toString().contains("CHEST"))) {
            int addedExp = 0;
            if(is.isSimilar(Items.EXP.get("15"))) addedExp = 15;
            else if(is.isSimilar(Items.EXP.get("20"))) addedExp = 20;
            else if(is.isSimilar(Items.EXP.get("30"))) addedExp = 30;
            player.setLevel(player.getLevel()+addedExp);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onConsume(PlayerItemConsumeEvent e) {
        if(e.isCancelled()) return;

        Player player = e.getPlayer();
        ItemStack is = e.getItem();

        if(is.isSimilar(Items.COFFEE.get(null))) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000, 2));
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 6000, 2));

        } else if(is.isSimilar(Items.TEA.get(null))) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 6000, 1));

        } else if(is.isSimilar(Items.BEER.get(null))) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 18000, 2));
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 18000, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 18000, 1));
        }
    }
}
