package org.kolbasa3.xcore.listener;

import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.kolbasa3.xcore.modules.tal.TalType;
import org.kolbasa3.xcore.modules.tal.TalUtil;

import java.util.List;
import java.util.Objects;

public class HandEvent implements Listener {

    TalUtil talUtil = new TalUtil();
    private final float default_speed = 0.10000000149011612F;

    @EventHandler
    public void onTal(PlayerSwapHandItemsEvent e) {
        Player p = e.getPlayer();
        ItemStack is = e.getOffHandItem();
        ItemStack is2 = e.getMainHandItem();

        if(is != null && is.getType().equals(Material.TOTEM_OF_UNDYING)
                && is.getItemMeta() != null
                && is.getItemMeta().hasLore()
                && is.getEnchantments().containsKey(Enchantment.DURABILITY)
                && !talUtil.getType(Objects.requireNonNull(is.getItemMeta().getLore())).equals(TalType.NULL)) {
            List<String> lore = is.getItemMeta().getLore();

            if(talUtil.getType(lore).equals(TalType.FEED)) {
                AttributeInstance a = p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
                if(a != null) {
                    a.setBaseValue(default_speed*70/100);
                }

                a = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if(a != null) {
                    a.setBaseValue(18);
                }

            } else if(talUtil.getType(lore).equals(TalType.HEALTH)) {
                AttributeInstance a = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if(a != null) {
                    a.setBaseValue(26);
                    if(p.getHealth() == 20) p.setHealth(26);
                }

                a = p.getAttribute(Attribute.GENERIC_ARMOR);
                if(a != null) {
                    a.setBaseValue(-4);
                }

                a = p.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
                if(a != null) {
                    a.setBaseValue(-2);
                }

            } else if(talUtil.getType(lore).equals(TalType.SPEED)) {
                AttributeInstance a = p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
                if (a != null) {
                    a.setBaseValue(default_speed*110/100);
                }

                a = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if(a != null) {
                    a.setBaseValue(18);
                }

                a = p.getAttribute(Attribute.GENERIC_ARMOR);
                if(a != null) {
                    a.setBaseValue(-4);
                }

            } else if(talUtil.getType(lore).equals(TalType.LOOT)) {
                AttributeInstance a = p.getAttribute(Attribute.GENERIC_LUCK);
                if (a != null) {
                    a.setBaseValue(6);
                }

                a = p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
                if (a != null) {
                    a.setBaseValue(default_speed*80/100);
                }

                a = p.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
                if(a != null) {
                    a.setBaseValue(-2);
                }

            } else if(talUtil.getType(lore).equals(TalType.DURABILITY)) {
                AttributeInstance a = p.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
                if(a != null) {
                    a.setBaseValue(-8);
                }

                a = p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
                if (a != null) {
                    a.setBaseValue(default_speed*80/100);
                }

                a = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if(a != null) {
                    a.setBaseValue(18);
                }

            } else if(talUtil.getType(lore).equals(TalType.DAMAGE)) {
                AttributeInstance a = p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
                if(a != null) {
                    a.setBaseValue((double) (2 * 120) /100);
                }

                a = p.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
                if(a != null) {
                    a.setBaseValue(-4);
                }

                a = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if(a != null) {
                    a.setBaseValue(18);
                }

            } else if(talUtil.getType(lore).equals(TalType.SKY)) {
                AttributeInstance a = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if(a != null) {
                    a.setBaseValue(26);
                    if(p.getHealth() == 20) p.setHealth(26);
                }

                a = p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
                if (a != null) {
                    a.setBaseValue(default_speed*110/100);
                }

                a = p.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
                if(a != null) {
                    a.setBaseValue(5);
                }

                a = p.getAttribute(Attribute.GENERIC_ARMOR);
                if(a != null) {
                    a.setBaseValue(-2);
                }
            }

        } else if(is2 != null && is2.getType().equals(Material.TOTEM_OF_UNDYING)
                && is2.getItemMeta() != null && is2.getItemMeta().hasLore()
                && is2.getEnchantments().containsKey(Enchantment.DURABILITY)
                && !talUtil.getType(Objects.requireNonNull(is2.getItemMeta().getLore())).equals(TalType.NULL)) {
            List<String> lore = is2.getItemMeta().getLore();
            if(talUtil.getType(lore).equals(TalType.FEED)) {
                AttributeInstance a = p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
                if(a != null) {
                    a.setBaseValue(default_speed);
                }

                a = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if(a != null) {
                    a.setBaseValue(20);
                }

            } else if(talUtil.getType(lore).equals(TalType.HEALTH)) {
                AttributeInstance a = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if(a != null) {
                    a.setBaseValue(20);
                }

                a = p.getAttribute(Attribute.GENERIC_ARMOR);
                if(a != null) {
                    a.setBaseValue(0);
                }

                a = p.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
                if(a != null) {
                    a.setBaseValue(0);
                }

            } else if(talUtil.getType(lore).equals(TalType.SPEED)) {
                AttributeInstance a = p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
                if (a != null) {
                    a.setBaseValue(default_speed);
                }

                a = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if(a != null) {
                    a.setBaseValue(20);
                }

                a = p.getAttribute(Attribute.GENERIC_ARMOR);
                if(a != null) {
                    a.setBaseValue(0);
                }

            } else if(talUtil.getType(lore).equals(TalType.LOOT)) {
                AttributeInstance a = p.getAttribute(Attribute.GENERIC_LUCK);
                if (a != null) {
                    a.setBaseValue(0);
                }

                a = p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
                if (a != null) {
                    a.setBaseValue(default_speed);
                }

                a = p.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
                if(a != null) {
                    a.setBaseValue(0);
                }

            } else if(talUtil.getType(lore).equals(TalType.DURABILITY)) {
                AttributeInstance a = p.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
                if(a != null) {
                    a.setBaseValue(0);
                }

                a = p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
                if (a != null) {
                    a.setBaseValue(default_speed);
                }

                a = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if(a != null) {
                    a.setBaseValue(20);
                }

            } else if(talUtil.getType(lore).equals(TalType.DAMAGE)) {
                AttributeInstance a = p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
                if(a != null) {
                    a.setBaseValue(a.getDefaultValue());
                }

                a = p.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
                if(a != null) {
                    a.setBaseValue(0);
                }

                a = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if(a != null) {
                    a.setBaseValue(20);
                }

            } else if(talUtil.getType(lore).equals(TalType.SKY)) {
                AttributeInstance a = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if(a != null) {
                    a.setBaseValue(20);
                }

                a = p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
                if (a != null) {
                    a.setBaseValue(default_speed);
                }

                a = p.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
                if(a != null) {
                    a.setBaseValue(0);
                }

                a = p.getAttribute(Attribute.GENERIC_ARMOR);
                if(a != null) {
                    a.setBaseValue(0);
                }
            }
        }
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent e) {
        if(!(e.getEntity() instanceof Player p)) return;

        if(p.getInventory().getItemInOffHand().equals(talUtil.getTal(TalType.FEED))) {
            if(p.getFoodLevel() > e.getFoodLevel()) {
                if(RandomUtils.nextInt(100) < 80) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if(!(e.getPlayer() instanceof Player p)) return;
        ItemStack is = p.getInventory().getItemInOffHand();

        if(is.getType().equals(Material.TOTEM_OF_UNDYING) && is.getItemMeta() != null
                && is.getItemMeta().hasLore() && is.getEnchantments().containsKey(Enchantment.DURABILITY)
                && !talUtil.getType(Objects.requireNonNull(is.getItemMeta().getLore())).equals(TalType.NULL)) {
            List<String> lore = is.getItemMeta().getLore();
            if(talUtil.getType(lore).equals(TalType.FEED)) {
                AttributeInstance a = p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
                if(a != null) {
                    a.setBaseValue(default_speed*70/100);
                }

                a = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if(a != null) {
                    a.setBaseValue(18);
                }

            } else if(talUtil.getType(lore).equals(TalType.HEALTH)) {
                AttributeInstance a = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if(a != null) {
                    a.setBaseValue(26);
                    if(p.getHealth() == 20) p.setHealth(26);
                }

                a = p.getAttribute(Attribute.GENERIC_ARMOR);
                if(a != null) {
                    a.setBaseValue(-4);
                }

                a = p.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
                if(a != null) {
                    a.setBaseValue(-2);
                }

            } else if(talUtil.getType(lore).equals(TalType.SPEED)) {
                AttributeInstance a = p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
                if (a != null) {
                    a.setBaseValue(default_speed*110/100);
                }

                a = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if(a != null) {
                    a.setBaseValue(18);
                }

                a = p.getAttribute(Attribute.GENERIC_ARMOR);
                if(a != null) {
                    a.setBaseValue(-4);
                }

            } else if(talUtil.getType(lore).equals(TalType.LOOT)) {
                AttributeInstance a = p.getAttribute(Attribute.GENERIC_LUCK);
                if (a != null) {
                    a.setBaseValue(6);
                }

                a = p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
                if (a != null) {
                    a.setBaseValue(default_speed*80/100);
                }

                a = p.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
                if(a != null) {
                    a.setBaseValue(-2);
                }

            } else if(talUtil.getType(lore).equals(TalType.DURABILITY)) {
                AttributeInstance a = p.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
                if(a != null) {
                    a.setBaseValue(-8);
                }

                a = p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
                if (a != null) {
                    a.setBaseValue(default_speed*80/100);
                }

                a = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if(a != null) {
                    a.setBaseValue(18);
                }

            } else if(talUtil.getType(lore).equals(TalType.DAMAGE)) {
                AttributeInstance a = p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
                if(a != null) {
                    a.setBaseValue((double) (2 * 120) /100);
                }

                a = p.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
                if(a != null) {
                    a.setBaseValue(-4);
                }

                a = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if(a != null) {
                    a.setBaseValue(18);
                }

            } else if(talUtil.getType(lore).equals(TalType.SKY)) {
                AttributeInstance a = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if(a != null) {
                    a.setBaseValue(26);
                    if(p.getHealth() == 20) p.setHealth(26);
                }

                a = p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
                if (a != null) {
                    a.setBaseValue(default_speed*110/100);
                }

                a = p.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
                if(a != null) {
                    a.setBaseValue(5);
                }

                a = p.getAttribute(Attribute.GENERIC_ARMOR);
                if(a != null) {
                    a.setBaseValue(-2);
                }

            } else if(talUtil.getType(lore).equals(TalType.NULL)) {
                AttributeInstance a = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if(a != null) {
                    a.setBaseValue(20);
                }

                a = p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
                if (a != null) {
                    a.setBaseValue(default_speed);
                }

                a = p.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
                if(a != null) {
                    a.setBaseValue(0);
                }

                a = p.getAttribute(Attribute.GENERIC_ARMOR);
                if(a != null) {
                    a.setBaseValue(0);
                }

                a = p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
                if(a != null) {
                    a.setBaseValue(a.getDefaultValue());
                }

                a = p.getAttribute(Attribute.GENERIC_LUCK);
                if (a != null) {
                    a.setBaseValue(0);
                }
            }

            if(talUtil.getType(lore).equals(TalType.LOOT)
                    || talUtil.getType(lore).equals(TalType.NULL)) {
                AttributeInstance a = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if(a != null) {
                    a.setBaseValue(20);
                }
            }

            if(talUtil.getType(lore).equals(TalType.HEALTH)
                    || talUtil.getType(lore).equals(TalType.DAMAGE)
                    || talUtil.getType(lore).equals(TalType.NULL)) {
                AttributeInstance a = p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
                if(a != null) {
                    a.setBaseValue(default_speed);
                }
            }

            if(talUtil.getType(lore).equals(TalType.FEED)
                    || talUtil.getType(lore).equals(TalType.LOOT)
                    || talUtil.getType(lore).equals(TalType.DURABILITY)
                    || talUtil.getType(lore).equals(TalType.DAMAGE)
                    || talUtil.getType(lore).equals(TalType.NULL)) {
                AttributeInstance a = p.getAttribute(Attribute.GENERIC_ARMOR);
                if(a != null) {
                    a.setBaseValue(0);
                }
            }

            if(talUtil.getType(lore).equals(TalType.FEED)
                    || talUtil.getType(lore).equals(TalType.SPEED)
                    || talUtil.getType(lore).equals(TalType.NULL)) {
                AttributeInstance a = p.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
                if(a != null) {
                    a.setBaseValue(0);
                }
            }

            if(!talUtil.getType(lore).equals(TalType.DAMAGE)) {
                AttributeInstance a = p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
                if(a != null) {
                    a.setBaseValue(a.getDefaultValue());
                }
            }
        } else {
            AttributeInstance a = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            if(a != null) {
                a.setBaseValue(20);
            }

            a = p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
            if (a != null) {
                a.setBaseValue(default_speed);
            }

            a = p.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS);
            if(a != null) {
                a.setBaseValue(0);
            }

            a = p.getAttribute(Attribute.GENERIC_ARMOR);
            if(a != null) {
                a.setBaseValue(0);
            }

            a = p.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
            if(a != null) {
                a.setBaseValue(a.getDefaultValue());
            }

            a = p.getAttribute(Attribute.GENERIC_LUCK);
            if (a != null) {
                a.setBaseValue(0);
            }
        }
    }
}
