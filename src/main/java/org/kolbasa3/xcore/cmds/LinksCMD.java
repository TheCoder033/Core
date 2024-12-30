package org.kolbasa3.xcore.cmds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.kolbasa3.xcore.utils.PluginUtil.orange;

public class LinksCMD extends AbstractCMD {

    public LinksCMD() {
        super("links");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;

        player.sendMessage("");
        player.sendMessage("Discord-группа сервера §7» "+orange+"https://discord.gg/rGWEnjEu");
        player.sendMessage("Сайт сервера §7» "+orange+"sw1.easydonate.ru");
        player.sendMessage("");
    }
}
