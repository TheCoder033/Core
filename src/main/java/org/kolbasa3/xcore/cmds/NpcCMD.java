package org.kolbasa3.xcore.cmds;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.kolbasa3.xcore.XCore;
import org.kolbasa3.xcore.utils.Npc;

import java.util.List;
import java.util.Objects;

import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class NpcCMD extends AbstractCMD {

    public NpcCMD() {
        super("npc");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;

        if(!sender.hasPermission("npc")) {
            p.sendMessage(red+"У вас нет прав на использование данной команды.");
            return;
        }

        if(args[0].equalsIgnoreCase("spawn")) {
            Location loc = p.getLocation();
            List<String> list = cfg().getStringList("npc");
            list.add(locToStr(loc, " ", false)+","+args[1]+","+args[2]);
            cfg().set("npc", list);
            XCore.getInstance().saveConfig();

            new Npc(args[1], loc);

        } else if(args[0].equalsIgnoreCase("del")
                && p.getTargetEntity(5) != null) {
            Objects.requireNonNull(p.getTargetEntity(5)).remove();
        }
    }
}
