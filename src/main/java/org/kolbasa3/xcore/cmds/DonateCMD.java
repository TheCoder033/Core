package org.kolbasa3.xcore.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.gui.DonateGUI;

public class DonateCMD extends AbstractCMD {

    public DonateCMD() {
        super("donate");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;
        new DonateGUI(p);
    }
}
