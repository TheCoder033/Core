package org.kolbasa3.xcore.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.gui.HelpGUI;

public class HelpCMD extends AbstractCMD {

    public HelpCMD() {
        super("help");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        new HelpGUI(player);
    }
}
