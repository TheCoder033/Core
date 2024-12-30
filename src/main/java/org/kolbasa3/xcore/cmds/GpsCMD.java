package org.kolbasa3.xcore.cmds;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class GpsCMD extends AbstractCMD {

    public GpsCMD() {
        super("gps");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;

        if(args.length < 2 || !isInt(args[0]) || !isInt(args[1])) {
            player.sendMessage("Направить компас §7» "+orange+"/gps §7(x, z)");
            return;
        }

        int i = player.getInventory().first(Material.COMPASS);
        if (i == -1) {
            player.sendMessage(red+"В вашем инвентаре нет компаса.");
            return;
        }
        ItemStack is = player.getInventory().getItem(i);
        if (is == null) return;

        int x = Integer.parseInt(args[0]), z = Integer.parseInt(args[1]);
        Location target = new Location(player.getWorld(), x,
                player.getWorld().getHighestBlockYAt(x, z), z);

        CompassMeta cm = (CompassMeta) is.getItemMeta();
        cm.setLodestone(target);
        cm.setLodestoneTracked(false);
        is.setItemMeta(cm);

        player.sendMessage("Компас успешно направлен на "+orange+x+"x "+z+"z");
    }
}
