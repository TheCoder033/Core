package org.kolbasa3.xcore;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import org.kolbasa3.xcore.cmds.tp.AcceptCMD;
import org.kolbasa3.xcore.cmds.tp.DenyCMD;
import org.kolbasa3.xcore.cmds.tp.TpCMD;
import org.kolbasa3.xcore.cmds.tp.TpaCMD;
import org.kolbasa3.xcore.modules.bosses.BossListener;
import org.kolbasa3.xcore.modules.bosses.BossStarter;
import org.kolbasa3.xcore.cmds.homes.DelHomeCMD;
import org.kolbasa3.xcore.cmds.homes.HomeCMD;
import org.kolbasa3.xcore.cmds.homes.HomesCMD;
import org.kolbasa3.xcore.cmds.homes.SetHomeCMD;
import org.kolbasa3.xcore.cmds.push.PushCMD;
import org.kolbasa3.xcore.cmds.push.UnPushCMD;
import org.kolbasa3.xcore.cmds.warps.*;
import org.kolbasa3.xcore.db.*;
import org.kolbasa3.xcore.modules.cases.Case;
import org.kolbasa3.xcore.modules.cases.CaseType;
import org.kolbasa3.xcore.cmds.*;
import org.kolbasa3.xcore.modules.duels.DuelCMD;
import org.kolbasa3.xcore.modules.events.EventCMD;
import org.kolbasa3.xcore.modules.events.EventType;
import org.kolbasa3.xcore.modules.events.Events;
import org.kolbasa3.xcore.listener.*;
import org.kolbasa3.xcore.modules.kits.KitCMD;
import org.kolbasa3.xcore.modules.kits.KitsCMD;
import org.kolbasa3.xcore.modules.quests.QuestCMD;
import org.kolbasa3.xcore.modules.quests.QuestEvent;
import org.kolbasa3.xcore.papi.CorePAPI;
import org.kolbasa3.xcore.modules.tal.TalType;
import org.kolbasa3.xcore.modules.tal.TalUtil;
import org.kolbasa3.xcore.utils.*;
import org.kolbasa3.xcore.utils.custom.Crafts;
import org.kolbasa3.xcore.utils.region.RegionTask;

import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

import static org.kolbasa3.xcore.listener.PhysicsEvent.angryMobs;
import static org.kolbasa3.xcore.utils.PluginUtil.*;
import static org.kolbasa3.xcore.utils.PluginUtil.getRandom;

public final class XCore extends JavaPlugin {

    private static XCore instance;
    Case caseUtil = new Case();
    Events events = new Events();
    EnchUtil enchUtil = new EnchUtil();
    TalUtil talUtil = new TalUtil();

    public static PlayerSQL playerDB;
    public static RegionSQL regionDB;
    public static ClanSQL clanDB;
    public static WarpSQL warpsDB;
    public static HomeSQL homesDB;
    public static PushSQL pushDB;
    public static CdSQL cdDB;
    public static ToggleSQL toggleDB;

