package org.kolbasa3.xcore.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.gui.ExpGUI;

public class ExpCMD extends AbstractCMD {

    public ExpCMD() {
        super("exp");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        new ExpGUI(player);
    }
}
