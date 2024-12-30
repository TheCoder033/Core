package org.kolbasa3.xcore.cmds.warps;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.cmds.AbstractCMD;

import java.util.ArrayList;
import java.util.List;

import static org.kolbasa3.xcore.XCore.warpsDB;
import static org.kolbasa3.xcore.utils.PluginUtil.orange;
import static org.kolbasa3.xcore.utils.PluginUtil.red;

public class DelWarpCMD extends AbstractCMD {

    public DelWarpCMD() {
        super("delwarp");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;

        if(args.length == 0) {
            p.sendMessage("Удалить варп §7» "+orange+"/dwarp §7(название)");
            return;
        }

        if(!warpsDB.getWarps().containsKey(args[0])
        || (!warpsDB.getOwner(args[0]).equals(p.getName())
                && !p.hasPermission("delwarp.other"))) {
            p.sendMessage(red+"Варп " + orange + args[0] + red + " не найден.");
            return;
        }

        warpsDB.delWarp(args[0]);
        p.sendMessage("Варп "+orange+args[0]+" §fуспешно удалён.");
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if(args.length == 1) {
            List<String> list = new ArrayList<>();
            warpsDB.getWarps().keySet().forEach(warp -> {
                if(warpsDB.getOwner(args[0]) != null
                        && warpsDB.getOwner(args[0]).equals(sender.getName())
                        || sender.hasPermission("delwarp.other")) list.add(warp);
            });
            return list;
        }
        return new ArrayList<>();
    }
}
