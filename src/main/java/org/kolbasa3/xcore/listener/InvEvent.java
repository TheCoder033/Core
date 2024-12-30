package org.kolbasa3.xcore.listener;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.kolbasa3.xcore.XCore;
import org.kolbasa3.xcore.enums.SoundType;
import org.kolbasa3.xcore.enums.WarpsType;
import org.kolbasa3.xcore.gui.*;
import org.kolbasa3.xcore.gui.craft.CraftGUI;
import org.kolbasa3.xcore.gui.craft.ItemCraftGUI;
import org.kolbasa3.xcore.gui.custom.ModelGUI;
import org.kolbasa3.xcore.gui.ench.ArmorEnchGUI;
import org.kolbasa3.xcore.gui.ench.AxeEnchGUI;
import org.kolbasa3.xcore.gui.ench.EnchGUI;
import org.kolbasa3.xcore.gui.ench.SwordEnchGUI;
import org.kolbasa3.xcore.gui.region.RegionFlagsGUI;
import org.kolbasa3.xcore.gui.region.RegionUpgradesGUI;
import org.kolbasa3.xcore.gui.clans.ClanPermsGUI;
import org.kolbasa3.xcore.gui.clans.ClanPlayersGUI;
import org.kolbasa3.xcore.gui.clans.ClanUpgradesGUI;
import org.kolbasa3.xcore.modules.custom.Models;
import org.kolbasa3.xcore.modules.kits.KitListGUI;
import org.kolbasa3.xcore.modules.kits.KitsGUI;
import org.kolbasa3.xcore.modules.smith.SmithCraft;
import org.kolbasa3.xcore.modules.tal.TalGUI;
import org.kolbasa3.xcore.utils.*;
import org.kolbasa3.xcore.enums.Items;
import org.kolbasa3.xcore.utils.region.RegionTask;
import org.kolbasa3.xcore.utils.region.RegionUpgrade;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.kolbasa3.xcore.XCore.*;
import static org.kolbasa3.xcore.modules.duels.Duel.duelCall;
import static org.kolbasa3.xcore.modules.duels.Duel.selectingDuelKit;
import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class InvEvent implements Listener {

    RegionUpgrade regionUpgrade = new RegionUpgrade();
    ClanUpgrade clanUpgrade = new ClanUpgrade();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onOpen(InventoryOpenEvent e) {
        if (!(e.getPlayer() instanceof Player p)) return;

        if(p.getWorld().getName().equals("air") && e.getInventory().getType().equals(InventoryType.MERCHANT)) {
            e.setCancelled(true);
            p.performCommand("smith");
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        String invName = e.getView().getTitle();

        if(invName.equals("§0Казино » Выберите слот") && casino.containsKey(p.getName())) {
            casino.remove(p.getName());
            p.sendMessage(orange + "Игра завершена, поскольку вы закрыли меню.");
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player p)) return;
        String invName = e.getView().getTitle();
        ItemStack is = e.getCurrentItem();
        if (is == null && !invName.equals("             §0⁘ Скупщик ⁘")) return;
        int slot = e.getSlot();
        Inventory inv = e.getInventory();

        String nm = "";
        if (is != null && is.hasItemMeta()) nm = is.getItemMeta().getDisplayName()
                .replace("§x§F§F§9§D§3§5", "");

        switch (invName) {
            case "            §0⁘ Топ кланы ⁘" -> {
                e.setCancelled(true);
                if (is.getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                    p.closeInventory();
                    return;
                }
                String clan = is.getItemMeta().getDisplayName().split("5")[1];

                Player t = Bukkit.getPlayer(clanDB.getOwner(clan));
                if (t == null) {
                    for (String pl : clanDB.getList(clan, true)) {
                        t = Bukkit.getPlayer(pl);
                        if (t == null) break;
                    }
                }

                if (t != null) {
                    clanInvite.put(p.getName(), t.getName());
                    TextComponent text = new TextComponent();
                    TextComponent acceptButton = new TextComponent(hexTextComponent(lime + "[Принять]"));
                    acceptButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/c accept " + p.getName()));
                    TextComponent denyButton = new TextComponent(hexTextComponent(red + "[Отклонить]"));
                    denyButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/c deny " + p.getName()));

                    text.addExtra("\n");
                    text.addExtra(hexTextComponent("Игрок " + orange + p.getName() + " §fхочет вступить в клан.\n"));
                    text.addExtra(acceptButton);
                    text.addExtra(" §7| ");
                    text.addExtra(denyButton);
                    text.addExtra("\n");
                    p.sendMessage(text);
                }
            }

            case "            §0⁘ Талисманы ⁘" -> {
                e.setCancelled(true);
                if (is.getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                    p.performCommand("donshop");
                    return;
                }

                for (String str : Objects.requireNonNull(is.getItemMeta().getLore())) {
                    if (str.contains("Цена")) {
                        int price = Integer.parseInt(str
                                .replace("§fЦена: §x§F§F§9§D§3§5", "")
                                .replace("К", ""));

                        if (playerDB.getCoin(p.getName()) < price) {
                            p.closeInventory();
                            p.sendMessage("");
                            p.sendMessage(red + "Недостаточно средств. §7(Баланс: " + orange + playerDB.getCoin(p.getName()) + "К§7)");
                            p.sendMessage("Вам нужно " + yellow + price + "К");
                            p.sendMessage("");
                            sound(p, SoundType.WRONG);
                            return;
                        } else {
                            playerDB.setCoin(p.getName(), (playerDB.getCoin(p.getName()) - price));
                            ItemStack item = is.clone();
                            List<String> lore = item.getItemMeta().getLore();
                            if (lore != null) {
                                lore.removeIf(str2 -> str2.contains("Цена: ") || str2.contains("Баланс: ")
                                        || str2.isEmpty() || str2.contains("Нажмите для покупки"));
                                lore.add("");
                                lore.add("§fПрочность: " + azure + "100§7/100");
                            }
                            item.setLore(lore);
                            p.getInventory().addItem(item);
                            new TalGUI(p);
                        }
                        return;
                    }
                }
            }

            case "            §0⁘ Модельки ⁘" -> {
                e.setCancelled(true);
                if (is.getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                    p.closeInventory();
                    return;
                }

                if (slot == 49) {
                    models.get(p.getName()).remove();
                    models.remove(p.getName());
                    new ModelGUI(p);
                    return;
                }

                String formattedNm = nm.replace(" ", "_");
                if (!playerDB.getModels(p.getName()).contains(formattedNm)
                        && (slot < 19 || slot > 21)) {
                    int coins = playerDB.getCoin(p.getName());
                    if (coins >= 50) {
                        playerDB.setCoin(p.getName(), coins - 50);
                        List<String> models = playerDB.getModels(p.getName());
                        models.add(formattedNm);
                        playerDB.setModels(p.getName(), models);
                        new ModelGUI(p);
                        return;
                    }
                } else if (models.containsKey(p.getName())
                        && Objects.equals(models.get(p.getName()).getCustomName(), formattedNm)) {
                    models.get(p.getName()).remove();
                    models.remove(p.getName());
                } else {
                    if (models.containsKey(p.getName())) models.get(p.getName()).remove();
                    new Models().spawn(p, formattedNm, is);
                }

                new ModelGUI(p);
                return;
            }

            case "             §0⁘ Скупщик ⁘" -> {
                if (slot == 49) {
                    e.setCancelled(true);
                    if (e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) return;

                    AtomicInteger price = new AtomicInteger();
                    AtomicInteger i = new AtomicInteger();
                    e.getInventory().forEach(item -> {
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

                            if (itemprice == 0) {
                                addPlayerInv(p, item);
                                return;
                            }
                            price.addAndGet(item.getAmount() * itemprice);
                        }
                    });

                    p.closeInventory();

                    if (price.get() == 0) {
                        p.sendMessage(red + "Возвращаем неподходящие предметы в инвентарь...");
                        return;
                    }

                    playerDB.setMoney(p.getName(), (playerDB.getMoney(p.getName())+price.get()));
                    p.sendMessage("");
                    p.sendMessage("Вещи успешно проданы за " + orange + price.get() + "⛁");
                    p.sendMessage("Баланс §7» " + yellow + playerDB.getMoney(p.getName()) + "⛁");
                    p.sendMessage("");
                    return;
                }
            }

            case "§0Казино » Выберите слот" -> {
                e.setCancelled(true);
                if (is.getType().equals(Material.BLACK_STAINED_GLASS_PANE)) {
                    int price = casino.get(p.getName());

                    String totalPrize;
                    int random = new Random().nextInt(100);
                    if (random > 40 && random <= 95) {
                        totalPrize = yellow + "x2";
                        playerDB.setMoney(p.getName(), (playerDB.getMoney(p.getName())+(price*2)));
                    } else if (random > 95) {
                        totalPrize = lime + "x4";
                        playerDB.setMoney(p.getName(), (playerDB.getMoney(p.getName())+(price*4)));
                    } else {
                        totalPrize = red + "0";
                        playerDB.setMoney(p.getName(), (playerDB.getMoney(p.getName())-price));
                    }

                    casino.remove(p.getName());

                    p.closeInventory();
                    p.sendMessage("");
                    p.sendMessage("Вы выиграли: " + totalPrize);
                    p.sendMessage("Сыграть ещё раз §7» " + orange + "/bet §7(сумма)");
                    p.sendMessage("");
                    sound(p, SoundType.SELECT);
                }
            }

            case "          §0⁘ Новые крафты ⁘" -> {
                e.setCancelled(true);
                if (is.getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                    p.performCommand("menu");
                    return;
                }
                new ItemCraftGUI(p, is);
                return;
            }

            case "§0⁘ Кирка Тора", "§0⁘ Топор Тора", "§0⁘ Меч Тора", "§0⁘ Зачарованное золотое яблоко"
            , "§0⁘ Обсидиановый динамит", "§0⁘ Водяной динамит", "§0⁘ Пузырёк опыта", "§0⁘ Пласт", "§0⁘ Блок региона"
            , "§0⁘ Рубиновый посох (/smith)", "§0⁘ Аметистовый посох (/smith)", "§0⁘ Отталкивающая плита"
                    , "§0⁘ Кофе", "§0⁘ Чай", "§0⁘ Пиво" -> {
                e.setCancelled(true);
                if (is.getType().equals(Material.BLACK_STAINED_GLASS_PANE)) {
                    new CraftGUI(p);
                    return;
                }
                return;
            }

            case "            §0⁘ Новые чары ⁘" -> {
                e.setCancelled(true);
                if (is.getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                    p.performCommand("menu");
                    return;
                }

                if (slot == 20) new AxeEnchGUI(p);
                else if (slot == 22) new SwordEnchGUI(p);
                else if (slot == 24) new ArmorEnchGUI(p);
                return;
            }

            case "§0⁘ Новые чары на инструменты ⁘", "§0⁘ Новые чары на оружие ⁘"
            , "§0⁘ Новые чары на броню ⁘" -> {
                e.setCancelled(true);
                if (is.getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                    new EnchGUI(p);
                    return;
                }
            }

            case "         §0⁘ Варпы сервера ⁘", "            §0⁘ Мои варпы ⁘", "         §0⁘ Варпы игроков ⁘" -> {
                e.setCancelled(true);
                if (is.getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                    p.performCommand("menu");
                    return;
                }

                nm = nm.replace("§x§F§F§9§D§3§5", "")
                        .replace("§x§3§5§C§B§F§F", "")
                        .replace("§l", "");

                if (invName.equals("         §0⁘ Варпы сервера ⁘")) {
                    switch (is.getType()) {
                        case DIAMOND_SWORD -> {
                            p.closeInventory();
                            p.performCommand("warp pvp");
                            return;
                        }
                        case ENDER_CHEST -> {
                            p.closeInventory();
                            p.performCommand("warp case");
                            return;
                        }
                    }
                }

                switch (nm) {
                    case "Варпы сервера" -> {
                        new WarpsGUI(p, WarpsType.SERVER);
                        return;
                    }
                    case "Мои варпы" -> {
                        new WarpsGUI(p, WarpsType.PLAYER);
                        return;
                    }
                    case "Варпы игроков" -> {
                        new WarpsGUI(p, WarpsType.OTHER);
                        return;
                    }
                }

                if (!invName.equals("            §0⁘ Мои варпы ⁘") || e.isLeftClick()) {
                    p.closeInventory();
                    p.performCommand("warp " + nm);
                    return;

                } else if (e.isRightClick()) {
                    Material mat = p.getInventory().getItemInMainHand().getType();
                    if (mat.equals(Material.AIR)) {
                        p.closeInventory();
                        p.sendMessage(red + "Держите предмет в руке.");
                        return;
                    }

                    warpsDB.updateIcon(nm, mat);
                    new WarpsGUI(p, WarpsType.PLAYER);
                    return;
                }
            }

            case "            §0⁘ Точки дома ⁘" -> {
                e.setCancelled(true);
                if (is.getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                    p.closeInventory();
                    return;
                }

                nm = nm.replace("§x§3§5§C§B§F§F", "")
                        .replace("§l", "");

                if (e.isLeftClick()) {
                    p.closeInventory();
                    p.performCommand("home " + nm);

                } else if (e.isRightClick()) {
                    Material mat = p.getInventory().getItemInMainHand().getType();
                    if (mat.equals(Material.AIR)) {
                        p.closeInventory();
                        p.sendMessage(red + "Держите предмет в руке.");
                        return;
                    }

                    homesDB.updateIcon(p.getName(), nm, mat);
                    new HomesGUI(p);
                    return;
                }
                return;
            }

            case "              §0⁘ Кузнец ⁘" -> {
                if (is.getType().equals(Material.BLACK_STAINED_GLASS_PANE)) {
                    e.setCancelled(true);
                    p.closeInventory();
                    return;
                }

                if (is.getType().equals(Material.RED_STAINED_GLASS_PANE)) e.setCancelled(true);
                else if (slot == 15) {
                    e.setCancelled(true);
                    p.closeInventory();
                    p.getInventory().addItem(is);
                }

                ItemStack item = new SmithCraft(e.getInventory()).get();
                if (item != null) e.getInventory().setItem(15, item);
            }

            case "          §0⁘ Донат-наборы ⁘" -> {
                e.setCancelled(true);
                if (is.getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                    p.closeInventory();
                    return;
                }

                String name = "Start";
                if (slot == 12) name = "LITE";
                else if (slot == 22) name = "PREM";
                else if (slot == 30) name = "GOLD";
                else if (slot == 14) name = "ADMIN";
                else if (slot == 24) name = "BOSS";
                else if (slot == 32) name = "ULTRA";
                new KitsGUI(p, name);
                return;
            }

            case "       §0⁘ Помощь по серверу ⁘" -> {
                e.setCancelled(true);
                if (is.getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                    p.closeInventory();
                    return;
                }

                if(slot == 20) new CraftGUI(p);
            }

            case "           §0⁘ Работы ⁘" -> {
                e.setCancelled(true);
                if (is.getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                    p.closeInventory();
                    return;
                }

                if(slot == 12) {
                    p.closeInventory();
                    p.performCommand("warp wood");
                    return;
                } else if(slot == 14) {
                    p.closeInventory();
                    p.performCommand("warp fish");
                    return;
                }
            }

            case "                §0⁘ Опыт ⁘" -> {
                e.setCancelled(true);
                if (is.getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                    p.closeInventory();
                    return;
                }

                if(slot == 11 || slot == 13 || slot == 15) {
                    p.closeInventory();
                    int needExp = 15;

                    if (slot == 13) needExp = 20;
                    else if(slot == 15) needExp = 30;

                    if(p.getLevel() < needExp) {
                        p.sendMessage(red+"Недостаточно опыта. §7(Вам нужен: "+orange+needExp+" §7уровень)");
                        return;
                    }

                    p.setLevel(p.getLevel() - needExp);
                    p.getInventory().addItem(Items.EXP.get(needExp+""));
                }
            }

            case "              §0⁘ Работы ⁘" -> {
                e.setCancelled(true);
                if (is.getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                    p.closeInventory();
                    return;
                }

                if(slot == 12) {
                    p.closeInventory();
                    p.performCommand("warp wood");
                } else if(slot == 14) {
                    p.closeInventory();
                    p.performCommand("warp fish");
                }
            }

            case "              §0⁘ Магазин ⁘" -> {
                e.setCancelled(true);
                if (is.getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                    p.closeInventory();
                    return;
                }

                int price = 0;

                if(slot == 11) price = 7000;
                else if(slot == 13) price = 200;
                else if(slot == 15) price = 1000;

                if(price == 0) return;

                if (playerDB.getMoney(p.getName()) < price) {
                    p.sendMessage("");
                    p.sendMessage(red + "Недостаточно средств. §7(Баланс: " + yellow + playerDB.getMoney(p.getName()) + "⛁§7)");
                    p.sendMessage("Вам нужно " + yellow + price + "⛁");
                    p.sendMessage("");
                    sound(p, SoundType.WRONG);
                    return;
                }
                playerDB.setMoney(p.getName(), (playerDB.getMoney(p.getName())-price));
                addPlayerInv(p, new ItemStack(is.getType()));
                p.sendMessage(lime+"Успешная покупка. §7("+red+"-"+price+"⛁§7)");
                return;
            }

            case "               §0⁘ Меню ⁘" -> {
                e.setCancelled(true);
                if (is.getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                    p.closeInventory();
                    return;
                }

                if(slot == 13) {
                    new WarpsGUI(p, WarpsType.SERVER);
                } else if(slot == 21) {
                    new CraftGUI(p);
                } else if(slot == 23) {
                    new EnchGUI(p);
                } else if(slot == 31) {
                    new DonateGUI(p);
                }
            }

            case "           §0⁘ Привилегии ⁘" -> {
                e.setCancelled(true);
                if (is.getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                    new MenuGUI(p);
                    return;
                }

                p.closeInventory();
                p.sendMessage("Покупка привилегий §7» "+orange+"skyworld.net");
            }

            case "        §0⁘ Выберите набор ⁘" -> {
                e.setCancelled(true);
                p.closeInventory();

                String name = "start";
                if (slot == 12) name = "lite";
                else if (slot == 22) name = "prem";
                else if (slot == 30) name = "gold";
                else if (slot == 14) name = "admin";
                else if (slot == 24) name = "boss";
                else if (slot == 32) name = "ultra";

                HashMap<String, String> teams = new HashMap<>();
                teams.put(p.getName(), selectingDuelKit.get(p.getName()));

                selectingDuelKit.remove(p.getName());
                duelCall.put(teams, name);

                Bukkit.getScheduler().runTaskLater(XCore.getInstance(), () ->
                        duelCall.remove(teams), 12000);

                p.sendMessage("");
                p.sendMessage("Набор "+orange+"§l"+name.toUpperCase()+" §fуспешно выбран.");
                p.sendMessage("Запрос на дуэль игроку "+orange+teams.get(p.getName())+" §fотправлен.");

                Player target = Bukkit.getPlayer(teams.get(p.getName()));
                if(target != null) {
                    target.sendMessage("");
                    target.sendMessage("Игрок "+orange+"§l"+p.getName()+" §fпригласил вас на дуэль.");
                    target.sendMessage("Набор §7» "+orange+name.toUpperCase());
                    target.sendMessage("Принять запрос §7» "+azure+"§l/duel "+p.getName());
                }
            }
        }

        if (invName.contains("§0⁘ Улучшения региона ")) {
            e.setCancelled(true);
            if (is.getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                p.closeInventory();
                return;
            }
            String rgName = invName.replace("§0⁘ Улучшения региона ", "");
            String owner = regionDB.getOwner(rgName);
            if (slot == 11) {
                //size
                int lvl = regionUpgrade.getLvl(owner, rgName, "size");
                if (lvl < 16) {
                    int price = regionUpgrade.getPrice("size", lvl + 1);
                    if (playerDB.getMoney(p.getName()) < price) {
                        p.sendMessage("");
                        p.sendMessage(red + "Недостаточно средств. §7(Баланс: " + yellow + playerDB.getMoney(p.getName()) + "⛁§7)");
                        p.sendMessage("Вам нужно " + yellow + price + "⛁");
                        p.sendMessage("");
                        sound(p, SoundType.WRONG);
                        return;
                    }
                    playerDB.setMoney(p.getName(), (playerDB.getMoney(p.getName())-price));
                    regionUpgrade.upgrade(owner, rgName, "size", lvl + 2, 2);
                    new RegionUpgradesGUI(p, rgName);
                }

            } else if (slot == 13) {
                //players
                int lvl = regionUpgrade.getLvl(p.getName(), rgName, "players");
                if (lvl < 14) {
                    int price = regionUpgrade.getPrice("players", lvl + 1);
                    if (playerDB.getMoney(p.getName()) < price) {
                        p.sendMessage("");
                        p.sendMessage(red + "Недостаточно средств. §7(Баланс: " + yellow + playerDB.getMoney(p.getName()) + "⛁§7)");
                        p.sendMessage("Вам нужно " + yellow + price + "⛁");
                        p.sendMessage("");
                        sound(p, SoundType.WRONG);
                        return;
                    }
                    playerDB.setMoney(p.getName(), (playerDB.getMoney(p.getName())-price));
                    regionUpgrade.upgrade(p.getName(), rgName, "players", lvl + 1, 1);
                    new RegionUpgradesGUI(p, rgName);
                }

            } else if (slot == 15) {
                //radar
                if (!regionDB.getList(p.getName(), rgName, false).contains("radar")) {
                    int price = regionUpgrade.getPrice("radar", 0);
                    if (playerDB.getMoney(p.getName()) < price) {
                        p.sendMessage("");
                        p.sendMessage(red + "Недостаточно средств. §7(Баланс: " + yellow + playerDB.getMoney(p.getName()) + "⛁§7)");
                        p.sendMessage("Вам нужно " + yellow + price + "⛁");
                        p.sendMessage("");
                        sound(p, SoundType.WRONG);
                        return;
                    }
                    playerDB.setMoney(p.getName(), (playerDB.getMoney(p.getName())-price));
                    regionUpgrade.upgrade(p.getName(), rgName, "radar", 0, 0);
                    new RegionTask().add(p.getName(), rgName, regionDB.getLoc(p.getName(), rgName));
                    new RegionUpgradesGUI(p, rgName);
                }
            }

        } else if (invName.contains("§0⁘ Флаги региона ")) {
            e.setCancelled(true);
            if (is.getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                p.closeInventory();
                return;
            }
            String rgName = invName.replace("§0⁘ Флаги региона ", "");
            List<String> upgrades = regionDB.getList(p.getName(), rgName, false);
            if (slot == 11) {
                //pvp
                if (!upgrades.contains("pvp")) {
                    int price = regionUpgrade.getPrice("pvp", 0);
                    if (playerDB.getMoney(p.getName()) < price) {
                        p.sendMessage("");
                        p.sendMessage(red + "Недостаточно средств. §7(Баланс: " + yellow + playerDB.getMoney(p.getName()) + "⛁§7)");
                        p.sendMessage("Вам нужно " + yellow + price + "⛁");
                        p.sendMessage("");
                        sound(p, SoundType.WRONG);
                        return;
                    }
                    playerDB.setMoney(p.getName(), (playerDB.getMoney(p.getName())-price));
                    regionUpgrade.upgrade(p.getName(), rgName, "pvp", 0, 0);
                } else {
                    String str = "pvpOff";
                    if (upgrades.contains(str)) upgrades.remove(str);
                    else upgrades.add(str);
                    regionDB.updateList(p.getName(), rgName, false, upgrades);
                }
                new RegionFlagsGUI(p, rgName);

            } else if (slot == 13) {
                //build
                if (!upgrades.contains("build")) {
                    int price = regionUpgrade.getPrice("build", 0);
                    if (playerDB.getMoney(p.getName()) < price) {
                        p.sendMessage("");
                        p.sendMessage(red + "Недостаточно средств. §7(Баланс: " + yellow + playerDB.getMoney(p.getName()) + "⛁§7)");
                        p.sendMessage("Вам нужно " + yellow + price + "⛁");
                        p.sendMessage("");
                        sound(p, SoundType.WRONG);
                        return;
                    }
                    playerDB.setMoney(p.getName(), (playerDB.getMoney(p.getName())-price));
                    regionUpgrade.upgrade(p.getName(), rgName, "build", 0, 0);
                } else {
                    String str = "buildOff";
                    if (upgrades.contains(str)) upgrades.remove(str);
                    else upgrades.add(str);
                    regionDB.updateList(p.getName(), rgName, false, upgrades);
                }
                new RegionFlagsGUI(p, rgName);

            } else if (slot == 15) {
                //destroy
                if (!upgrades.contains("destroy")) {
                    int price = regionUpgrade.getPrice("destroy", 0);
                    if (playerDB.getMoney(p.getName()) < price) {
                        p.sendMessage("");
                        p.sendMessage(red + "Недостаточно средств. §7(Баланс: " + yellow + playerDB.getMoney(p.getName()) + "⛁§7)");
                        p.sendMessage("Вам нужно " + yellow + price + "⛁");
                        p.sendMessage("");
                        sound(p, SoundType.WRONG);
                        return;
                    }
                    playerDB.setMoney(p.getName(), (playerDB.getMoney(p.getName())-price));
                    regionUpgrade.upgrade(p.getName(), rgName, "destroy", 0, 0);
                } else {
                    String str = "destroyOff";
                    if (upgrades.contains(str)) upgrades.remove(str);
                    else upgrades.add(str);
                    regionDB.updateList(p.getName(), rgName, false, upgrades);
                }
                new RegionFlagsGUI(p, rgName);
            }


        } else if (invName.contains("§0⁘ Улучшения клана ")) {
            e.setCancelled(true);
            if (is.getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                p.closeInventory();
                return;
            }
            String clan = clanDB.getClans(p.getName()).get(0);

            if (slot == 12) {
                //players
                int lvl = clanUpgrade.getLvl(clan, "players");
                if (lvl < 14) {
                    int price = clanUpgrade.getPrice("players", lvl + 1);
                    if (playerDB.getMoney(p.getName()) < price) {
                        p.sendMessage("");
                        p.sendMessage(red + "Недостаточно средств. §7(Баланс: " + yellow + playerDB.getMoney(p.getName()) + "⛁§7)");
                        p.sendMessage("Вам нужно " + yellow + price + "⛁");
                        p.sendMessage("");
                        sound(p, SoundType.WRONG);
                        return;
                    }
                    playerDB.setMoney(p.getName(), (playerDB.getMoney(p.getName())-price));
                    clanUpgrade.upgrade(clan, "players", lvl + 1, 1);
                    new ClanUpgradesGUI(p, clan);
                }

            } else if (slot == 14) {
                //balance
                int lvl = clanUpgrade.getLvl(clan, "bal");
                if (lvl < 300000) {
                    int price = clanUpgrade.getPrice("bal", lvl + 15000);
                    if (playerDB.getMoney(p.getName()) < price) {
                        p.sendMessage("");
                        p.sendMessage(red + "Недостаточно средств. §7(Баланс: " + yellow + playerDB.getMoney(p.getName()) + "⛁§7)");
                        p.sendMessage("Вам нужно " + yellow + price + "⛁");
                        p.sendMessage("");
                        sound(p, SoundType.WRONG);
                        return;
                    }
                    playerDB.setMoney(p.getName(), (playerDB.getMoney(p.getName())-price));
                    clanUpgrade.upgrade(clan, "bal", lvl + 15000, 15000);
                    new ClanUpgradesGUI(p, clan);
                }
            }

        } else if (invName.contains("§0⁘ Участники клана ")) {
            e.setCancelled(true);
            if (is.getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                p.closeInventory();
                return;
            }
            String clan = clanDB.getClans(p.getName()).get(0);

            if (is.getType().equals(Material.PLAYER_HEAD) && slot != 10) {
                if (e.isLeftClick() && (clanDB.getOwner(clan).equals(p.getName())
                        || clanDB.getPerms(clan, p.getName()).contains("kick"))) {
                    p.closeInventory();
                    p.performCommand("c kick " + nm);

                } else if (e.isRightClick() && (clanDB.getOwner(clan).equals(p.getName())))
                    new ClanPermsGUI(p, clan, nm);
            }

        } else if (invName.contains("§0⁘ Права участника ")) {
            e.setCancelled(true);
            String clan = clanDB.getClans(p.getName()).get(0);
            if (is.getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                new ClanPlayersGUI(p, clan);
                return;
            }

            String target = invName.replace("§0⁘ Права участника ", "");
            List<String> players = new ArrayList<>(clanDB.getList(clan, true));
            List<String> perms = new ArrayList<>(clanDB.getPerms(clan, target));
            List<String> updatedPlayers = new ArrayList<>();

            if (slot == 11) {
                //kick
                String str = "kick";
                if (perms.contains(str)) perms.remove(str);
                else perms.add(str);

                StringBuilder sb = new StringBuilder();
                perms.forEach(perm -> {
                    if (!sb.isEmpty()) sb.append(",");
                    sb.append(perm);
                });

                players.forEach(pl -> {
                    if (pl.contains(target)) {
                        if (sb.isEmpty()) updatedPlayers.add(target);
                        else updatedPlayers.add(target + ":" + sb);
                    } else updatedPlayers.add(pl);
                });
                clanDB.updateList(clan, true, updatedPlayers);
                new ClanPermsGUI(p, clan, target);

            } else if (slot == 13) {
                //invite
                String str = "invite";
                if (perms.contains(str)) perms.remove(str);
                else perms.add(str);

                StringBuilder sb = new StringBuilder();
                perms.forEach(perm -> {
                    if (!sb.isEmpty()) sb.append(",");
                    sb.append(perm);
                });

                players.forEach(pl -> {
                    if (pl.contains(target)) {
                        if (sb.isEmpty()) updatedPlayers.add(target);
                        else updatedPlayers.add(target + ":" + sb);
                    } else updatedPlayers.add(pl);
                });
                clanDB.updateList(clan, true, updatedPlayers);
                new ClanPermsGUI(p, clan, target);

            } else if (slot == 15) {
                //accept
                String str = "accept";
                if (perms.contains(str)) perms.remove(str);
                else perms.add(str);

                StringBuilder sb = new StringBuilder();
                perms.forEach(perm -> {
                    if (!sb.isEmpty()) sb.append(",");
                    sb.append(perm);
                });

                players.forEach(pl -> {
                    if (pl.contains(target)) {
                        if (sb.isEmpty()) updatedPlayers.add(target);
                        else updatedPlayers.add(target + ":" + sb);
                    } else updatedPlayers.add(pl);
                });
                clanDB.updateList(clan, true, updatedPlayers);
                new ClanPermsGUI(p, clan, target);
            }
        } else if (invName.contains("§0⁘ Набор ") || invName.equals("        §0⁘ Стартовый набор")) {
            e.setCancelled(true);
            if (is.getType().equals(Material.LIME_STAINED_GLASS_PANE)) {
                new KitListGUI(p);
            }
        }
    }
}
