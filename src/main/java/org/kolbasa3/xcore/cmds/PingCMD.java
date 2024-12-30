package org.kolbasa3.xcore.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.kolbasa3.xcore.utils.PluginUtil.orange;

public class PingCMD extends AbstractCMD {

    public PingCMD() {
        super("ping");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;

        p.sendMessage("Ваш пинг §7» "+orange+p.getPing());
    }
}
