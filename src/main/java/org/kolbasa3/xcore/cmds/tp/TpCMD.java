package org.kolbasa3.xcore.cmds.tp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.cmds.AbstractCMD;
import org.kolbasa3.xcore.modules.effects.EffectType;
import org.kolbasa3.xcore.modules.effects.Effects;

import static org.kolbasa3.xcore.utils.PluginUtil.isInt;
import static org.kolbasa3.xcore.utils.PluginUtil.red;

public class TpCMD extends AbstractCMD {

    public TpCMD() {
        super("tp");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;

        if(!p.hasPermission("tp")) {
            p.sendMessage(red+"У вас нет прав на использование данной команды.");
            return;
        }

        if(args.length == 2 && isInt(args[0]) && isInt(args[1])) {
            int x = Integer.parseInt(args[0]), z = Integer.parseInt(args[1]);
            p.teleport(new Location(p.getWorld(), x,
                    p.getWorld().getHighestBlockYAt(x, z), z));
            return;

        } else if(args.length == 3 && isInt(args[0]) && isInt(args[1])
        && isInt(args[2])) {
            int x = Integer.parseInt(args[0])
                    , y = Integer.parseInt(args[1])
                    , z = Integer.parseInt(args[2]);
            p.teleport(new Location(p.getWorld(), x, y, z));
            return;
        }

        Player t = Bukkit.getPlayer(args[0]);
        if(t != null) {
            p.teleport(t);
            new Effects(EffectType.TP).play(p.getLocation());
        }
    }
}
