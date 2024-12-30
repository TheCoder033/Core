package org.kolbasa3.xcore.cmds.warps;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.cmds.AbstractCMD;

import java.util.ArrayList;
import java.util.List;

import static org.kolbasa3.xcore.XCore.warpsDB;
import static org.kolbasa3.xcore.utils.PluginUtil.orange;
import static org.kolbasa3.xcore.utils.PluginUtil.red;

public class DisWarpCMD extends AbstractCMD {

    public DisWarpCMD() {
        super("diswarp");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;

        if(args.length == 0) {
            p.sendMessage("Дизлайкнуть варп §7» "+orange+"/diswarp §7(название)");
            return;
        }

        if(!warpsDB.getWarps().containsKey(args[0])) {
            p.sendMessage(red+"Варп " + orange + args[0] + red + " не найден.");
            return;
        }

        if(warpsDB.getRating(args[0]) <= 0) {
            p.sendMessage(red+"У варпа "+orange+args[0]+red+" уже минимальный рейтинг...");
            return;
        }

        warpsDB.updateRating(args[0], (warpsDB.getRating(args[0])-1));
        p.sendMessage("Вы поставили дизлайк варпу "+orange+args[0]);
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if(args.length == 1) return new ArrayList<>(warpsDB.getWarps().keySet());
        return new ArrayList<>();
    }
}
