package org.kolbasa3.xcore.modules.kits;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.cmds.AbstractCMD;
import org.kolbasa3.xcore.utils.Cooldowns;

import static java.util.Calendar.SECOND;

public class KitsCMD extends AbstractCMD {

    public KitsCMD() {
        super("kits");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;
        if(new Cooldowns().addCd((Player) sender, "kits", SECOND, 1)) return;
        new KitListGUI(p);
    }
}
