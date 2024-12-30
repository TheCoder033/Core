package org.kolbasa3.xcore.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.gui.JobsGUI;

public class JobsCMD extends AbstractCMD {

    public JobsCMD() {
        super("jobs");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        new JobsGUI(player);
    }
}
