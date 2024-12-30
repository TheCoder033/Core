package org.kolbasa3.xcore.cmds.tp;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.kolbasa3.xcore.XCore;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.cmds.AbstractCMD;
import org.kolbasa3.xcore.modules.events.Events;

import static org.kolbasa3.xcore.XCore.toggleDB;
import static org.kolbasa3.xcore.cmds.tp.AcceptCMD.tpa;
import static org.kolbasa3.xcore.utils.PluginUtil.orange;
import static org.kolbasa3.xcore.utils.PluginUtil.red;

public class TpaCMD extends AbstractCMD {

    public TpaCMD() {
        super("tpa");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;

        if(args.length == 0) {
            p.sendMessage("Отправить запрос на тп §7» "+orange+"/tpa §7(ник)");
            return;
        }

        if(p.getName().equals(args[0])) {
            p.sendMessage(red+"Вы не можете отправить запрос на тп самому себе...");
            return;
        }

        if(tpa.containsKey(p.getName()) && tpa.get(p.getName()).equals(args[0])) {
            p.sendMessage(red+"Запрос на тп уже отправлен игроку "+orange+args[0]);
            return;
        }

        Player t = Bukkit.getPlayer(args[0]);
        if(t == null) {
            p.sendMessage(red+"Игрок "+orange+args[0]+red+" не найден.");
            return;
        }

        if(toggleDB.getToggle(t.getName(), "tp")) {
            p.sendMessage(red+"Игрок "+orange+t.getName()+red+" отключил телепортации.");
            return;
        }

        if(Events.eventPlayers.contains(t)) {
            p.sendMessage(red+"Игрок "+orange+t.getName()+red+" находится на ивенте, где запрещён /tpa.");
            return;
        }

        tpa.put(p.getName(), t.getName());
        p.sendMessage("Запрос на тп успешно отправлен игроку "+orange+t.getName());

        TextComponent accept = new TextComponent("[Принять]");
        accept.setColor(ChatColor.of("#35FFA6"));
        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept "+p.getName()));
        TextComponent deny = new TextComponent("[Отклонить]");
        deny.setColor(ChatColor.of("#FB4B08"));
        deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpdeny "+p.getName()));

        TextComponent player = new TextComponent(p.getName());
        player.setColor(ChatColor.of("#3BE8FF"));

        TextComponent text = new TextComponent("\n");
        text.addExtra("Игрок ");
        text.addExtra(player);
        text.addExtra(" §fотправил вам запрос на телепортацию.");
        text.addExtra("\n");
        text.addExtra(accept);
        text.addExtra(" §7| ");
        text.addExtra(deny);
        text.addExtra("\n");
        t.sendMessage(text);

        Bukkit.getScheduler().runTaskLater(XCore.getInstance(), () ->
                tpa.remove(p.getName(), t.getName()), 6000);
    }
}
