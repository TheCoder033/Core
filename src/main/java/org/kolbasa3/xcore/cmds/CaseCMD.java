package org.kolbasa3.xcore.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.XCore;
import org.kolbasa3.xcore.modules.cases.Case;
import org.kolbasa3.xcore.modules.cases.CaseType;

import static org.kolbasa3.xcore.XCore.playerDB;
import static org.kolbasa3.xcore.utils.PluginUtil.cfg;
import static org.kolbasa3.xcore.utils.PluginUtil.locToStr;

public class CaseCMD extends AbstractCMD {

    public CaseCMD() {
        super("case");
    }

    Case caseUtil = new Case();

    @Override
    public void execute(CommandSender sender, String label, String[] args) {

        if(sender.hasPermission("XCore.case") && args.length >= 2) {
            if(args[0].equalsIgnoreCase("set") && sender instanceof Player player) {
                cfg().set("case."+args[1].toLowerCase(), locToStr(player.getLocation(), " ", false));
                XCore.getInstance().saveConfig();
                caseUtil.set(CaseType.valueOf(args[1].toUpperCase()), player.getLocation());

            } else if(args[0].equalsIgnoreCase("setkey") && args.length >= 4) {
                playerDB.setKey(args[1], CaseType.valueOf(args[2].toUpperCase()), Integer.parseInt(args[3]));
            } else if(args[0].equalsIgnoreCase("addkey") && args.length >= 4) {
                playerDB.setKey(args[1], CaseType.valueOf(args[2].toUpperCase()), (playerDB.getKey(args[1], CaseType.valueOf(args[2].toUpperCase()))+Integer.parseInt(args[3])));
            }
        }
    }
}
