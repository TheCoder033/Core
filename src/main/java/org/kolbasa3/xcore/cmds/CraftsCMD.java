package org.kolbasa3.xcore.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.gui.craft.CraftGUI;

public class CraftsCMD extends AbstractCMD {

    public CraftsCMD() {
        super("crafts");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        new CraftGUI(player);
    }
}
