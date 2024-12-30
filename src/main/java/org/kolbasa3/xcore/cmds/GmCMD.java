package org.kolbasa3.xcore.cmds;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class GmCMD extends AbstractCMD {

    public GmCMD() {
        super("gm");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;

        if(!sender.hasPermission("gm")) {
            sender.sendMessage(red+"У вас нет прав на использование данной команды.");
            return;
        }

        GameMode gm = GameMode.SURVIVAL;
        gm = switch (args[0]) {
            case "1" -> GameMode.CREATIVE;
            case "2" -> GameMode.ADVENTURE;
            case "3" -> GameMode.SPECTATOR;
            default -> gm;
        };
        p.setGameMode(gm);
    }
}
