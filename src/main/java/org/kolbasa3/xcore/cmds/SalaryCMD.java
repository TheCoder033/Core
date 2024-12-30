package org.kolbasa3.xcore.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.utils.Cooldowns;

import java.util.Calendar;

import static org.kolbasa3.xcore.XCore.playerDB;
import static org.kolbasa3.xcore.utils.PluginUtil.yellow;

public class SalaryCMD extends AbstractCMD {

    Cooldowns cooldowns = new Cooldowns();

    public SalaryCMD() {
        super("salary");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;

        if(cooldowns.addCd(p, "", Calendar.DAY_OF_WEEK, 1)) return;

        int money = 3000;
        if(p.hasPermission("salary.5")) money = 5000;
        if(p.hasPermission("salary.8")) money = 8000;
        if(p.hasPermission("salary.13")) money = 13000;
        if(p.hasPermission("salary.16")) money = 16000;
        if(p.hasPermission("salary.19")) money = 19000;

        playerDB.setMoney(p.getName(), (playerDB.getMoney(p.getName())+money));
        p.sendMessage("Вы успешно получили зарплату "+yellow+money+"⛁");
    }
}
