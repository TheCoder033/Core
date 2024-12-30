package org.kolbasa3.xcore.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.modules.tal.TalGUI;

public class TalCMD extends AbstractCMD {

    public TalCMD() {
        super("tal");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        new TalGUI(player);
    }
}
