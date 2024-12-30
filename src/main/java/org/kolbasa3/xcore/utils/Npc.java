package org.kolbasa3.xcore.utils;

import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.ArrayList;

public class Npc {

    public static ArrayList<Entity> npcList = new ArrayList<>();
    private final String type;
    private final Location loc;

    public Npc(String type, Location loc) {
        this.type = type;
        this.loc = loc;
    }
    
    public void spawn() {
        if (type.equalsIgnoreCase("villager")) {
            Villager ent = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER, CreatureSpawnEvent.SpawnReason.COMMAND);
            ent.setCustomNameVisible(false);
            ent.setSilent(true);
            ent.setAI(false);
            ent.setCanPickupItems(false);
            ent.setCollidable(false);
            ent.setGravity(false);
            ent.setInvulnerable(true);
            npcList.add(ent);

        } else if (type.equalsIgnoreCase("smith")) {
            Villager ent = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER, CreatureSpawnEvent.SpawnReason.COMMAND);
            ent.setProfession(Villager.Profession.FLETCHER);
            ent.setCustomNameVisible(false);
            ent.setSilent(true);
            ent.setAI(false);
            ent.setCanPickupItems(false);
            ent.setCollidable(false);
            ent.setGravity(false);
            ent.setInvulnerable(true);
            npcList.add(ent);

        } else if (type.equalsIgnoreCase("blaze")) {
            Blaze ent = (Blaze) loc.getWorld().spawnEntity(loc, EntityType.BLAZE, CreatureSpawnEvent.SpawnReason.COMMAND);
            ent.setCustomNameVisible(false);
            ent.setSilent(true);
            ent.setAI(false);
            ent.setCanPickupItems(false);
            ent.setCollidable(false);
            ent.setGravity(false);
            ent.setInvulnerable(true);
            npcList.add(ent);

        } else if (type.equalsIgnoreCase("slime")) {
            Slime ent = (Slime) loc.getWorld().spawnEntity(loc, EntityType.SLIME, CreatureSpawnEvent.SpawnReason.COMMAND);
            ent.setCustomNameVisible(false);
            ent.setSilent(true);
            ent.setAI(false);
            ent.setCanPickupItems(false);
            ent.setCollidable(false);
            ent.setGravity(false);
            ent.setInvulnerable(true);
            npcList.add(ent);

        } else if (type.equalsIgnoreCase("bear")) {
            PolarBear ent = (PolarBear) loc.getWorld().spawnEntity(loc, EntityType.POLAR_BEAR, CreatureSpawnEvent.SpawnReason.COMMAND);
            ent.setCustomNameVisible(false);
            ent.setSilent(true);
            ent.setAI(false);
            ent.setCanPickupItems(false);
            ent.setCollidable(false);
            ent.setGravity(false);
            ent.setInvulnerable(true);
            npcList.add(ent);

        } else if (type.equalsIgnoreCase("turtle")) {
            Turtle ent = (Turtle) loc.getWorld().spawnEntity(loc, EntityType.TURTLE, CreatureSpawnEvent.SpawnReason.COMMAND);
            ent.setCustomNameVisible(false);
            ent.setSilent(true);
            ent.setAI(false);
            ent.setCanPickupItems(false);
            ent.setCollidable(false);
            ent.setGravity(false);
            ent.setInvulnerable(true);
            npcList.add(ent);

        } else if (type.equalsIgnoreCase("witch")) {
            Witch ent = (Witch) loc.getWorld().spawnEntity(loc, EntityType.WITCH, CreatureSpawnEvent.SpawnReason.COMMAND);
            ent.setCustomNameVisible(false);
            ent.setSilent(true);
            ent.setAI(false);
            ent.setCanPickupItems(false);
            ent.setCollidable(false);
            ent.setGravity(false);
            ent.setInvulnerable(true);
            npcList.add(ent);

        } else if (type.equalsIgnoreCase("fox")) {
            Fox ent = (Fox) loc.getWorld().spawnEntity(loc, EntityType.FOX, CreatureSpawnEvent.SpawnReason.COMMAND);
            ent.setCustomNameVisible(false);
            ent.setSilent(true);
            ent.setAI(false);
            ent.setCanPickupItems(false);
            ent.setCollidable(false);
            ent.setGravity(false);
            ent.setInvulnerable(true);
            npcList.add(ent);

        } else if (type.equalsIgnoreCase("dolphin")) {
            Dolphin ent = (Dolphin) loc.getWorld().spawnEntity(loc, EntityType.DOLPHIN, CreatureSpawnEvent.SpawnReason.COMMAND);
            ent.setCustomNameVisible(false);
            ent.setSilent(true);
            ent.setAI(false);
            ent.setCanPickupItems(false);
            ent.setCollidable(false);
            ent.setGravity(false);
            ent.setInvulnerable(true);
            npcList.add(ent);

        } else if (type.equalsIgnoreCase("vindicator")) {
            Vindicator ent = (Vindicator) loc.getWorld().spawnEntity(loc, EntityType.VINDICATOR, CreatureSpawnEvent.SpawnReason.COMMAND);
            ent.setCustomNameVisible(false);
            ent.setSilent(true);
            ent.setAI(false);
            ent.setCanPickupItems(false);
            ent.setCollidable(false);
            ent.setGravity(false);
            ent.setInvulnerable(true);
            npcList.add(ent);

        } else if (type.equalsIgnoreCase("panda")) {
            Panda ent = (Panda) loc.getWorld().spawnEntity(loc, EntityType.PANDA, CreatureSpawnEvent.SpawnReason.COMMAND);
            ent.setCustomNameVisible(false);
            ent.setSilent(true);
            ent.setAI(false);
            ent.setCanPickupItems(false);
            ent.setCollidable(false);
            ent.setGravity(false);
            ent.setInvulnerable(true);
            npcList.add(ent);
        }
    }
}