    @Override
    public void onEnable() {
        instance = this;
        reg();
        saveDefaultConfig();
        new CaseCMD();
        new CoinCMD();
        new SpitCMD();
        new RegionCMD();
        new ClanCMD();
        new InvestCMD();
        new EventCMD();
        new TalCMD();
        new CoreCMD();
        new CasinoCMD();
        new TagCMD();
        new ModelCMD();
        new BuyerCMD();
        new NpcCMD();
        new CraftsCMD();
        new JoinCMD();
        new SmithCMD();
        new EnchCMD();
        new BcCMD();
        new PushCMD();
        new UnPushCMD();
        new QuestCMD();
        new GpsCMD();
        new PingCMD();
        new SitCMD();
        new FreeCMD();
        new HelpCMD();
        new LinksCMD();
        new JobsCMD();
        new ToggleCMD();
        new ExpCMD();
        new ShopCMD();
        new GmCMD();
        new SpawnCMD();
        new DuelCMD();
        new MenuCMD();
        new DonateCMD();
        new SalaryCMD();

        new AcceptCMD();
        new DenyCMD();
        new TpaCMD();
        new TpCMD();

        new KitCMD();
        new KitsCMD();

        new SetWarpCMD();
        new DelWarpCMD();
        new WarpCMD();
        new WarpsCMD();
        new LikeWarpCMD();
        new DisWarpCMD();

        new SetHomeCMD();
        new DelHomeCMD();
        new HomeCMD();
        new HomesCMD();

        File file = new File(getDataFolder(), "schematics");
        if (!file.exists()) file.mkdir();

        try {
            playerDB = new PlayerSQL(new File(getDataFolder(), "players.db"));
            regionDB = new RegionSQL(new File(getDataFolder(), "regions.db"));
            clanDB = new ClanSQL(new File(getDataFolder(), "clans.db"));
            warpsDB = new WarpSQL(new File(getDataFolder(), "warps.db"));
            homesDB = new HomeSQL(new File(getDataFolder(), "homes.db"));
            pushDB = new PushSQL(new File(getDataFolder(), "push.db"));
            cdDB = new CdSQL(new File(getDataFolder(), "cd.db"));
            toggleDB = new ToggleSQL(new File(getDataFolder(), "toggle.db"));
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось подключиться к SQLite.", e);
        }

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
            new CorePAPI().register();

        regionDB.getRegions(null).forEach(rgName -> {
            String owner = regionDB.getOwner(rgName);
            if (regionDB.getList(owner, rgName, false).contains("radar"))
                new RegionTask().add(owner, rgName, regionDB.getLoc(owner, rgName));
        });

        events.writeLastEvent();
        Bukkit.getScheduler().runTaskTimer(this, task -> {
            if(Bukkit.getOnlinePlayers().isEmpty()) return;
            events.writeLastEvent();
            EventType eventType = EventType.AIRDROP;
            int i = new Random().nextInt(100);

            if (i > 50 && i < 60) eventType = EventType.WATERCHEST;
            else if (i > 60 && i < 85) eventType = EventType.ISLAND;
            else if (i > 85) eventType = EventType.PARKOUR;

            events.spawnDrop(eventType);

            new BossStarter().check(false);
        }, 0, 36000);

        cfg().getStringList("npc").forEach(locStr -> {
            String[] str = locStr.split(",");
            new Npc(str[1], strToLoc(str[0], "\\s", false)).spawn();
        });

        Bukkit.getScheduler().runTaskTimer(this, task ->
                Bukkit.getScheduler().runTaskAsynchronously(XCore.getInstance(), task2 -> {
            Bukkit.getOnlinePlayers().forEach(pl -> {

                AtomicReference<ItemStack> is = new AtomicReference<>(pl.getInventory().getItemInOffHand());
                if (is.get().getType().equals(Material.TOTEM_OF_UNDYING)
                        && is.get().getEnchantments().containsKey(Enchantment.DURABILITY)
                        && is.get().getItemMeta().getLore() != null
                        && !talUtil.getType(Objects.requireNonNull(is.get().getItemMeta().getLore()))
                        .equals(TalType.NULL)
                        && new Random().nextInt(100) > 90) {

                    List<String> lore = is.get().getItemMeta().getLore();
                    if (lore != null && talUtil.getDurability(lore) - 1 > 0) {
                        talUtil.setDurability(is.get(), (talUtil.getDurability(lore) - 1));
                    } else
                        pl.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
                }

                Bukkit.getScheduler().runTask(this, task3 -> {
                    if(events.getLoc() != null && !events.isPastedSchem()
                            && events.getLoc().getWorld().getName().equals(pl.getWorld().getName())
                            && events.getLoc().distance(pl.getLocation()) <= 10) {
                        events.pasteSchem();
                    }

                    if (pl.getWorld().getName().equals("air") && cfg().contains("rtp")
                            && pl.getLocation().distance(strToLoc(Objects.requireNonNull(cfg().getString("rtp")), " ", true)) < 5)
                        pl.performCommand("rtp");

                    if(pl.getWorld().getName().equals("world_nether")
                    && new Random().nextInt(100) > 90) {
                        new BossStarter().spawnNether(pl.getName(), getNearbyLoc(pl.getLocation()));
                    }

                    if(pl.getWorld().getName().equals("world_the_end")
                            && new Random().nextInt(100) > 97) {
                        new BossStarter().spawnEnd(pl.getName(), getNearbyLoc(pl.getLocation()));
                    }

                    if (Bukkit.getTPS()[0] > 16) {
                        angryMobs.forEach(mob -> mob.getLocation().getNearbyPlayers(20)
                                .forEach(nearby -> {
                                    if (mob instanceof Fox fox) fox.setTarget(nearby);
                                    else if (mob instanceof Wolf wolf) wolf.setTarget(nearby);

                                    Location mobLoc = mob.getLocation();
                                    Location playerLoc = nearby.getLocation();

                                    double dx = playerLoc.getX() - mobLoc.getX();
                                    double dy = playerLoc.getY() - mobLoc.getY();
                                    double dz = playerLoc.getZ() - mobLoc.getZ();

                                    double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

                                    dx /= distance;
                                    dy /= distance;
                                    dz /= distance;

                                    mob.setVelocity(new Vector(dx, dy+0.2, dz));

                                    if(mob.getLocation().distance(nearby.getLocation()) <= 1) nearby.damage(0.5, mob);
                                }));
                    }
                    
                    if (Bukkit.getTPS()[0] > 17) {
                        dirt.forEach(l -> {
                            if (l.distance(pl.getLocation()) < 5
                                    && !dirtPlaced.contains(l)) {
                                dirtPlaced.add(l);
                                l.getBlock().setType(Material.AIR);
                                ArmorStand a = (ArmorStand) l.getWorld().spawnEntity(l.getBlock().getLocation().clone().add(0.5, -1.48, 0.5), EntityType.ARMOR_STAND);
                                a.setSilent(true);
                                a.setGravity(false);
                                a.setVisible(false);
                                a.setInvulnerable(true);
                                FallingBlock fb = l.getWorld().spawnFallingBlock(l.getBlock().getLocation().clone().add(0.5, 0, 0.5), Material.COARSE_DIRT.createBlockData());
                                fb.setSilent(true);
                                fb.setInvulnerable(true);
                                fb.setGravity(false);
                                fb.setDropItem(false);
                                a.addPassenger(fb);

                                Bukkit.getScheduler().runTaskLater(this, task4 -> {
                                    if (l.getBlock().getType().equals(Material.DIRT)
                                            || l.getBlock().getType().equals(Material.COARSE_DIRT)
                                            || l.getBlock().getType().equals(Material.AIR)) {
                                        Material m = Material.COARSE_DIRT;
                                        if (!dirt.contains(l)) m = Material.DIRT;
                                        l.getBlock().setType(m);
                                    }
                                    fb.remove();
                                    a.remove();
                                    dirtPlaced.remove(l);
                                }, 40);
                            }
                        });
                    }

                    if (Bukkit.getTPS()[0] > 19) {
                        World w = pl.getWorld();
                        Random random = new Random();
                        if (w.getName().equals("world") && w.hasStorm()
                                && w.isThundering()
                                && w.getHighestBlockYAt(pl.getLocation()) <= pl.getLocation().getBlockY()
                                && random.nextInt(100) > 96) {
                            if (random.nextBoolean()) w.strikeLightning(pl.getLocation());
                            else {
                                Location l = pl.getLocation().clone().add(getRandom(10, 30)
                                        , 0, getRandom(10, 30));
                                l.setY(w.getHighestBlockYAt(l));
                                w.strikeLightning(l);
                            }
                        }
                    }

                    is.set(pl.getInventory().getBoots());
                    if (is.get() != null && is.get().getType().toString().contains("BOOTS")
                            && is.get().hasItemMeta()
                            && is.get().getItemMeta().hasLore()
                            && enchUtil.list(is.get()).contains("лаваход")) {
                        Location loc = pl.getLocation().clone().add(0, -1, 0);

                        if (loc.getBlock().getType().equals(Material.LAVA)) {
                            for (int x = loc.getBlockX() - 1; x < loc.getBlockX() + 1; x++) {
                                for (int z = loc.getBlockZ() - 1; z < loc.getBlockZ() + 1; z++) {
                                    Location pos = new Location(loc.getWorld(), x, loc.getY(), z);

                                    if (pos.getBlock().getType().equals(Material.LAVA)) {
                                        pos.getBlock().setType(Material.OBSIDIAN);

                                        Bukkit.getScheduler().runTaskLater(XCore.getInstance(), task5 -> {
                                            if (pos.getBlock().getType().equals(Material.OBSIDIAN))
                                                pos.getBlock().setType(Material.LAVA);
                                        }, 40);
                                    }
                                }
                            }
                        }
                    }
                });
            });

            if (new Random().nextBoolean() && Bukkit.getTPS()[0] > 18) {
                Arrays.stream(CaseType.values())
                        .filter(caseType -> caseUtil.getOpening(caseType) != null)
                        .forEach(caseType -> {
                            for (CaseType type : CaseType.values()) {
                                if (!cfg().contains("case." + type.toString().toLowerCase())) continue;
                                Location caseLoc = strToLoc(Objects.requireNonNull(cfg().getString("case." + type.toString().toLowerCase()))
                                        , "\\s", true)
                                        .toBlockLocation().toCenterLocation();
                                caseLoc.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME,
                                        caseLoc.clone().add(((double) getRandom(-7, 15) / 10), ((double) getRandom(-7, 15) / 10), ((double) getRandom(-7, 15) / 10))
                                        , 0);
                                break;
                            }
                        });
            }
        }), 0, 20);

