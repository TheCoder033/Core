package org.kolbasa3.xcore.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.enums.SoundType;

import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class InvestCMD extends AbstractCMD {

    public InvestCMD() {
        super("invest");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;

        if(args.length == 0) {
            player.sendMessage("");
            player.sendMessage(orange+"/invest §7(сумма) » §fИнвестировать деньги в казну");
            player.sendMessage("§7*В конце вайпа топ-3 получают призы:");
            player.sendMessage("§71 место §7» "+orange+"10К");
            player.sendMessage("§72 место §7» "+orange+"5К");
            player.sendMessage("§73 место §7» "+orange+"2К");
            player.sendMessage("");
            sound(player, SoundType.WRONG);
            return;
        }

        if(!isInt(args[0]) || Integer.parseInt(args[0]) <= 0) {
            player.sendMessage(red+"В качестве суммы используются числа. §7(Пример: /invest 35000)");
            sound(player, SoundType.WRONG);
            return;
        }
        int money = Integer.parseInt(args[0]);

        /*
        if(!getEconomy().has(player, money)) {
            player.sendMessage("");
            player.sendMessage(red + "Недостаточно средств. §7(Баланс: " + yellow + getEconomy().getBalance(player) + "⛁§7)");
            player.sendMessage("Вам нужно " + yellow + money + "⛁");
            player.sendMessage("");
            sound(player, SoundType.WRONG);
            return;
        }
        getEconomy().withdrawPlayer(player, money);

        playerDB.setInvested(player.getName(), (playerDB.getInvested(player.getName())+money));
        player.sendMessage("");
        player.sendMessage(lime+"Успешная инвестиция.");
        player.sendMessage("Всего вы инвестировали §7» "+yellow+playerDB.getInvested(player.getName())+"⛁");
        player.sendMessage("");
        */
        sound(player, SoundType.SUCCESS);
    }
}
