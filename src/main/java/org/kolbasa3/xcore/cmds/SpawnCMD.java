package org.kolbasa3.xcore.cmds;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCMD extends AbstractCMD {

    public SpawnCMD() {
        super("spawn");
    }

    private static final Location SPAWN_LOC = new Location(Bukkit.getWorld("air"), 0.481, 80, 0.492, -89.1F, 7.9F);

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;

        p.teleport(SPAWN_LOC);
    }
}
