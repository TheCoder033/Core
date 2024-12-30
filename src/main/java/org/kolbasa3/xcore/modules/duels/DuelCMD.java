package org.kolbasa3.xcore.modules.duels;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.cmds.AbstractCMD;

import java.util.HashMap;

import static org.kolbasa3.xcore.modules.duels.Duel.duelCall;
import static org.kolbasa3.xcore.modules.duels.Duel.selectingDuelKit;

public class DuelCMD extends AbstractCMD {

    public DuelCMD() {
        super("duel");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;

        if(args.length == 0) {

        } else {
            String target = args[0].toLowerCase();
            for(HashMap<String, String> teams : duelCall.keySet()) {
                if(teams.containsKey(target)
                && teams.get(target).equals(p.getName())) {
                    p.sendMessage("");
                    p.sendMessage("Подготовка к дуэли...");
                    p.sendMessage("Набор » "+duelCall.get(teams));
                    return;
                }
            }

            selectingDuelKit.put(p.getName(), target);
            new DuelKitGUI(p);

            HashMap<String, String> teams = new HashMap<>();
            teams.put(p.getName(), target);
            duelCall.put(teams, "");
        }
    }
}
