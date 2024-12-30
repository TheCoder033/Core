package org.kolbasa3.xcore.cmds.homes;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.cmds.AbstractCMD;

import java.util.ArrayList;
import java.util.List;

import static org.kolbasa3.xcore.XCore.homesDB;
import static org.kolbasa3.xcore.utils.PluginUtil.orange;
import static org.kolbasa3.xcore.utils.PluginUtil.red;

public class DelHomeCMD extends AbstractCMD {

    public DelHomeCMD() {
        super("delhome");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;

        String home = "home";
        if(args.length > 0) home = args[0];

        if(!homesDB.getHomes(p.getName()).containsKey(home)) {
            p.sendMessage(red+"Точка дома "+orange+home+red+" не найдена.");
            return;
        }

        homesDB.delHome(p.getName(), home);
        p.sendMessage("Точка дома "+orange+home+" §fуспешно удалена.");
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if(args.length == 1) return new ArrayList<>(homesDB.getHomes(sender.getName()).keySet());
        return new ArrayList<>();
    }
}
