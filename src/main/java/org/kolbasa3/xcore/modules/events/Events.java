package org.kolbasa3.xcore.modules.events;

import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.kolbasa3.xcore.XCore;
import org.kolbasa3.xcore.utils.EnchUtil;
import org.kolbasa3.xcore.utils.Schematic;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static org.kolbasa3.xcore.utils.PluginUtil.*;
import static org.kolbasa3.xcore.utils.PluginUtil.getRandom;

public class Events {

    Schematic schematic = new Schematic();
    EnchUtil enchUtil = new EnchUtil();

    public static Location EventLoc;
    public static EventType currentEvent = EventType.NULL;
    public static long lastEvent;
    public static int EventTime;
    public static ArmorStand EventStandUp, EventStandDown;
    public static boolean EventOpened;
    public static ArrayList<ItemStack> EventLoot = new ArrayList<>();
    public static boolean pastedSchematic;
    public static ArrayList<Player> eventPlayers = new ArrayList<>();

    public Location getLoc() {
        return EventLoc;
    }

    public EventType getEvent() {
        return currentEvent;
    }

    public int getTime() {
        return EventTime;
    }

    public long getLastEvent() {
        return lastEvent;
    }

    public void loadItems(EventType eventType) {
        switch(eventType) {
            case AIRDROP -> {
                EventLoot.add(new ItemStack(Material.OAK_LOG, getRandom(3, 6)));
                EventLoot.add(new ItemStack(Material.SAND, getRandom(8, 14)));
                EventLoot.add(new ItemStack(Material.COAL, getRandom(8, 16)));
                EventLoot.add(new ItemStack(Material.IRON_INGOT, getRandom(8, 12)));
                EventLoot.add(new ItemStack(Material.GOLD_INGOT, getRandom(6, 9)));
                EventLoot.add(new ItemStack(Material.DIAMOND, getRandom(3, 6)));
                EventLoot.add(new ItemStack(Material.EMERALD, getRandom(2, 4)));
                EventLoot.add(new ItemStack(Material.GOLDEN_APPLE, getRandom(2, 4)));
                EventLoot.add(new ItemStack(Material.ELYTRA));
                EventLoot.add(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, getRandom(1, 2)));
                EventLoot.add(new ItemStack(Material.TOTEM_OF_UNDYING));
            }
            case WATERCHEST -> {
                EventLoot.add(new ItemStack(Material.GLASS, getRandom(8, 14)));
                EventLoot.add(new ItemStack(Material.COAL, getRandom(12, 19)));
                EventLoot.add(new ItemStack(Material.IRON_INGOT, getRandom(10, 14)));
                EventLoot.add(new ItemStack(Material.GOLD_INGOT, getRandom(9, 12)));
                EventLoot.add(new ItemStack(Material.DIAMOND, getRandom(5, 7)));
                EventLoot.add(new ItemStack(Material.EMERALD, getRandom(4, 7)));
                EventLoot.add(new ItemStack(Material.EXPERIENCE_BOTTLE, getRandom(15, 28)));
                EventLoot.add(new ItemStack(Material.GOLDEN_APPLE, getRandom(3, 5)));
                EventLoot.add(new ItemStack(Material.ELYTRA));
                EventLoot.add(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, getRandom(1, 3)));
                EventLoot.add(new ItemStack(Material.TOTEM_OF_UNDYING));
            }
            case ISLAND -> {
                EventLoot.add(new ItemStack(Material.IRON_INGOT, getRandom(15, 17)));
                EventLoot.add(new ItemStack(Material.GOLD_INGOT, getRandom(13, 20)));
                EventLoot.add(new ItemStack(Material.DIAMOND, getRandom(8, 10)));
                EventLoot.add(new ItemStack(Material.EMERALD, getRandom(6, 8)));
                EventLoot.add(new ItemStack(Material.EXPERIENCE_BOTTLE, getRandom(25, 35)));
                EventLoot.add(new ItemStack(Material.ELYTRA));
                EventLoot.add(new ItemStack(Material.GOLDEN_APPLE, getRandom(5, 7)));
                EventLoot.add(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, getRandom(2, 3)));
                EventLoot.add(new ItemStack(Material.TOTEM_OF_UNDYING));
                EventLoot.add(new ItemStack(Material.ELYTRA));

                ItemStack is = new ItemStack(Material.ENCHANTED_BOOK);
                enchUtil.add(is, "плавильня");
                EventLoot.add(is);

                is = new ItemStack(Material.ENCHANTED_BOOK);
                enchUtil.add(is, "магнетизм");
                EventLoot.add(is);

