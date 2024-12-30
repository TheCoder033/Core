package org.kolbasa3.xcore.cmds;

import com.google.common.collect.Lists;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static org.kolbasa3.xcore.XCore.toggleDB;
import static org.kolbasa3.xcore.utils.PluginUtil.orange;
import static org.kolbasa3.xcore.utils.PluginUtil.red;

public class ToggleCMD extends AbstractCMD {

    public ToggleCMD() {
        super("toggle");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;

        if (args.length == 0 || (!args[0].equalsIgnoreCase("tp")
                && !args[0].equalsIgnoreCase("msg")
                && !args[0].equalsIgnoreCase("pay"))) {
            p.sendMessage("Переключить возможности §7» "+orange+"/toggle §7(tp/msg/pay)");
            return;
        }

        String key = args[0].toLowerCase();
        if (!p.hasPermission("toggle."+key)) {
            p.sendMessage(red+"У вас нет прав на использование данной команды.");
            return;
        }
        toggleDB.setToggle(p.getName(), key, !toggleDB.getToggle(p.getName(), key));

        String state = "Выключены";
        String m = switch (key) {
            case "msg" -> "Личные сообщения";
            case "pay" -> "Переводы";
            default -> "Телепортации";
        };

        p.sendMessage(m+" успешно "+state);
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if(args.length == 1) return Lists.newArrayList("tp", "msg", "pay");
        return new ArrayList<>();
    }
}
