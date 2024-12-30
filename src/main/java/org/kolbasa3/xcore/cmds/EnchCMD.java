package org.kolbasa3.xcore.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.gui.ench.EnchGUI;

public class EnchCMD extends AbstractCMD {

    public EnchCMD() {
        super("ench");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        new EnchGUI(player);
    }
}
