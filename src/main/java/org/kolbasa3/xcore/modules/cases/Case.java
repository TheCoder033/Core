package org.kolbasa3.xcore.modules.cases;

import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import org.kolbasa3.xcore.XCore;
import org.kolbasa3.xcore.utils.ItemUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.kolbasa3.xcore.XCore.playerDB;
import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class Case {

    static HashMap<CaseType, ArmorStand> sword = new HashMap<>();
    static HashMap<CaseType, ArmorStand> icon = new HashMap<>();
    static HashMap<CaseType, String> opening = new HashMap<>();

    private final RandomUtil randomUtil = new RandomUtil();

    public void open(Player player, CaseType caseType, Location loc) {
        opening.put(caseType, player.getName());
        delHolo(caseType);
        AtomicInteger timer = new AtomicInteger();
        ArmorStand chest = getChest(loc);
        AtomicBoolean state = new AtomicBoolean(false);
        AtomicBoolean spawned = new AtomicBoolean(false);
        AtomicBoolean holoPrize = new AtomicBoolean(false);

        Bukkit.getScheduler().runTaskTimer(XCore.getInstance(), task -> {
            if(!sword.containsKey(caseType) && !spawned.get()) {
                ArmorStand armorStand = loc.getWorld().spawn(loc.clone().add(-0.2, 7, -0.5), ArmorStand.class, CreatureSpawnEvent.SpawnReason.COMMAND);
                armorStand.setGravity(false);
                armorStand.setSilent(true);
                armorStand.setInvulnerable(true);
                armorStand.setVisible(false);
                armorStand.setCanMove(false);
                armorStand.setCanTick(false);
                armorStand.setCanPickupItems(false);
                armorStand.setMarker(false);
                if (armorStand.getEquipment() != null)
                    armorStand.getEquipment().setHelmet(new ItemStack(Material.NETHERITE_SWORD));
                armorStand.setHeadPose(new EulerAngle(0, 0, Math.toRadians(135)));
                sword.put(caseType, armorStand);
                return;
            }

            if(timer.get() < 8) {
                timer.getAndIncrement();
                sword.get(caseType).teleport(sword.get(caseType).getLocation().clone().add(0, -0.9, 0));
                sword.get(caseType).getLocation().getWorld().playEffect(sword.get(caseType).getLocation(), Effect.MOBSPAWNER_FLAMES, 10);
                return;
            }

            if(!spawned.get()) {
                if(timer.get() < 20) {
                    timer.getAndIncrement();
                    double z = -20;
                    if (state.get()) z = 20;
                    state.set(!state.get());
                    chest.setHeadPose(new EulerAngle(0, 0, Math.toRadians(z)));

                    chest.getWorld().getNearbyPlayers(chest.getLocation(), 10).forEach(nearby ->
                            nearby.playSound(chest.getLocation(), Sound.BLOCK_CHEST_LOCKED, 0.3F, 0.3F));

                } else {
                    sword.get(caseType).remove();
                    sword.remove(caseType);
                    spawned.set(true);
                    chest.setHeadPose(new EulerAngle(0, 0, 0));

                    chest.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, loc, 0);
                    chest.getWorld().getNearbyPlayers(chest.getLocation(), 10).forEach(nearby ->
                            nearby.playSound(chest.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.3F, 0.3F));

                    chest.remove();
                    FallingBlock fb = loc.getWorld().spawnFallingBlock(loc.clone().add(0, 1, 0), loc.getBlock().getType().createBlockData());
                    fb.setSilent(true);
                    fb.setVelocity(new Vector(0, 0.85, 0));
                    fb.setInvulnerable(true);
                    fb.setDropItem(false);
                    fb.setHurtEntities(false);
                    loc.getBlock().setType(Material.AIR);
                }
            } else if(loc.getBlock().getType().isSolid()) {
                if (getChest(loc) == null) {
                    spawnChest(loc.clone().add(0, -0.4, 0));
                    loc.getWorld().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 20);
                }

                if (timer.get() < 40) {
                    timer.getAndIncrement();
                    if(!holoPrize.get()) {
                        holoPrize.set(true);
                        playerDB.setKey(player.getName(), caseType, (playerDB.getKey(player.getName(), caseType)-1));

                        if(caseType.equals(CaseType.ITEMS)) {
                            ItemStack prize = randomUtil.getItemPrize();
                            sendPrize(player.getName(), caseType, orange+prize.getType()+" x"+prize.getAmount());
                            addPlayerInv(player, prize);
                            delHolo(caseType);
                            double y = -1.2;
                            if(prize.getType().isSolid()) y = -0.7;
                            ArmorStand armorStand = loc.getWorld().spawn(loc.clone().add(0, y, 0), ArmorStand.class, CreatureSpawnEvent.SpawnReason.COMMAND);
                            armorStand.setGravity(false);
                            armorStand.setSilent(true);
                            armorStand.setInvulnerable(true);
                            armorStand.setVisible(false);
                            armorStand.setCanMove(false);
                            armorStand.setCanTick(false);
                            armorStand.setCanPickupItems(false);
                            armorStand.setMarker(false);
                            if (armorStand.getEquipment() != null)
                                armorStand.getEquipment().setHelmet(prize);
                            armorStand.setCustomName(blue+"x"+prize.getAmount());
                            armorStand.setCustomNameVisible(true);
                            icon.put(caseType, armorStand);
                            return;
                        }
                        String prize = randomUtil.getPrize(caseType);
                        int time;
                        if(caseType.equals(CaseType.DONATE)) {
                            time = randomUtil.getTime();
                            prize = prize+" "+time;
                        }
                        spawnHolo(caseType, loc, prize);
                    }
                } else {
                    task.cancel();
                    delIcon(caseType);
                    spawnHolo(caseType, loc, null);
                    opening.remove(caseType);
                }
            }
        }, 0, 2);
    }

    public void set(CaseType caseType, Location loc) {
        Material mat = switch (caseType) {
            case ITEMS -> Material.LIME_STAINED_GLASS;
            case MONEY -> Material.LIGHT_BLUE_STAINED_GLASS;
            default -> Material.ORANGE_STAINED_GLASS;
        };
        loc.getBlock().setType(mat);
        spawnChest(loc.toCenterLocation().clone().add(0, -0.4, 0));
        spawnHolo(caseType, loc, null);
    }

    public void spawnChest(Location loc) {
        ArmorStand armorStand = loc.getWorld().spawn(loc.clone().add(0, -1.25, 0), ArmorStand.class, CreatureSpawnEvent.SpawnReason.COMMAND);
        armorStand.setGravity(false);
        armorStand.setSilent(true);
        armorStand.setInvulnerable(true);
        armorStand.setVisible(false);
        armorStand.setCanMove(false);
        armorStand.setCanTick(false);
        armorStand.setCanPickupItems(false);
        armorStand.setMarker(false);
        armorStand.setCollidable(false);
        if(armorStand.getEquipment() != null) armorStand.getEquipment().setHelmet(new ItemUtil(Material.PLAYER_HEAD, "", null).build("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWM5NmJlNzg4NmViN2RmNzU1MjVhMzYzZTVmNTQ5NjI2YzIxMzg4ZjBmZGE5ODhhNmU4YmY0ODdhNTMifX19"));
    }

    public ArmorStand getChest(Location loc) {
        for (ArmorStand armorStand : loc.getNearbyEntitiesByType(ArmorStand.class, 0.5)) {
            if(!armorStand.isVisible() && !armorStand.hasGravity() && armorStand.getEquipment() != null
            && armorStand.getEquipment().getHelmet() != null) return armorStand;
        }
        return null;
    }

    public String getOpening(CaseType caseType) {
        return opening.getOrDefault(caseType, null);
    }

    public void spawnHolo(CaseType caseType, Location loc, String prize) {
        loc = loc.clone().add(0, 1.1, 0);
        String name = caseType.toString().toLowerCase() + "Case";
        List<String> lines = new ArrayList<>();
        if (prize == null) {
            String type = switch (caseType) {
                case ITEMS -> "Кейс с вещами";
                case MONEY -> "Кейс с деньгами";
                default -> "Донат-кейс";
            };
            lines.add(orange+"§l"+type);
            lines.add("У вас ключей: "+azure+"%XCore_"+caseType.toString().toLowerCase()+"Key%шт.");

        } else {
            String player = opening.get(caseType);
            if(caseType.equals(CaseType.DONATE) && prize.contains(" ")) {
                String[] str = prize.split("\\s");
                String time = str[1];

                String str2 = "add "+str[0];
                if(!Objects.equals(time, "-1")) str2 = "addtemp "+str[0]+" "+time+"month";

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user "+player+" parent "+str2);

                prize = hex(formatDonate(prize.replace(" "+time, "")));
                lines.add(prize);
                String prizeTime = "§7("+time+" мес.)";
                if(time.equals("-1")) prizeTime = "§7(Навсегда)";
                sendPrize(player, caseType, prize+" "+prizeTime);
                lines.add(prizeTime);
            } else if(caseType.equals(CaseType.MONEY)) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give "+player+" "+prize);
                lines.add(yellow+prize+"⛁");
                sendPrize(player, caseType, yellow+prize+"⛁");
            }

        }
        if (DHAPI.getHologram(name) == null) DHAPI.createHologram(name, loc, true, lines);
        else DHAPI.setHologramLines(Objects.requireNonNull(DHAPI.getHologram(name)), lines);
    }

    public void delHolo(CaseType caseType) {
        DHAPI.removeHologram(caseType.toString().toLowerCase()+"Case");
    }

    public void delIcon(CaseType caseType) {
        if(icon.containsKey(caseType)) {
            icon.get(caseType).remove();
            icon.remove(caseType);
        }
    }

    public void delSword(CaseType caseType) {
        if(sword.containsKey(caseType)) {
            sword.get(caseType).remove();
            sword.remove(caseType);
        }
    }

    public void sendPrize(String player, CaseType caseType, String prize) {
        String caseName = switch (caseType) {
            case ITEMS -> "Кейса с вещами";
            case MONEY -> "Кейса с деньгами";
            default -> "Донат-кейса";
        };
        Bukkit.getOnlinePlayers().forEach(pl -> {
            pl.sendMessage("");
            pl.sendMessage("Игрок "+azure+player+" §fвыбил "+prize+" §fиз "+blue+caseName);
            pl.sendMessage("Покупка ключей: "+orange+"/donshop");
            pl.sendMessage("");
        });
    }

    public String formatDonate(String key) {
        return switch (key) {
            case "lite" -> "&#5988FFʟ&#59A2FFɪ&#59BBFFᴛ&#59D5FFᴇ";
            case "prem" -> "&#00AF7Bᴘ&#1ECA85ʀ&#3BE48Fᴇ&#59FF99ᴍ";
            case "gold" -> "&#FFE85Bɢ&#FFD65Cᴏ&#FFC55Cʟ&#FFB35Dᴅ";
            case "admin" -> "&#EE3600ᴀ&#F24B0Cᴅ&#F75F18ᴍ&#FB7423ɪ&#FF882Fɴ";
            case "boss" -> "&#EE6C00ʙ&#F4821Aᴏ&#F99933ꜱ&#FFAF4Dꜱ";
            case "ultra" -> "&#4FFF1Eᴜ&#6DFF2Bʟ&#8BFF39ᴛ&#A8FF46ʀ&#C6FF53ᴀ";
            default -> key;
        };
    }
}
