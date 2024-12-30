package org.kolbasa3.xcore.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.modules.smith.SmithGUI;

public class SmithCMD extends AbstractCMD {

    public SmithCMD() {
        super("smith");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        new SmithGUI(player);
    }
}
