package org.kolbasa3.xcore.listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.kolbasa3.xcore.XCore;

import static org.kolbasa3.xcore.cmds.push.PushCMD.mute;
import static org.kolbasa3.xcore.cmds.push.PushCMD.muteReason;
import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class ChatEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncChatEvent e) {
        if(e.isCancelled()) return;
        Player p = e.getPlayer();
        String m = componentToString(e.message());
        e.setCancelled(true);

        if(mute.containsKey(p.getName())) {
            long different = mute.get(p.getName()) - System.currentTimeMillis();
            StringBuilder sb = new StringBuilder();

            long second = (different / 1000) % 60;
            if (second > 0) {
                long minute = (different / (1000 * 60)) % 60;
                if (minute != 0) sb.append(minute).append("м. ");
                sb.append(second).append("с.");

                String reason = switch (muteReason.get(p.getName())) {
                    case NULL, HACK, HACK3, DUPE, PIAR -> "Неизвестно";
                    case OSK -> "Оск";
                    case SPAM -> "Спам";
                };
                p.sendMessage("");
                p.sendMessage(red+"Вы не можете писать в чат во время мута.");
                p.sendMessage(red+"Причина мута: "+azure+reason);
                p.sendMessage("Размут через §7» "+orange+sb);
                p.sendMessage("");
                return;
            } else {
                mute.remove(p.getName());
                muteReason.remove(p.getName());
            }
        }

        int cd = 1;
        if(m.startsWith("!")) cd = 2;
        if(chatCd.containsKey(p.getName())) {
            long seconds = cd-(System.currentTimeMillis()-chatCd.get(p.getName()))/1000;
            if(seconds <= 0) chatCd.remove(p.getName());
            else {
                p.sendMessage(red+"Чат будет доступен через "+orange+seconds+"сек.");
                return;
            }
        }
        chatCd.put(p.getName(), System.currentTimeMillis());

        if(cfg().getStringList("banword").stream().anyMatch(m::contains)) {
            p.sendMessage(red+"В вашем сообщении содержатся запрещённые символы.");
            return;
        }

        TextComponent text = new TextComponent();
        TextComponent player = new TextComponent(p.getName());
        player.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(
                "§7Нажмите для отправки\n§7личного сообщения.")
        ));
        player.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/m "+p.getName()+" "));

        if(m.startsWith("!")) {
            if(m.equals("!") || m.equals(" !")) return;
            text.addExtra(hexTextComponent("§f[&#BBFF33ɢ§f] "+/*getChat().getPlayerPrefix(p)
                    +/*" §f"));
            text.addExtra(player);
            text.addExtra(hexTextComponent(" §7» &#BBFF33"+m.replaceFirst("!", "")
                    .replaceFirst(" ", "").toLowerCase()));

            Bukkit.getOnlinePlayers().forEach(pl -> pl.sendMessage(text));

        } else {
            text.addExtra(hexTextComponent("§f[&#FFBB33ʟ§f] " +/* getChat().getPlayerPrefix(p)
                    +*/ " §f"));
            text.addExtra(player);
            text.addExtra(hexTextComponent(" §7» &#FFBB33"+m.toLowerCase()));

            Bukkit.getScheduler().runTask(XCore.getInstance(), task ->
                    p.getWorld().getNearbyPlayers(p.getLocation(), 100)
                            .forEach(pl -> pl.sendMessage(text)));
        }
    }
}
