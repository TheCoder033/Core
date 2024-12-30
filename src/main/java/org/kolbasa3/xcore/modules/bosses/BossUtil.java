package org.kolbasa3.xcore.modules.bosses;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;

public class BossUtil {

    private final LivingEntity boss;

    public BossUtil(BossType bossType, Location loc) {
        LivingEntity ent = null;
        double maxHp = 0, maxSpeed = 0;
        switch (bossType) {
            case GUARDIAN -> {
                WitherSkeleton boss = loc.getWorld().spawn(loc, WitherSkeleton.class, CreatureSpawnEvent.SpawnReason.COMMAND);
                maxHp = 300;
                maxSpeed = 0.2;
                ent = boss;
            }
            case HAMSTER -> {
                Hoglin boss = loc.getWorld().spawn(loc, Hoglin.class, CreatureSpawnEvent.SpawnReason.COMMAND);
                maxHp = 100;
                maxSpeed = 1;
                ent = boss;
            }
            case FLAME -> {
                Blaze boss = loc.getWorld().spawn(loc, Blaze.class, CreatureSpawnEvent.SpawnReason.COMMAND);
                maxHp = 500;
                maxSpeed = 0.2;
                ent = boss;
            }
            case SHULKER -> {
                Pillager boss = loc.getWorld().spawn(loc, Pillager.class, CreatureSpawnEvent.SpawnReason.COMMAND);
                if(boss.getEquipment() != null)
                    boss.getEquipment().setHelmet(new ItemStack(Material.SHULKER_BOX));
                maxHp = 800;
                maxSpeed = 0.4;
                ent = boss;
            }
            case SLIME -> {
                Slime boss = loc.getWorld().spawn(loc, Slime.class, CreatureSpawnEvent.SpawnReason.COMMAND);
                maxHp = 1000;
                maxSpeed = 0.7;
                boss.setSize(4);
                ent = boss;
            }

            case NETHER_ZOMBIE -> {
                Zombie boss = loc.getWorld().spawn(loc, Zombie.class, CreatureSpawnEvent.SpawnReason.COMMAND);
                maxHp = 200;
                maxSpeed = 0.2;
                boss.setFireTicks(99999);
                if(boss.getEquipment() != null) boss.getEquipment()
                        .setItemInMainHand(new ItemStack(Material.FLINT_AND_STEEL));
                ent = boss;
            }

            case NETHER_MAGE -> {
                Vindicator boss = loc.getWorld().spawn(loc, Vindicator.class, CreatureSpawnEvent.SpawnReason.COMMAND);
                maxHp = 300;
                maxSpeed = 0.3;
                ent = boss;
            }

            case END_SPIDER -> {
                CaveSpider boss = loc.getWorld().spawn(loc, CaveSpider.class, CreatureSpawnEvent.SpawnReason.COMMAND);
                maxHp = 100;
                maxSpeed = 0.5;
                ent = boss;
            }
        }

        this.boss = ent;

        if(ent != null) {
            AttributeInstance attribute = ent.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            if (attribute != null) attribute.setBaseValue(maxHp);
            ent.setHealth(maxHp);

            attribute = ent.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
            if (attribute != null) attribute.setBaseValue(maxSpeed);

            ent.setCustomName(bossType.getCustomName());
            ent.setCustomNameVisible(true);
        }
    }

    public void remove() {
        boss.remove();
    }

    public LivingEntity get() {
        return boss;
    }
}