        new Crafts();
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        Npc.npcList.forEach(Entity::remove);
        events.delEvent();
        try {
            playerDB.close();
            regionDB.close();
            clanDB.close();
            warpsDB.close();
            homesDB.close();
            pushDB.close();
            cdDB.close();
            toggleDB.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        models.forEach((player, key) -> models.get(player).remove());

        Arrays.stream(CaseType.values()).toList().forEach(caseType -> {
            if (!cfg().contains("case." + caseType.toString().toLowerCase())) return;
            Location caseLoc = strToLoc(Objects.requireNonNull(cfg().getString("case." + caseType.toString().toLowerCase()))
                    , "\\s", false).toCenterLocation();
            caseUtil.spawnHolo(caseType, caseLoc, null);

            ArmorStand armorStand = caseUtil.getChest(caseLoc);
            if (armorStand == null) caseUtil.spawnChest(caseLoc);
            else armorStand.setHeadPose(new EulerAngle(0, 0, 0));

            caseUtil.delSword(caseType);
            caseUtil.delIcon(caseType);
        });

        dirt.forEach(l -> {
            l.getWorld().getNearbyEntitiesByType(ArmorStand.class, l, 2).forEach(a -> {
                if(!a.hasGravity() && !a.isVisible()) a.remove();
            });
            if(l.getBlock().getType().equals(Material.COARSE_DIRT)) l.getBlock().setType(Material.DIRT);
        });

        new CorePAPI().unregister();
    }

