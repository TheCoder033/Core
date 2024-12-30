package org.kolbasa3.xcore.cmds;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.gui.clans.ClanPlayersGUI;
import org.kolbasa3.xcore.gui.clans.ClanTopGUI;
import org.kolbasa3.xcore.gui.clans.ClanUpgradesGUI;
import org.kolbasa3.xcore.utils.ClanUpgrade;
import org.kolbasa3.xcore.enums.SoundType;

import java.util.List;

import static org.kolbasa3.xcore.XCore.*;
import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class ClanCMD extends AbstractCMD {

    public ClanCMD() {
        super("clan");
    }

    ClanUpgrade clanUpgrade = new ClanUpgrade();

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;

        if(args.length == 0
        || (args.length < 2 && (args[0].equalsIgnoreCase("new")
                || args[0].equalsIgnoreCase("accept")
                || args[0].equalsIgnoreCase("deny")
        || args[0].equalsIgnoreCase("invite")
        || args[0].equalsIgnoreCase("kick")
        || args[0].equalsIgnoreCase("pay")
        || args[0].equalsIgnoreCase("take")))
        || (!args[0].equalsIgnoreCase("new") && !args[0].equalsIgnoreCase("del")
        && !args[0].equalsIgnoreCase("invite") && !args[0].equalsIgnoreCase("kick")
        && !args[0].equalsIgnoreCase("upgrade") && !args[0].equalsIgnoreCase("top")
                && !args[0].equalsIgnoreCase("pl") && !args[0].equalsIgnoreCase("icon")
                && !args[0].equalsIgnoreCase("accept") && !args[0].equalsIgnoreCase("deny")
        && !args[0].equalsIgnoreCase("setspawn") && !args[0].equalsIgnoreCase("spawn")
        && !args[0].equalsIgnoreCase("bal") && !args[0].equalsIgnoreCase("pay")
                && !args[0].equalsIgnoreCase("take"))) {
            player.sendMessage("");
            player.sendMessage("Кланы:");
            if(!clanDB.getClans(player.getName()).isEmpty()) {
                player.sendMessage(orange + "/c del §f» Удалить клан.");
                player.sendMessage(orange + "/c setspawn §f» Установить точку спавна клана.");
                player.sendMessage(orange + "/c spawn §f» Телепортироваться на спавн клана.");
                player.sendMessage(orange + "/c bal §f» Баланс клана.");
                player.sendMessage(orange + "/c pay/take §7(сумма) §f» Внести/снять деньги клана.");
                player.sendMessage(orange + "/c invite §7(ник) §f» Пригласить игрока в клан.");
                player.sendMessage(orange + "/c kick §7(ник) §f» Кикнуть игрока из клана.");
                player.sendMessage(orange + "/c upgrade §f» Улучшения клана.");
                player.sendMessage(orange + "/c pl §f» Список участников клана.");
                player.sendMessage(orange + "/c icon §f» Поменять иконку клана. §7(в /c top)");
            } else {
                player.sendMessage(orange + "/c new §7(название) §f» Создать клан. §7("+yellow+"25000⛁§7)");
                player.sendMessage(orange + "/c accept §7(ник) §f» Принять приглашение в клан.");
                player.sendMessage(orange + "/c deny §7(ник) §f» Отклонить приглашение в клан.");
            }
            player.sendMessage(orange + "/c top §f» Список топ кланов.");
            player.sendMessage("");
            sound(player, SoundType.WRONG);
            return;
        }

        if(args[0].equalsIgnoreCase("new")) {
            if (args[1].length() < 4) {
                player.sendMessage(red + "Минимальная длина названия клана §7» " + orange + "4 " + red + "символа.");
                sound(player, SoundType.WRONG);
                return;
            }
            if (args[1].length() > 16) {
                player.sendMessage(red + "Максимальная длина названия клана §7» " + orange + "16 " + red + "символов.");
                sound(player, SoundType.WRONG);
                return;
            }

            if(playerDB.getMoney(player.getName()) < 25000) {
                player.sendMessage("");
                player.sendMessage(red + "Недостаточно средств. §7(Баланс: " + yellow + playerDB.getMoney(player.getName()) + "⛁§7)");
                player.sendMessage("Вам нужно " + yellow + "25000⛁");
                player.sendMessage("");
                sound(player, SoundType.WRONG);
                return;
            }
            playerDB.setMoney(player.getName(), (playerDB.getMoney(player.getName())-25000));

            clanDB.createClan(args[1], player.getName());
            player.sendMessage("");
            player.sendMessage("Клан "+orange+args[1]+" §fуспешно создан.");
            player.sendMessage("Список команд §7» "+azure+"/c");
            player.sendMessage("");
            sound(player, SoundType.SUCCESS);

        } else if(args[0].equalsIgnoreCase("del")) {
            if(clanDB.getClans(player.getName()).isEmpty()) {
                player.sendMessage("");
                player.sendMessage(red+"У вас нет клана.");
                player.sendMessage("Создать клан §7» "+orange+"/c new §7(название)");
                player.sendMessage("");
                sound(player, SoundType.WRONG);
                return;
            }
            String clan = clanDB.getClans(player.getName()).get(0);
            clanDB.delClan(clan);
            player.sendMessage("Клан " +orange+clan+" §fуспешно удалён.");
            sound(player, SoundType.SUCCESS);

        } else if(args[0].equalsIgnoreCase("invite")) {
            if(clanDB.getClans(player.getName()).isEmpty()) {
                player.sendMessage("");
                player.sendMessage(red+"У вас нет клана.");
                player.sendMessage("Создать клан §7» "+orange+"/c new §7(название)");
                player.sendMessage("");
                sound(player, SoundType.WRONG);
                return;
            }
            if(player.getName().equals(args[1])) return;
            String clan = clanDB.getClans(player.getName()).get(0);

            if(!clanDB.getOwner(clan).equals(player.getName())
                    && clanDB.getPerms(clan, player.getName()).contains("invite")) {
                player.sendMessage(red+"Вы не можете приглашать игроков в клан.");
                sound(player, SoundType.WRONG);
                return;
            }

            List<String> list = clanDB.getList(clan, true);

            if(list.size() >= clanUpgrade.getLvl(clan, "players")) {
                player.sendMessage("");
                player.sendMessage(red+"Вы превысили максимальное количество участников.");
                player.sendMessage("Расширить ограничение §7» "+azure+"/c upgrade");
                player.sendMessage("");
                sound(player, SoundType.WRONG);
                return;
            }

            if(list.contains(args[1])) {
                player.sendMessage(red+"Игрок "+azure+args[1]+red+" уже в вашем клане.");
                sound(player, SoundType.WRONG);
                return;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if(target == null) {
                player.sendMessage(red + "Игрок " + azure + args[1] + red + " не найден.");
                sound(player, SoundType.WRONG);
                return;
            }

            clanInvite.put(args[1], player.getName());
            player.sendMessage("Игрок " + orange + args[1] + " §fуспешно приглашён в клан.");
            sound(player, SoundType.SUCCESS);

            TextComponent text = new TextComponent();
            TextComponent acceptButton = new TextComponent(hexTextComponent(lime+"[Принять]"));
            acceptButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/c accept "+player.getName()));
            TextComponent denyButton = new TextComponent(hexTextComponent(red+"[Отклонить]"));
            denyButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/c deny "+player.getName()));

            text.addExtra("\n");
            text.addExtra(hexTextComponent("Игрок "+azure+player.getName()
                    +" §fпригласил вас в клан "+orange+clan+"\n"));
            text.addExtra(acceptButton);
            text.addExtra(" §7| ");
            text.addExtra(denyButton);
            text.addExtra("\n");
            target.sendMessage(text);
            sound(target, SoundType.SUCCESS);

        } else if(args[0].equalsIgnoreCase("kick")) {
            if(clanDB.getClans(player.getName()).isEmpty()) {
                player.sendMessage("");
                player.sendMessage(red+"У вас нет клана.");
                player.sendMessage("Создать клан §7» "+orange+"/c new §7(название)");
                player.sendMessage("");
                sound(player, SoundType.WRONG);
                return;
            }
            String clan = clanDB.getClans(player.getName()).get(0);

            if(!clanDB.getOwner(clan).equals(player.getName())
            && clanDB.getPerms(clan, player.getName()).contains("kick")) {
                player.sendMessage(red+"Вы не можете кикать участников клана.");
                sound(player, SoundType.WRONG);
                return;
            }

            List<String> list = clanDB.getList(clan, true);

            if (!list.contains(args[1])) {
                player.sendMessage(red + "Игрок " + orange + args[1] + red + " не состоит в вашем клане.");
                sound(player, SoundType.WRONG);
                return;
            }
            list.remove(args[1]);
            clanDB.updateList(clan, true, list);
            player.sendMessage("Игрок " + azure + args[1] + " §fуспешно кикнут с клана.");
            sound(player, SoundType.SUCCESS);

        } else if(args[0].equalsIgnoreCase("upgrade")) {
            if(clanDB.getClans(player.getName()).isEmpty()) {
                player.sendMessage("");
                player.sendMessage(red+"У вас нет клана.");
                player.sendMessage("Создать клан §7» "+orange+"/c new §7(название)");
                player.sendMessage("");
                sound(player, SoundType.WRONG);
                return;
            }
            new ClanUpgradesGUI(player, clanDB.getClans(player.getName()).get(0));

        } else if(args[0].equalsIgnoreCase("pl")) {
            if (clanDB.getClans(player.getName()).isEmpty()) {
                player.sendMessage("");
                player.sendMessage(red + "У вас нет клана.");
                player.sendMessage("Создать клан §7» " + orange + "/c new §7(название)");
                player.sendMessage("");
                sound(player, SoundType.WRONG);
                return;
            }
            new ClanPlayersGUI(player, clanDB.getClans(player.getName()).get(0));

        } else if(args[0].equalsIgnoreCase("accept")) {
            if (player.getName().equals(args[1])) return;
            if (clanDB.getClans(player.getName()).isEmpty()) {
                if (!clanInvite.containsKey(player.getName()) || !clanInvite.get(player.getName()).equals(args[1])) {
                    player.sendMessage(red + "Игрок " + orange + args[1] + red + " не приглашал вас в клан.");
                    sound(player, SoundType.WRONG);
                    return;
                }
                String clan = clanDB.getClans(clanInvite.get(player.getName())).get(0);
                clanInvite.remove(player.getName());
                List<String> players = clanDB.getList(clan, true);
                players.add(player.getName());
                clanDB.updateList(clan, true, players);

                players.forEach(pl -> {
                    Player t = Bukkit.getPlayer(pl);
                    if (t != null)
                        t.sendMessage("Игрок " + orange + player.getName() + " §fвступил в клан.");
                });

            } else {
                //accept join
                if (!clanInvite.containsKey(args[1]) || !clanInvite.get(args[1]).equals(player.getName())) {
                    player.sendMessage(red + "Игрок " + orange + args[1] + red + " не отправлял заявку в клан.");
                    sound(player, SoundType.WRONG);
                    return;
                }
                String clan = clanDB.getClans(clanInvite.get(args[1])).get(0);

                if(!clanDB.getOwner(clan).equals(player.getName())
                        && clanDB.getPerms(clan, player.getName()).contains("accept")) {
                    player.sendMessage(red+"Вы не можете принимать заявки в клан.");
                    sound(player, SoundType.WRONG);
                    return;
                }

                clanInvite.remove(args[1]);
                List<String> players = clanDB.getList(clan, true);
                players.add(args[1]);
                clanDB.updateList(clan, true, players);

                players.forEach(pl -> {
                    Player t = Bukkit.getPlayer(pl);
                    if (t != null)
                        t.sendMessage("Игрок " + orange + args[1] + " §fвступил в клан.");
                });
            }

        } else if(args[0].equalsIgnoreCase("deny")) {
            if(player.getName().equals(args[1])) return;
            if (clanDB.getClans(player.getName()).isEmpty()) {
                if (!clanInvite.containsKey(player.getName()) || !clanInvite.get(player.getName()).equals(args[1])) {
                    player.sendMessage(red + "Игрок " + orange + args[1] + red + " не приглашал вас в клан.");
                    sound(player, SoundType.WRONG);
                    return;
                }
                Player by = Bukkit.getPlayer(clanInvite.get(player.getName()));
                if (by != null)
                    by.sendMessage("Игрок " + orange + player.getName()
                            + " §fотклонил приглашение в клан.");
                clanInvite.remove(player.getName());
            } else {
                //deny join
                if (!clanInvite.containsKey(args[1]) || !clanInvite.get(args[1]).equals(player.getName())) {
                    player.sendMessage(red + "Игрок " + orange + args[1] + red + " не отправлял заявку в клан.");
                    sound(player, SoundType.WRONG);
                    return;
                }
                String clan = clanDB.getClans(clanInvite.get(args[1])).get(0);

                if(!clanDB.getOwner(clan).equals(player.getName())
                        && clanDB.getPerms(clan, player.getName()).contains("accept")) {
                    player.sendMessage(red+"Вы не можете принимать заявки в клан.");
                    sound(player, SoundType.WRONG);
                    return;
                }

                clanInvite.remove(args[1]);

                Player by = Bukkit.getPlayer(clanInvite.get(args[1]));
                if (by != null)
                    by.sendMessage("Игрок " + orange + player.getName()
                            + " §fотклонил заявку в клан " + clan);
                clanInvite.remove(player.getName());
            }

        } else if(args[0].equalsIgnoreCase("icon")) {
            if (clanDB.getClans(player.getName()).isEmpty()) {
                player.sendMessage("");
                player.sendMessage(red + "У вас нет клана.");
                player.sendMessage("Создать клан §7» " + orange + "/c new §7(название)");
                player.sendMessage("");
                sound(player, SoundType.WRONG);
                return;
            }
            String clan = clanDB.getClans(player.getName()).get(0);
            Material mat = player.getInventory().getItemInMainHand().getType();
            if (mat.equals(Material.AIR)) {
                player.sendMessage(red + "Держите предмет в руке.");
                sound(player, SoundType.WRONG);
                return;
            }
            clanDB.updateIcon(clan, mat);
            player.sendMessage("Новая иконка клана §7» " + orange + mat);
            sound(player, SoundType.SUCCESS);

        } else if(args[0].equalsIgnoreCase("setspawn")) {
            if (clanDB.getClans(player.getName()).isEmpty()) {
                player.sendMessage("");
                player.sendMessage(red + "У вас нет клана.");
                player.sendMessage("Создать клан §7» " + orange + "/c new §7(название)");
                player.sendMessage("");
                sound(player, SoundType.WRONG);
                return;
            }
            String clan = clanDB.getClans(player.getName()).get(0);

            if(!clanDB.getOwner(clan).equals(player.getName())) {
                player.sendMessage(red+"Вы не можете менять точку спавна клана.");
                sound(player, SoundType.WRONG);
                return;
            }

            clanDB.updateLoc(clan, player.getLocation());
            player.sendMessage(lime+"Точка спавна клана успешно установлена.");
            sound(player, SoundType.SUCCESS);

        } else if(args[0].equalsIgnoreCase("spawn")) {
            if (clanDB.getClans(player.getName()).isEmpty()) {
                player.sendMessage("");
                player.sendMessage(red + "У вас нет клана.");
                player.sendMessage("Создать клан §7» " + orange + "/c new §7(название)");
                player.sendMessage("");
                sound(player, SoundType.WRONG);
                return;
            }
            String clan = clanDB.getClans(player.getName()).get(0);

            Location spawnLoc = clanDB.getLoc(clan);
            if (spawnLoc == null) {
                player.sendMessage(red + "Точка спавна клана не установлена.");
                sound(player, SoundType.WRONG);
                return;
            }
            player.teleport(spawnLoc);
            player.sendMessage(lime + "Телепортируем на спавн клана...");
            sound(player, SoundType.SUCCESS);

        } else if(args[0].equalsIgnoreCase("bal")) {
            if (clanDB.getClans(player.getName()).isEmpty()) {
                player.sendMessage("");
                player.sendMessage(red + "У вас нет клана.");
                player.sendMessage("Создать клан §7» " + orange + "/c new §7(название)");
                player.sendMessage("");
                sound(player, SoundType.WRONG);
                return;
            }
            player.sendMessage("Баланс клана §7» "+yellow+clanDB.getBal(clanDB.getClans(player.getName()).get(0))+"⛁");

        } else if(args[0].equalsIgnoreCase("pay")) {
            if (clanDB.getClans(player.getName()).isEmpty()) {
                player.sendMessage("");
                player.sendMessage(red + "У вас нет клана.");
                player.sendMessage("Создать клан §7» " + orange + "/c new §7(название)");
                player.sendMessage("");
                sound(player, SoundType.WRONG);
                return;
            }
            String clan = clanDB.getClans(player.getName()).get(0);

            if(!isInt(args[1])) {
                player.sendMessage(red+"В качестве суммы используются числа. §7(Пример: /c pay 15000)");
                sound(player, SoundType.WRONG);
                return;
            }
            int money = Integer.parseInt(args[1]);

            if(playerDB.getMoney(player.getName()) < money) {
                player.sendMessage("");
                player.sendMessage(red + "Недостаточно средств. §7(Баланс: " + yellow + playerDB.getMoney(player.getName()) + "⛁§7)");
                player.sendMessage("Вам нужно "+yellow+money+"⛁");
                player.sendMessage("");
                sound(player, SoundType.WRONG);
                return;
            }
            playerDB.setMoney(player.getName(), (playerDB.getMoney(player.getName())-money));

            clanDB.updateBal(clan, (clanDB.getBal(clan)+money));
            player.sendMessage("Новый баланс клана: "+yellow+clanDB.getBal(clan)+"⛁ §7("+lime+"+"+money+"⛁§7)");

        } else if(args[0].equalsIgnoreCase("take")) {
            if (clanDB.getClans(player.getName()).isEmpty()) {
                player.sendMessage("");
                player.sendMessage(red + "У вас нет клана.");
                player.sendMessage("Создать клан §7» " + orange + "/c new §7(название)");
                player.sendMessage("");
                sound(player, SoundType.WRONG);
                return;
            }
            String clan = clanDB.getClans(player.getName()).get(0);

            if(!isInt(args[1])) {
                player.sendMessage(red+"В качестве суммы используются числа. §7(Пример: /c take 5000)");
                sound(player, SoundType.WRONG);
                return;
            }
            int money = Integer.parseInt(args[1]);
            int clanBal = clanDB.getBal(clan);

            if(clanBal < money) {
                player.sendMessage("");
                player.sendMessage(red + "Недостаточно средств. §7(Баланс клана: " + yellow + clanBal + "⛁§7)");
                player.sendMessage("Вам нужно "+yellow+money+"⛁");
                player.sendMessage("");
                sound(player, SoundType.WRONG);
                return;
            }
            clanDB.updateBal(clan, (clanBal-money));
            playerDB.setMoney(player.getName(), (playerDB.getMoney(player.getName())+money));
            player.sendMessage("Новый баланс клана: "+yellow+clanDB.getBal(clan)+"⛁ §7("+red+"-"+money+"⛁§7)");

        } else if(args[0].equalsIgnoreCase("top")) {
            new ClanTopGUI(player);
        }
    }
}
