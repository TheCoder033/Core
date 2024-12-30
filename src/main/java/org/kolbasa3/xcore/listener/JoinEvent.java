package org.kolbasa3.xcore.listener;

import com.destroystokyo.paper.event.player.PlayerConnectionCloseEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.kolbasa3.xcore.XCore;
import org.kolbasa3.xcore.modules.effects.Fountain;
import org.kolbasa3.xcore.modules.events.Events;
import org.kolbasa3.xcore.utils.AutoBroadcast;
import org.kolbasa3.xcore.enums.PushReason;

import java.util.ArrayList;
import java.util.HashMap;

import static org.kolbasa3.xcore.XCore.*;
import static org.kolbasa3.xcore.XCore.pushDB;
import static org.kolbasa3.xcore.listener.DamageEvent.pvp;
import static org.kolbasa3.xcore.modules.duels.Duel.duelCall;
import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class JoinEvent implements Listener {

    AutoBroadcast autoBroadcast = new AutoBroadcast();

    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent e) {
        if(Bukkit.getOnlinePlayers().size() >= cfg().getInt("max-online", 30)) {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_FULL, red+"Сервер заполнен.\n"+red+"Попробуйте зайти позже.");
        }

        long ban = pushDB.getTime(e.getName());
        if(ban != 0) {
            long different = ban-System.currentTimeMillis();
            StringBuilder sb = new StringBuilder();

            long second = (different / 1000) % 60;
            if(second > 0) {
                long minute = (different / (1000 * 60)) % 60;
                long hour = (different / (1000 * 60 * 60)) % 24;
                long days = different / (1000 * 60 * 60 * 24);
                if(days != 0) sb.append(days).append("дн. ");
                if (hour != 0) sb.append(hour).append("ч. ");
                if (minute != 0) sb.append(minute).append("м. ");
                sb.append(second).append("с.");

                String reason = switch (PushReason.valueOf(pushDB.getPush(e.getName()))) {
                    case NULL, OSK, SPAM -> "Неизвестно";
                    case HACK, HACK3 -> "Читы";
                    case DUPE -> "Дюп";
                    case PIAR -> "Пиар";
                };
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, red +"Вы забанены администрацией сервера.\n"+red+"Истекает через: "+orange
                        +sb+"\n"+red+"Причина: "+blue+reason);
            } else pushDB.delPush(e.getName());
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        String msg = playerDB.getJoinMsg(player.getName());
        if(msg != null)
            e.setJoinMessage(hex(/*getChat().getPlayerPrefix(player))
                    +*/ " §f"+player.getName()
                    +" "+msg));
        else e.setJoinMessage("["+lime+"+§f] §7"+player.getName());

        Bukkit.getScheduler().runTaskLater(XCore.getInstance(), task -> {
            new Fountain(false);
            new Fountain(true);
        }, 40);

        autoBroadcast.start();

        if(!player.hasPlayedBefore()) {
            player.sendMessage("");
            player.sendMessage("Бесплатный кейс §7» "+orange+"/free");
            player.sendMessage("Помощь по серверу §7» "+orange+"/help");
            player.sendMessage("");
            Bukkit.getScheduler().runTaskLater(XCore.getInstance(), task ->
                    player.sendTitle("§7⇓ §fКвесты §7⇓", blue+"/quest"), 60);
        }
        player.performCommand("evt");

        for(HashMap<String, String> teams : duelCall.keySet()) {
            if(teams.containsValue(player.getName())) {
                String from = teams.keySet().stream().toList().get(0);
                player.sendMessage("");
                player.sendMessage("Игрок "+orange+"§l"+from+" §fпригласил вас на дуэль.");
                player.sendMessage("Набор §7» "+orange+duelCall.get(teams).toUpperCase());
                player.sendMessage("Принять запрос §7» "+azure+"§l/duel "+from);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        chatCd.remove(player.getName());

        if(pvp.containsKey(player.getName()) || pvp.containsValue(player.getName())) {
            new ArrayList<>(pvp.keySet()).forEach(key -> {
                if(key.equals(player.getName()) || pvp.get(key).equals(player.getName())) {
                    player.setHealth(0);
                    pvp.remove(key);
                }
            });
        }

        autoBroadcast.stop();

        if(Events.eventPlayers.contains(player)) {
            Events.eventPlayers.remove(player);
            player.performCommand("spawn");
        }

        if(models.containsKey(player.getName())) {
            models.get(player.getName()).remove();
            models.remove(player.getName());
        }
    }

    @EventHandler
    public void onClose(PlayerConnectionCloseEvent e) {
        String player = e.getPlayerName();
        chatCd.remove(player);

        if(pvp.containsKey(player) || pvp.containsValue(player)) {
            new ArrayList<>(pvp.keySet()).forEach(key -> {
                if(key.equals(player) || pvp.get(key).equals(player)) {
                    pvp.remove(key);
                }
            });
        }

        autoBroadcast.stop();

        if(models.containsKey(player)) {
            models.get(player).remove();
            models.remove(player);
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        Player player = e.getPlayer();
        chatCd.remove(player.getName());

        if(pvp.containsKey(player.getName()) || pvp.containsValue(player.getName())) {
            new ArrayList<>(pvp.keySet()).forEach(key -> {
                if(key.equals(player.getName()) || pvp.get(key).equals(player.getName())) {
                    player.setHealth(0);
                    pvp.remove(key);
                }
            });
        }

        autoBroadcast.stop();

        if(Events.eventPlayers.contains(player)) {
            Events.eventPlayers.remove(player);
            player.performCommand("spawn");
        }

        if(models.containsKey(player.getName())) {
            models.get(player.getName()).remove();
            models.remove(player.getName());
        }
    }

    @EventHandler
    public void onAdvancement(PlayerAdvancementDoneEvent e) {
        e.message(Component.text(""));
    }
}
