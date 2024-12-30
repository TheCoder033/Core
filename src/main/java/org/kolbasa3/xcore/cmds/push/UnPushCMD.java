package org.kolbasa3.xcore.cmds.push;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.kolbasa3.xcore.cmds.AbstractCMD;
import org.kolbasa3.xcore.enums.SoundType;

import static org.kolbasa3.xcore.XCore.pushDB;
import static org.kolbasa3.xcore.cmds.push.PushCMD.mute;
import static org.kolbasa3.xcore.utils.PluginUtil.*;
import static org.kolbasa3.xcore.utils.PluginUtil.sound;

public class UnPushCMD extends AbstractCMD {

    public UnPushCMD() {
        super("unpush");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission("unpush")) {
            sender.sendMessage(red + "У вас нет прав на использование данной команды.");
            return;
        }

        if (args.length < 2) {
            sender.sendMessage("Снять наказание §7» " + orange + "/unpush §7(ник) (ban/mute)");
            return;
        }

        String type;
        if (args[1].equalsIgnoreCase("ban")) {
            type = "Бан";
            pushDB.delPush(args[0]);
        } else {
            type = "Мут";
            mute.remove(args[0]);
        }

        Bukkit.getOnlinePlayers().stream()
                .filter(pl -> pl.hasPermission("ban.spy"))
                .forEach(pl -> {
                    pl.sendMessage(red + "С игрока " + orange + args[0] + red + " был снят " + azure + type);
                    sound(pl, SoundType.SELECT);
                });
    }
}
