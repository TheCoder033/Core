package org.kolbasa3.xcore.listener;

import com.google.common.collect.Lists;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.kolbasa3.xcore.XCore;
import org.kolbasa3.xcore.utils.EnchUtil;
import org.kolbasa3.xcore.utils.ItemUtil;
import org.kolbasa3.xcore.utils.region.RegionUpgrade;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.kolbasa3.xcore.XCore.playerDB;
import static org.kolbasa3.xcore.XCore.regionDB;
import static org.kolbasa3.xcore.listener.PhysicsEvent.angryMobs;
import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class DamageEvent implements Listener {

    RegionUpgrade regionUpgrade = new RegionUpgrade();
    EnchUtil enchUtil = new EnchUtil();

    public static HashMap<String, String> pvp = new HashMap<>();
    public static HashMap<String, Integer> pvp_cd = new HashMap<>();
    public static HashMap<String, BossBar> pvp_bossbar = new HashMap<>();
    public static boolean pvpTasked;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof LightningStrike
                && e.getEntity() instanceof Player p) {
            if(p.getWorld().getName().equals("air") && !inPvPArena(p)) {
                e.setCancelled(true);
                e.setDamage(0);
                return;
            }
            ItemStack is = p.getInventory().getHelmet();
            if(is != null) {
                List<String> ench = enchUtil.list(is);
                if (ench.contains("громоотвод")
                        && new Random().nextInt(100) > 70) {
                    e.setCancelled(true);
                    return;
                }
            }
            p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100, 1));
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 230, 1));
            p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 250, 1));
        }

        if(e.getEntity() instanceof Player t && e.getDamager() instanceof Player p) {
            if(p.getWorld().getName().equals("air") && t.getWorld().getName().equals("air")
                    && (!inPvPArena(p) || !inPvPArena(t))) {
                e.setCancelled(true);
                e.setDamage(0);
                return;
            }

            for(String nearbyRg : regionDB.getRegions(null)) {
                String owner = regionDB.getOwner(nearbyRg);
                if(regionDB.getLoc(owner, nearbyRg).getWorld().getName().equals(p.getWorld().getName())
                && regionDB.getLoc(owner, nearbyRg)
                        .distance(p.getLocation()) < regionUpgrade.getLvl(owner, nearbyRg, "size")
                        && regionDB.getLoc(owner, nearbyRg).getWorld().getName().equals(t.getWorld().getName())
                && regionDB.getLoc(owner, nearbyRg)
                        .distance(t.getLocation()) < regionUpgrade.getLvl(owner, nearbyRg, "size")) {
                    if (owner.equals(p.getName()) || regionDB.getList(owner, nearbyRg, true)
                            .contains(p.getName())
                            && (owner.equals(t.getName()) || regionDB.getList(owner, nearbyRg, true)
                            .contains(t.getName()))) {
                        List<String> upgrades = regionDB.getList(owner, nearbyRg, false);
                        if(upgrades.contains("pvpOff")) {
                            e.setCancelled(true);
                            return;
                        }
                    }
                    return;
                }
            }

            ItemStack is = p.getInventory().getItemInMainHand();
            if (!doubleExp.contains(e.getEntity()) && is.hasItemMeta() && is.getItemMeta().hasLore()
                    && enchUtil.list(is).contains("опыт")) {
                doubleExp.add(e.getEntity());

                Bukkit.getScheduler().runTaskLater(XCore.getInstance(), task ->
                        doubleExp.remove(e.getEntity()), 120);
            }

            if (is.getType().toString().contains("SWORD")) {
                List<String> ench = enchUtil.list(is);
                Random random = new Random();

                if (ench.contains("отравление") && random.nextBoolean())
                    t.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 60, 1));

                if(ench.contains("гнев_зевса") && random.nextInt(100) > 70)
                    t.getWorld().strikeLightning(t.getLocation());

                if(ench.contains("заражение") && random.nextBoolean()) {
                    t.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1));
                    t.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 30, 1));

                    t.getLocation().getNearbyPlayers(3).forEach(nearby -> {
                        nearby.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1));
                        nearby.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 30, 1));
                    });
                }
            }

            is = t.getInventory().getChestplate();
            if(is != null) {
                List<String> ench = enchUtil.list(is);
                if (ench.contains("защитная_аура")
                        && t.getHealth() <= 3 && new Random().nextInt(100) > 70) {
                    e.setCancelled(true);
                    return;
                }
            }

            pvp.put(e.getDamager().getName(), t.getName());
            pvp_cd.put(t.getName(), 30);
            pvp_cd.put(e.getDamager().getName(), 30);
            if(!pvpTasked) {
                pvpTasked = true;
                Bukkit.getScheduler().runTaskTimer(XCore.getInstance(), task -> {
                    AtomicBoolean stopped = new AtomicBoolean(false);
                    new ArrayList<>(pvp_cd.keySet()).stream()
                            .filter(s -> !stopped.get())
                            .forEach(s -> {
                                if (pvp_cd.get(s) <= 1) {
                                    if (pvp_bossbar.containsKey(s)) pvp_bossbar.get(s).removeAll();
                                    pvp_bossbar.remove(s);
                                    pvp_cd.remove(s);
                                    pvp.remove(s);

                                    if (pvp_cd.isEmpty()) {
                                        pvpTasked = false;
                                        stopped.set(true);
                                        task.cancel();
                                    }
                                } else {
                                    pvp_cd.put(s, (pvp_cd.get(s) - 1));
                                    Player player = Bukkit.getPlayer(s);
                                    if (player != null) {
                                        if (pvp_bossbar.containsKey(player.getName())) {
                                            BossBar b = pvp_bossbar.get(player.getName());
                                            b.setTitle(hex("PvP-режим: "+red + pvp_cd.get(s) + "сек."));
                                            pvp_bossbar.put(player.getName(), b);
                                            return;
                                        }
                                        BossBar b = Bukkit.createBossBar(hex("PvP-режим: "+red + pvp_cd.get(s) + "сек."), BarColor.RED, BarStyle.SOLID);
                                        b.setVisible(true);
                                        b.addPlayer(player);
                                        pvp_bossbar.put(player.getName(), b);
                                        b.setProgress((b.getProgress() - 0.0333333333333333));
                                    }
                                }
                            });
                }, 0, 20);
            }
            
            p.sendActionBar("Урон &7» "+orange+e.getDamage());
        }

        if(e.getEntity() instanceof Player player
                && angryMobs.contains(e.getDamager())
        && new Random().nextInt(100) > 80) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 3600, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 1000, 1));
            player.sendMessage(red+"Вы заразились бешенством!");
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        e.setCancelled(e.getEntity() instanceof Player p && p.getWorld().getName().equals("air")
        && e.getCause().equals(EntityDamageEvent.DamageCause.FALL));
    }

    @EventHandler
    public void onDamage(EntityDamageByBlockEvent e) {
        if (!(e.getEntity() instanceof Player)
                || e.getDamager() == null
                || !(e.getDamager().getType().equals(Material.CACTUS))) return;

        if (new Random().nextBoolean()) {
            Player p = (Player) e.getEntity();
            p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 160, 1));
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1));
            p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 1));
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        e.deathMessage(Component.text(""));

        double i = (double) (playerDB.getMoney(p.getName()) * 70) /100;
        playerDB.setMoney(p.getName(), (int) (playerDB.getMoney(p.getName())-i));

        e.getDrops().add(new ItemUtil(Material.GOLD_INGOT
                , "", Lists.newArrayList(hex("§0"+i))).build(null));
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        Entity ent = e.getEntity();
        EntityType et = e.getEntityType();

        if(Bukkit.getTPS()[0] > 17) {
            ent.getWorld().spawnParticle(Particle.BLOCK_CRACK, ent.getLocation().clone().add(0, 0.5, 0), 10, Material.REDSTONE_BLOCK.createBlockData());
            ent.getLocation().getNearbyPlayers(5).forEach(nearby ->
                    nearby.playSound(ent.getLocation(), Sound.BLOCK_STONE_STEP, 1, 1));
        }
        if(ent instanceof Player) return;

        int i = 30;
        if(et.equals(EntityType.ZOMBIE)) i = 40;
        else if(et.equals(EntityType.SKELETON)) i = 50;
        else if(et.equals(EntityType.CREEPER)) i = 70;
        else if(et.equals(EntityType.BLAZE)) i = 80;

        if(!et.equals(EntityType.ARMOR_STAND)) e.getDrops().add(new ItemUtil(Material.GOLD_NUGGET
                , "", Lists.newArrayList(hex("§0"+i))).build(null));

        if(doubleExp.contains(e.getEntity())) {
            doubleExp.remove(e.getEntity());
            e.setDroppedExp(e.getDroppedExp()*2);
        }
    }

    @EventHandler
    public void onPickUp(PlayerAttemptPickupItemEvent e) {
        if((!e.getItem().getItemStack().getType().equals(Material.GOLD_NUGGET)
                && !e.getItem().getItemStack().getType().equals(Material.GOLD_INGOT))
                || !e.getItem().getItemStack().getItemMeta().hasLore()) return;
        Player p = e.getPlayer();
        e.setCancelled(true);
        double i = Double.parseDouble(Objects.requireNonNull(e.getItem().getItemStack().getItemMeta().getLore()).get(0).replace("§0", ""));

        if(e.getItem().getItemStack().getType().equals(Material.GOLD_NUGGET)) {
            if (p.hasPermission("mobbooster6")) i *= 6;
            else if (p.hasPermission("mobbooster4")) i *= 4;
            else if (p.hasPermission("mobbooster2")) i *= 2;
        }
        e.getItem().remove();

        playerDB.setMoney(p.getName(), (int) (playerDB.getMoney(p.getName())+i));
        p.sendActionBar(hex("Вы подобрали "+ yellow + i + "⛁"));
    }

    @EventHandler
    public void onHopper(InventoryPickupItemEvent e) {
        if(!e.getInventory().getType().equals(InventoryType.HOPPER)
                || (!e.getItem().getItemStack().getType().equals(Material.GOLD_NUGGET)
                && !e.getItem().getItemStack().getType().equals(Material.GOLD_INGOT))
                || !e.getItem().getItemStack().getItemMeta().hasLore()) return;
        e.setCancelled(true);
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage2(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player p
                && e.getEntity().getWorld().getName().equals("air")) {
            for(String str : cfg().getStringList("npc")) {
                String[] str2 = str.split(",");
                if(str2[0].equals(locToStr(e.getEntity().getLocation(), " ", false))) {
                    e.setCancelled(true);
                    e.setDamage(0);
                    p.performCommand(str2[2]);
                    return;
                }
            }
        }
    }

    private boolean inPvPArena(Player p) {
        Location pvpZone = regionDB.getLoc("Kolbasa3", "pvp");
        return pvpZone.getWorld().getName().equals(p.getWorld().getName())
                && pvpZone.distance(p.getLocation()) <= 25;
    }
}
