package org.kolbasa3.xcore.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.gui.custom.ModelGUI;

public class ModelCMD extends AbstractCMD {

    public ModelCMD() {
        super("models");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        new ModelGUI(player);
    }
}
