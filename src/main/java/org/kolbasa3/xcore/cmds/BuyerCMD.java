package org.kolbasa3.xcore.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.gui.BuyerGUI;

public class BuyerCMD extends AbstractCMD {

    public BuyerCMD() {
        super("buyer");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        new BuyerGUI(player);
    }
}