                is = new ItemStack(Material.ENCHANTED_BOOK);
                enchUtil.add(is, "бур");
                EventLoot.add(is);
            }
            case PARKOUR -> {
                EventLoot.add(new ItemStack(Material.GOLD_INGOT, getRandom(16, 25)));
                EventLoot.add(new ItemStack(Material.DIAMOND, getRandom(10, 13)));
                EventLoot.add(new ItemStack(Material.EMERALD, getRandom(8, 10)));
                EventLoot.add(new ItemStack(Material.EXPERIENCE_BOTTLE, getRandom(40, 50)));
                EventLoot.add(new ItemStack(Material.GOLDEN_APPLE, getRandom(6, 8)));
                EventLoot.add(new ItemStack(Material.ELYTRA));
                EventLoot.add(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, getRandom(2, 4)));
                EventLoot.add(new ItemStack(Material.TOTEM_OF_UNDYING));
                EventLoot.add(new ItemStack(Material.ELYTRA));
                EventLoot.add(new ItemStack(Material.TOTEM_OF_UNDYING));

                ItemStack is = new ItemStack(Material.ENCHANTED_BOOK);
                enchUtil.add(is, "плавильня");
                EventLoot.add(is);

                is = new ItemStack(Material.ENCHANTED_BOOK);
                enchUtil.add(is, "магнетизм");
                EventLoot.add(is);

                is = new ItemStack(Material.ENCHANTED_BOOK);
                enchUtil.add(is, "бур");
                EventLoot.add(is);

                is = new ItemStack(Material.ENCHANTED_BOOK);
                enchUtil.add(is, "отравление");
                EventLoot.add(is);

                is = new ItemStack(Material.ENCHANTED_BOOK);
                enchUtil.add(is, "громоотвод");
                EventLoot.add(is);
            }
        }
    }

    public void spawnDrop(EventType eventType) {
        EventLoc = switch (eventType) {
            case NULL -> null;
            case AIRDROP -> getRandLoc(Bukkit.getWorld("world"));
            case WATERCHEST -> getWaterRandLoc(Bukkit.getWorld("world"));
            case ISLAND -> getIslandRandLoc(Bukkit.getWorld("world"));
            case PARKOUR -> strToLoc(Objects.requireNonNull(cfg().getString("chram")), "\\s", false);
        };
        if (EventLoc == null) return;

        EventLoc.getChunk().load();
        currentEvent = eventType;
        timer();

        String str;
        if (eventType.equals(EventType.WATERCHEST))
            str = "появилось " + blue + "Подводное сокровище";
        else if (eventType.equals(EventType.ISLAND))
            str = "появился " + blue + "Небесный сундук";
        else if (eventType.equals(EventType.PARKOUR))
            str = "появился " + blue + "Небесный храм";
        else str = "появился " + blue + "Аирдроп";

        Bukkit.getOnlinePlayers().forEach(pl -> {
            pl.sendMessage("");
            if(!eventType.equals(EventType.PARKOUR)) {
                pl.sendMessage("На координатах "+azure+getLoc().getBlockX()
                        +"x "+getLoc().getBlockZ()+"z §f"+str);
                pl.sendMessage("Навести компас §7» "+orange+"/gps "+getLoc().getBlockX()
                        +" "+getLoc().getBlockZ());
            }
            else {
                pl.sendMessage("В "+blue+"Небесном храме §fобновились сундуки.");
                pl.sendMessage("Телепортация на ивент §7» "+orange+"/evt tp");
            }
            pl.sendMessage("");
        });
    }

    public void pasteSchem() {
        spawnHologram(EventLoc);
        schematic.paste(EventLoc, currentEvent.toString().toLowerCase());
        pastedSchematic = true;
    }

    public boolean isPastedSchem() {
        return pastedSchematic;
    }

    public void spawnHologram(Location EventLoc) {
        ArmorStand a = (ArmorStand) EventLoc.getWorld().spawnEntity(EventLoc.clone().add(0.5, 0.2, 0.5), EntityType.ARMOR_STAND);
        a.setSilent(true);
        a.setGravity(false);
        a.setSmall(true);
        a.setVisible(false);
        a.setInvulnerable(true);
        a.setCanMove(false);
        a.setMarker(false);

        String text = switch (currentEvent) {
            case NULL, PARKOUR -> null;
            case AIRDROP -> "Аирдроп";
            case WATERCHEST -> "Подводное сокровище";
            case ISLAND -> "Небесный сундук";
        };
        a.setCustomName(hex(blue+text));
        a.setCustomNameVisible(true);
        EventStandUp = a;

        a = (ArmorStand) EventLoc.getWorld().spawnEntity(EventLoc.clone().add(0.5, 0, 0.5), EntityType.ARMOR_STAND);
        a.setSilent(true);
        a.setGravity(false);
        a.setSmall(true);
        a.setVisible(false);
        a.setInvulnerable(true);
        a.setCanMove(false);
        a.setMarker(false);

        a.setCustomName(hex("До открытия: &#51CDFF300сек."));
        a.setCustomNameVisible(true);
        EventStandDown = a;
    }

    public ArmorStand getHologram(boolean up) {
        if(up) return EventStandUp;
        else return EventStandDown;
    }

    public void writeLastEvent() {
        lastEvent = System.currentTimeMillis();
    }

    public void timer() {
        // Particles
        Bukkit.getScheduler().runTaskTimer(XCore.getInstance(), task -> {
            if(currentEvent.equals(EventType.NULL)
                    || currentEvent.equals(EventType.PARKOUR)
                    || EventLoc == null) task.cancel();
            createParticle(EventLoc);
        }, 0, 10);

        // Holograms
        Bukkit.getScheduler().runTaskTimer(XCore.getInstance(), task -> {
            if(EventTime < 600) EventTime++;

            if(EventTime >= 600) {
                delEvent();
                if(!currentEvent.equals(EventType.PARKOUR)) schematic.undo(currentEvent.toString().toLowerCase());
                task.cancel();
            }

            if(currentEvent.equals(EventType.PARKOUR)) return;

            if(EventTime < 300) {
                if(getHologram(false) != null) getHologram(false).setCustomName(hex("До открытия: &#51CDFF"+(300-EventTime)+"сек."));

                String text2 = switch (currentEvent) {
                    case WATERCHEST -> "сокровища";
                    case ISLAND -> "небесного сундука";
                    default -> "аирдропа";
                };
                if(EventLoc != null) EventLoc.getWorld().getNearbyPlayers(EventLoc, 30).forEach(nearby -> nearby.sendActionBar(hex("До открытия "+text2+": &#51CDFF"+(300-EventTime)+"сек.")));

            } else if(!EventOpened) {
                for (Entity entity : EventLoc.getNearbyEntities(15, 15, 15)) {
                    Vector velocity = entity.getLocation().toVector().subtract(EventLoc.toVector()).normalize().multiply(2.0);
                    entity.setVelocity(velocity);
                }

                loadItems(currentEvent);
                EventOpened = true;
                if(getHologram(true) != null) {
                    getHologram(true).setCustomName("");
                    getHologram(true).setCustomNameVisible(false);
                }

                String text  = switch (currentEvent) {
                    case WATERCHEST -> "Подводное сокровище";
                    case ISLAND -> "Небесный сундук";
                    default -> "Аирдроп";
                };

                if(getHologram(false) != null) {
                    getHologram(false).setCustomName(hex(blue + text));
                    getHologram(false).setCustomNameVisible(true);
                }
            }

        }, 0, 20);
    }

    public void delEvent() {
        if(!currentEvent.equals(EventType.PARKOUR)) {
            if(getHologram(true) != null) getHologram(true).remove();
            if(getHologram(false) != null) getHologram(false).remove();
            EventTime = 0;
            EventOpened = false;
        } else {
            eventPlayers.forEach(pl -> {
                if(pl != null)pl.performCommand("spawn");
            });
            eventPlayers.clear();
        }
        schematic.undo(currentEvent.toString().toLowerCase());
        currentEvent = EventType.NULL;
        EventLoc = null;
        EventLoot.clear();
    }

    public void createParticle(Location center) {
        if(center == null) return;
        World world = center.getWorld();

        double radius = 1.3;
        double particles = 5;

        for (double i = 0; i < particles; i+=0.25) {
            double angle = 2 * Math.PI * i / particles;
            double x = center.getX() + radius * Math.cos(angle);
            double y = center.getY();
            double z = center.getZ() + radius * Math.sin(angle);

            Location particleLoc = new Location(world, x, y-0.7, z);
            world.spawnParticle(Particle.SOUL_FIRE_FLAME, particleLoc
                    .clone().add(0.5, 1, 0.5), 0);
        }
    }

    private Location getRandLoc(World w) {
        int x = getRandom(800, 1300), z = getRandom(800, 1300);
        Location loc = new Location(w, x, w.getHighestBlockYAt(x, z)+1, z);

        if(!loc.clone().add(0, -1, 0).getBlock().isSolid()) return getRandLoc(w);
        return loc;
    }

    private Location getWaterRandLoc(World w) {
        int x = getRandom(1000, 1500), z = getRandom(1000, 1500);

        for(int i = 20; i < 50; i++) {
            Location loc = new Location(w, x, i+2, z);
            if(loc.getBlock().getType().equals(Material.WATER)
                    && loc.clone().add(0, 4, 0).getBlock().getType().equals(Material.WATER)
                    && loc.clone().add(0, 6, 0).getBlock().getType().equals(Material.WATER)) {
                EventLoc = loc;
                return loc;
            }
        } return getWaterRandLoc(w);
    }

    private Location getIslandRandLoc(World w) {
        int x = getRandom(1300, 1700), z = getRandom(1300, 1700);
        return new Location(w, x, w.getHighestBlockYAt(x, z)+getRandom(30, 50), z);
    }

    public boolean itemsEmpty() {
        return EventLoot.isEmpty();
    }

    public void generateLoot() {
        Chest chest = (Chest) EventLoc.getBlock().getState();
        int amount = 0;
        int max = getRandom(3, 6);
        Random random = new Random();
        for(int i = 0; i < 27; i++) {
            if(amount > max) return;
            if(random.nextBoolean()) {
                amount++;
                chest.getInventory().setItem(i, EventLoot.get(random.nextInt(EventLoot.size())));
            }
        }
        EventLoot.clear();
    }
}
