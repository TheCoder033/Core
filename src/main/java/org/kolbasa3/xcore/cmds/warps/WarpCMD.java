package org.kolbasa3.xcore.cmds.warps;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.cmds.AbstractCMD;
import org.kolbasa3.xcore.enums.SoundType;

import java.util.ArrayList;
import java.util.List;

import static org.kolbasa3.xcore.XCore.warpsDB;
import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class WarpCMD extends AbstractCMD {

    public WarpCMD() {
        super("warp");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;

        if(args.length == 0) {
            p.sendMessage("Телепортация на варп §7» "+orange+"/warp §7(название)");
            return;
        }

        if(!warpsDB.getWarps().containsKey(args[0])) {
            p.sendMessage(red+"Варп " + orange + args[0] + red + " не найден.");
            return;
        }

        p.teleport(warpsDB.getWarps().get(args[0]));
        p.sendMessage("Телепортируем на варп "+orange+args[0]);
        sound(p, SoundType.SUCCESS);

        if(!p.hasPermission("warp_rating_cd")) {
            p.sendMessage("");
            p.sendMessage("Оцените варп §7(по желанию)§f:");
            p.sendMessage(orange+"/likewarp "+args[0]+" §7» §fЛайкнуть варп.");
            p.sendMessage(orange+"/diswarp "+args[0]+" §7» §fДизлайкнуть варп.");
            p.sendMessage("");
        }
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if(args.length == 1) return new ArrayList<>(warpsDB.getWarps().keySet());
        return new ArrayList<>();
    }
}
