package org.kolbasa3.xcore.modules.custom;

import org.kolbasa3.xcore.XCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.utils.ItemUtil;

public class Emote {

    public Emote(Player p, String key) {
        String data = "";
        //EmoteLastHelmet.put(p.getName(), p.getInventory().getHelmet());
        p.getInventory().setHelmet(new ItemUtil(Material.PLAYER_HEAD, "", null).build(data));

        Bukkit.getScheduler().runTaskLater(XCore.getInstance(), task -> {
            /*p.getInventory().setHelmet(EmoteLastHelmet.get(p.getName()));
            EmoteLastHelmet.remove(p.getName());

             */
        }, 60);
    }
}
