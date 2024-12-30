package org.kolbasa3.xcore.cmds.tp;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.cmds.AbstractCMD;
import org.kolbasa3.xcore.modules.effects.EffectType;
import org.kolbasa3.xcore.modules.effects.Effects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.kolbasa3.xcore.utils.PluginUtil.orange;
import static org.kolbasa3.xcore.utils.PluginUtil.red;

public class AcceptCMD extends AbstractCMD {

    public AcceptCMD() {
        super("tpaccept");
    }

    public static HashMap<String, String> tpa = new HashMap<>();

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;

        if(args.length == 0) {
            p.sendMessage("Принять запрос на тп §7» "+orange+"/tpaccept §7(ник)");
            return;
        }

        if(!tpa.containsKey(args[0]) || !tpa.get(args[0]).equals(p.getName())) {
            p.sendMessage(red+"Игрок "+orange+args[0]+red+" не отправлял вам запрос на тп.");
            return;
        }

        tpa.remove(args[0], p.getName());
        Player t = Bukkit.getPlayer(args[0]);
        if(t != null) {
            new Effects(EffectType.TP).play(t.getLocation());
            p.sendMessage("Телепортируем игрока "+orange+args[0]+" §fк вам...");
            t.sendMessage("Игрок "+orange+p.getName()+" §fпринял ваш запрос на тп.");
            t.teleport(p);
        } else {
            p.sendMessage(red+"Игрок "+orange+args[0]+" §fне в сети, отменяем запрос.");
        }
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if(args.length == 1) {
            List<String> list = new ArrayList<>();
            tpa.forEach((from, to) -> {
                if(from.equals(sender.getName())) list.add(to);
                else if(to.equals(sender.getName())) list.add(from);
            });
            return list;
        }
        return new ArrayList<>();
    }
}
