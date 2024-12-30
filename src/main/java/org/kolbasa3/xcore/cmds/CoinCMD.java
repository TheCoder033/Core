package org.kolbasa3.xcore.cmds;

import org.bukkit.command.CommandSender;

import static org.kolbasa3.xcore.XCore.playerDB;
import static org.kolbasa3.xcore.utils.PluginUtil.orange;

public class CoinCMD extends AbstractCMD {

    public CoinCMD() {
        super("coin");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {

        if(sender.hasPermission("XCore.coin") && args.length >= 2) {
            if(args[0].equalsIgnoreCase("set") && args.length >= 3) {
                playerDB.setCoin(args[1], Integer.parseInt(args[2]));
                sender.sendMessage("Новый баланс игрока "+args[1]+": "+orange+args[2]+"К");
            }
            else if(args[0].equalsIgnoreCase("add") && args.length >= 3) {
                playerDB.setCoin(args[1], (playerDB.getCoin(args[1])) + Integer.parseInt(args[2]));
                sender.sendMessage("Новый баланс игрока "+args[1]+": "+orange+playerDB.getCoin(args[1])+"К");
            }
            else if(args[0].equalsIgnoreCase("take") && args.length >= 3) {
                playerDB.setCoin(args[1], (playerDB.getCoin(args[1])) - Integer.parseInt(args[2]));
                sender.sendMessage("Новый баланс игрока "+args[1]+": "+orange+playerDB.getCoin(args[1])+"К");
            }
        }
    }
}