    private void reg() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new BlockEvent(), this);
        pm.registerEvents(new DamageEvent(), this);
        pm.registerEvents(new InteractEvent(), this);
        pm.registerEvents(new CommandEvent(), this);
        pm.registerEvents(new PistonEvent(), this);
        pm.registerEvents(new InvEvent(), this);
        pm.registerEvents(new AnvilEvent(), this);
        pm.registerEvents(new HandEvent(), this);
        pm.registerEvents(new ExplodeEvent(), this);
        pm.registerEvents(new ChatEvent(), this);
        pm.registerEvents(new JoinEvent(), this);
        pm.registerEvents(new QuestEvent(), this);
        pm.registerEvents(new PhysicsEvent(), this);
        pm.registerEvents(new BossListener(), this);
    }

    public static XCore getInstance() {
        return instance;
    }

    private Location getNearbyLoc(Location center) {
        int x = center.getBlockX(), z = center.getBlockZ();
        Random random = new Random();

        if(random.nextBoolean()) x -= getRandom(4, 8);
        else x += getRandom(4, 8);

        if(random.nextBoolean()) z -= getRandom(4, 8);
        else z += getRandom(4, 8);

        return new Location(center.getWorld(), x, center.getWorld().getHighestBlockYAt(x, z), z);
    }
}
