package org.kolbasa3.xcore.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.kolbasa3.xcore.XCore;
import org.kolbasa3.xcore.enums.Items;
import org.kolbasa3.xcore.utils.ItemUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.kolbasa3.xcore.utils.PluginUtil.orange;
import static org.kolbasa3.xcore.utils.PluginUtil.yellow;

public class BuyerGUI {

    public BuyerGUI(Player p) {
        Inventory inv = Bukkit.createInventory(null, 54, Component.text("             §0⁘ Скупщик ⁘"));

        AtomicReference<List<String>> lore = new AtomicReference<>(new ArrayList<>());

        lore.get().add("");
        lore.get().add("§fВы получите: "+yellow+"0⛁");
        lore.get().add("§7Нажмите чтобы продать.");
        lore.get().add("");
        inv.setItem(49, new ItemUtil(Material.PLAYER_HEAD, orange+"Продать вещи", lore.get()).build("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjE4NTZjN2IzNzhkMzUwMjYyMTQzODQzZDFmOWZiYjIxOTExYTcxOTgzYmE3YjM5YTRkNGJhNWI2NmJlZGM2In19fQ=="));

        p.openInventory(inv);

        Bukkit.getScheduler().runTaskTimer(XCore.getInstance(), task -> {
            if(!p.getOpenInventory().getTitle().equals("             §0⁘ Скупщик ⁘")) {
                task.cancel();
                return;
            }

            AtomicInteger price = new AtomicInteger();
            AtomicInteger i = new AtomicInteger();
            p.getOpenInventory().getTopInventory().forEach(item -> {
                i.getAndIncrement();
                if (item != null && i.get() != 50) {
                    int itemprice = switch (item.getType()) {
                        case WHEAT, CACTUS -> 30;
                        case POTATO -> 20;
                        case BEETROOT -> 50;
                        case APPLE -> 80;
                        case CARROT -> 20;
                        case COD -> 60;
                        case SALMON -> 60;
                        case TROPICAL_FISH -> 130;
                        case MELON_SLICE -> 20;
                        case CHORUS_FRUIT -> 300;
                        case GOLDEN_APPLE -> 2000;
                        case ENCHANTED_GOLDEN_APPLE -> 5000;
                        case HONEY_BOTTLE -> 100;
                        case CAKE -> 1000;

                        case STRING -> 10;
                        case GUNPOWDER -> 70;
                        case FLINT -> 20;
                        case FEATHER -> 15;
                        case BRICK -> 10;
                        case ENDER_PEARL -> 300;
                        case BLAZE_ROD -> 100;
                        case NETHER_WART -> 50;
                        case BONE -> 20;
                        case SUGAR -> 100;

                        case NETHER_STAR -> 1000;
                        case TOTEM_OF_UNDYING -> 10000;
                        case TNT -> 3000;
                        case OBSIDIAN -> 200;
                        case BOOKSHELF -> 500;

                        case COAL -> 100;
                        case CHARCOAL -> 150;
                        case IRON_INGOT -> 300;
                        case GOLD_INGOT -> 500;
                        case DIAMOND -> 1500;
                        case EMERALD -> 3000;
                        case NETHERITE_INGOT -> 300;
                        default -> 0;
                    };

                    if(item.isSimilar(Items.ARTIFACT.get(null))) itemprice = 10;
                    else if(item.isSimilar(Items.COFFEE.get(null))) itemprice = 100;
                    else if(item.isSimilar(Items.TEA.get(null))) itemprice = 50;
                    else if(item.isSimilar(Items.BEER.get(null))) itemprice = 300;

                    price.addAndGet(item.getAmount() * itemprice);
                }
            });

            ItemStack button = inv.getItem(49);
            ItemMeta meta = button.getItemMeta();
            lore.set(meta.getLore());
            lore.get().set(1, "§fВы получите: "+yellow+price.get()+"⛁");
            meta.setLore(lore.get());
            button.setItemMeta(meta);

            // red
            String data = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjE4NTZjN2IzNzhkMzUwMjYyMTQzODQzZDFmOWZiYjIxOTExYTcxOTgzYmE3YjM5YTRkNGJhNWI2NmJlZGM2In19fQ==";

            // lime
            if(price.get() > 0) data = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGI1OTljNjE4ZTkxNGMyNWEzN2Q2OWY1NDFhMjJiZWJiZjc1MTYxNTI2Mzc1NmYyNTYxZmFiNGNmYTM5ZSJ9fX0=";

            p.getOpenInventory().getTopInventory().setItem(49, new ItemUtil(Material.PLAYER_HEAD, orange+"Продать вещи", lore.get()).build(data));
        }, 0, 20);
    }
}
