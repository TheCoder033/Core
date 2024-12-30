package org.kolbasa3.xcore.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.enums.SoundType;

import java.util.Arrays;

import static org.kolbasa3.xcore.XCore.playerDB;
import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class JoinCMD extends AbstractCMD {

    public JoinCMD() {
        super("join");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;

        if(args.length == 0) {
            playerDB.setJoinMsg(player.getName(), null);
            player.sendMessage("");
            player.sendMessage(lime+"Сообщение при входе успешно очищено.");
            player.sendMessage("Поменять сообщение при входе §7» "+orange+"/join §7(текст)");
            player.sendMessage("");
            return;
        }

        if(!player.hasPermission("XCore.join.msg")) {
            player.sendMessage(red+"У вас нет прав на использование данной команды.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        Arrays.stream(args).toList().forEach(arg -> {
            if(!sb.isEmpty()) sb.append(" ");
            sb.append(hex(arg));
        });

        playerDB.setJoinMsg(player.getName(), sb.toString());
        player.sendMessage("Новое сообщение при входе §7» " +
                /*hex(getChat().getPlayerPrefix(player))+" §f"+*/player.getName()
        +" "+ sb);
        sound(player, SoundType.SUCCESS);
    }
}
