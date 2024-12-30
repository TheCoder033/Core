package org.kolbasa3.xcore.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.gui.CasinoGUI;
import org.kolbasa3.xcore.enums.SoundType;

import static org.kolbasa3.xcore.XCore.playerDB;
import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class CasinoCMD extends AbstractCMD {

    public CasinoCMD() {
        super("casino");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;

        if(args.length == 0) {
            player.sendMessage(orange+"/bet §7(сумма) » §fНачать игру");
            sound(player, SoundType.WRONG);
            return;
        }

        if(!isInt(args[0]) || Integer.parseInt(args[0]) <= 0) {
            player.sendMessage(red+"В качестве суммы используются числа. §7(Пример: /bet 85000)");
            sound(player, SoundType.WRONG);
            return;
        }
        int money = Integer.parseInt(args[0]);

        if(playerDB.getMoney(player.getName()) < money) {
            player.sendMessage("");
            player.sendMessage(red + "Недостаточно средств. §7(Баланс: " + yellow +playerDB.getMoney(player.getName())+ "⛁§7)");
            player.sendMessage("Вам нужно " + yellow + money + "⛁");
            player.sendMessage("");
            sound(player, SoundType.WRONG);
            return;
        }
        playerDB.setMoney(player.getName(), (playerDB.getMoney(player.getName())-money));

        casino.put(player.getName(), money);
        new CasinoGUI(player);
        sound(player, SoundType.SUCCESS);
    }
}
