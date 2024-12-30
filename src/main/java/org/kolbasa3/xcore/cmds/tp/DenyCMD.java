package org.kolbasa3.xcore.cmds.tp;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.cmds.AbstractCMD;

import java.util.ArrayList;
import java.util.List;

import static org.kolbasa3.xcore.cmds.tp.AcceptCMD.tpa;
import static org.kolbasa3.xcore.utils.PluginUtil.orange;
import static org.kolbasa3.xcore.utils.PluginUtil.red;

public class DenyCMD extends AbstractCMD {

    public DenyCMD() {
        super("tpdeny");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;

        if(args.length == 0) {
            p.sendMessage("Отклонить запрос на тп §7» "+orange+"/tpdeny §7(ник)");
            return;
        }

        if(!tpa.containsKey(args[0]) || !tpa.get(args[0]).equals(p.getName())) {
            p.sendMessage(red+"Игрок "+orange+args[0]+red+" не отправлял вам запрос на тп.");
            return;
        }

        tpa.remove(args[0], p.getName());
        Player t = Bukkit.getPlayer(args[0]);
        if(t != null) {
            t.sendMessage(red+"Игрок "+orange+p.getName()+red+" отклонил ваш запрос на тп.");
        }
        p.sendMessage("Запрос на тп от игрока "+orange+t.getName()+" §fуспешно "+red+"отклонён");
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
