package org.kolbasa3.xcore.cmds.homes;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.cmds.AbstractCMD;
import org.kolbasa3.xcore.gui.HomesGUI;

import static org.kolbasa3.xcore.XCore.homesDB;
import static org.kolbasa3.xcore.utils.PluginUtil.orange;
import static org.kolbasa3.xcore.utils.PluginUtil.red;

public class HomesCMD extends AbstractCMD {

    public HomesCMD() {
        super("homes");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;

        if(homesDB.getHomes(p.getName()).isEmpty()) {
            p.sendMessage("");
            p.sendMessage(red+"У вас нет точек домов.");
            p.sendMessage("Создать точку дома §7» "+orange+"/shome §7(название)");
            p.sendMessage("");
            return;
        }
        new HomesGUI(p);
    }
}
