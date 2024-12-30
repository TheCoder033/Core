package org.kolbasa3.xcore.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.LlamaSpit;
import org.bukkit.entity.Player;

public class SpitCMD extends AbstractCMD {

    public SpitCMD() {
        super("spit");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        player.launchProjectile(LlamaSpit.class);
    }
}
