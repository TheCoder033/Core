package org.kolbasa3.xcore.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandEvent implements Listener {

    @EventHandler
    public void onCmd(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String msg = e.getMessage();

        if(!p.hasPermission("kit.select") && msg.contains("/kit")) {
            String type = "start";
            if(p.hasPermission("essentials.kits.lite")) type = "lite";
            else if(p.hasPermission("essentials.kits.prem")) type = "prem";
            else if(p.hasPermission("essentials.kits.gold")) type = "gold";
            else if(p.hasPermission("essentials.kits.admin")) type = "admin";
            else if(p.hasPermission("essentials.kits.boss")) type = "boss";
            else if(p.hasPermission("essentials.kits.ultra")) type = "ultra";
            e.setMessage("/kit "+type);
        }
    }
}
