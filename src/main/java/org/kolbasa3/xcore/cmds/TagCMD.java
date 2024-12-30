package org.kolbasa3.xcore.cmds;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.enums.SoundType;

import static org.kolbasa3.xcore.XCore.playerDB;
import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class TagCMD extends AbstractCMD {

    public TagCMD() {
        super("tag");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;

        if(args.length == 0) {
            player.sendMessage("Поменять тег §7» "+orange+"/tag §7(текст)");
            return;
        }

        int coins = playerDB.getCoin(player.getName());
        if (coins >= 3) {
            playerDB.setCoin(player.getName(), (coins - 3));
            String tag = hex(args[0]);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " meta setsuffix " + tag);
            player.sendMessage("Новый тег: " + orange + tag);
            sound(player, SoundType.SUCCESS);
        }
    }
}
