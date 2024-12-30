package org.kolbasa3.xcore.cmds.push;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.cmds.AbstractCMD;
import org.kolbasa3.xcore.enums.PushReason;
import org.kolbasa3.xcore.enums.SoundType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static org.kolbasa3.xcore.XCore.pushDB;
import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class PushCMD extends AbstractCMD {

    public PushCMD() {
        super("push");
    }

    public static HashMap<String, Long> mute = new HashMap<>();
    public static HashMap<String, PushReason> muteReason = new HashMap<>();

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission("push")) {
            sender.sendMessage(red+"У вас нет прав на использование данной команды.");
            return;
        }

        if (args.length == 0) {
            sender.sendMessage("Наказания §7» "+orange+"/push §7(ник) (hack/hack3/dupe/piar/osk/spam)");
            return;
        }

        PushReason br;
        if (args.length == 1) br = PushReason.NULL;
        else br = PushReason.valueOf(args[1].toUpperCase());

        Player t = Bukkit.getPlayer(args[0]);

        if(br.equals(PushReason.HACK) || br.equals(PushReason.HACK3)
                || br.equals(PushReason.DUPE)
        || br.equals(PushReason.PIAR)) {
            if (t != null) t.kickPlayer("Потеряно соединение с сервером.");
            pushDB.ban(args[0], br);
        } else {
            Calendar c = Calendar.getInstance();
            int time = 15;
            if(br.equals(PushReason.OSK)) time = 30;
            c.add(Calendar.MINUTE, time);
            mute.put(args[0], c.getTimeInMillis());
            muteReason.put(args[0], br);
        }

        String reason = switch(br) {
            case NULL -> null;
            case HACK, HACK3 -> "Читы";
            case DUPE -> "Дюп";
            case PIAR -> "Пиар";
            case OSK -> "Оск";
            case SPAM -> "Спам";
        };

        Bukkit.getOnlinePlayers().stream()
                .filter(pl -> pl.hasPermission("ban.spy"))
                .forEach(pl -> {
                    pl.sendMessage(red+"Игрок "+orange+args[0]+red+" был наказан за "+azure+reason);
                    sound(pl, SoundType.SELECT);
                });
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if(args.length == 2) return Lists.newArrayList("hack", "hack3", "dupe", "spam", "osk"
        , "piar");
        return new ArrayList<>();
    }
}
