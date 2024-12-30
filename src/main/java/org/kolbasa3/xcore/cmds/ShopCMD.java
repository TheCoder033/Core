package org.kolbasa3.xcore.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.gui.ShopGUI;

public class ShopCMD extends AbstractCMD {

    public ShopCMD() {
        super("shop");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        new ShopGUI(player);
    }
}
