package org.kolbasa3.xcore.cmds.warps;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.cmds.AbstractCMD;
import org.kolbasa3.xcore.gui.WarpsGUI;
import org.kolbasa3.xcore.enums.WarpsType;

public class WarpsCMD extends AbstractCMD {

    public WarpsCMD() {
        super("warps");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;
        new WarpsGUI(p, WarpsType.SERVER);
    }
}
