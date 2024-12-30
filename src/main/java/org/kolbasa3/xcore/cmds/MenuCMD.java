package org.kolbasa3.xcore.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.gui.MenuGUI;

public class MenuCMD extends AbstractCMD {

    public MenuCMD() {
        super("menu");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;
        new MenuGUI(p);
    }
}
