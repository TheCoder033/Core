package org.kolbasa3.xcore.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.modules.cases.CaseType;
import org.kolbasa3.xcore.utils.Cooldowns;

import static java.util.Calendar.DAY_OF_WEEK;
import static org.kolbasa3.xcore.XCore.playerDB;
import static org.kolbasa3.xcore.utils.PluginUtil.lime;
import static org.kolbasa3.xcore.utils.PluginUtil.orange;

public class FreeCMD extends AbstractCMD {

    public FreeCMD() {
        super("free");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;

        if(new Cooldowns().addCd(p, "free", DAY_OF_WEEK, 7)) return;

        playerDB.setKey(p.getName(), CaseType.ITEMS,  playerDB.getKey(p.getName(), CaseType.ITEMS)+1);
        p.sendMessage("");
        p.sendMessage(lime+"Выдаём бесплатный кейс с вещами.");
        p.sendMessage("Открыть кейс §7» "+orange+"/warp case");
        p.sendMessage("");
    }
}
