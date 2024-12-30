package org.kolbasa3.xcore.cmds.warps;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.cmds.AbstractCMD;

import static org.kolbasa3.xcore.XCore.warpsDB;
import static org.kolbasa3.xcore.utils.PluginUtil.orange;
import static org.kolbasa3.xcore.utils.PluginUtil.red;

public class SetWarpCMD extends AbstractCMD {

    public SetWarpCMD() {
        super("setwarp");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;

        if(!p.hasPermission("setwarp")) {
            sender.sendMessage(red+"У вас нет прав на использование данной команды.");
            return;
        }

        if(args.length == 0) {
            p.sendMessage("Создать варп §7» "+orange+"/setwarp §7(название)");
            return;
        }

        if(warpsDB.getWarps().containsKey(args[0])) {
            p.sendMessage(red+"Варп " + orange + args[0] + red + " уже существует.");
            return;
        }

        warpsDB.setWarp(p.getName(), args[0], p.getLocation());
        p.sendMessage("Варп "+orange+args[0]+" §fуспешно создан.");
    }
}
