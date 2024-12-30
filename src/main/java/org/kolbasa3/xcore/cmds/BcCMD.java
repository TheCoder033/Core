package org.kolbasa3.xcore.cmds;

import org.apache.commons.lang.text.StrBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.kolbasa3.xcore.enums.SoundType;

import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class BcCMD extends AbstractCMD {

    public BcCMD() {
        super("broadcast");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {

        if(!sender.hasPermission("bc")) {
            sender.sendMessage(red+"У вас нет прав на использование данной команды.");
            return;
        }

        if(args.length == 0) {
            sender.sendMessage("Сделать объявление §7» "+orange+"/bc §7(текст)");
            return;
        }

        StrBuilder sb = new StrBuilder();
        for(String str : args) {
            if(!sb.isEmpty()) sb.append(" ");
            sb.append(str);
        }

        int max = 40;
        if(sender.hasPermission("bc.60")) max = 60;
        else if(sender.hasPermission("bc.80")) max = 80;
        if(sb.toCharArray().length > max) {
            sender.sendMessage("");
            sender.sendMessage(red+"Максимальное количество символов: "+orange+max);
            sender.sendMessage(red+"Расширить ограничение §7» "+azure+"/don");
            sender.sendMessage("");
            return;
        }

        Bukkit.getOnlinePlayers().forEach(p -> {
            p.sendMessage(hex("Объявление: "+orange+sb+" §7(От "+sender.getName()+")"));
            sound(p, SoundType.SELECT);
        });
    }
}
