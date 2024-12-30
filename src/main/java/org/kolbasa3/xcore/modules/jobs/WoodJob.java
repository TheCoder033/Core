package org.kolbasa3.xcore.modules.jobs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.XCore;
import org.kolbasa3.xcore.modules.quests.QuestTask;
import org.kolbasa3.xcore.utils.Schematic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.kolbasa3.xcore.XCore.playerDB;
import static org.kolbasa3.xcore.utils.PluginUtil.*;
import static org.kolbasa3.xcore.utils.PluginUtil.cfg;

public class WoodJob {

    Schematic schematic = new Schematic();
    QuestTask questTask = new QuestTask();
    HashMap<Location, Integer> count = new HashMap<>();

    public void breakTree(Location loc, Player p) {
        int i = count.getOrDefault(loc, 0)+1;

        if(i >= 3) {
            count.remove(loc);
            Bukkit.getScheduler().runTask(XCore.getInstance(), task -> schematic.paste(loc, "woodAir"));

            if(questTask.getTaskList(p.getName()) != null
                    && questTask.getTaskList(p.getName()).containsKey("wood"))
                questTask.addProgress(p);

            int prize = 30;
            if(p.hasPermission("woodbooster6")) prize = 180;
            else if(p.hasPermission("woodbooster4")) prize = 120;
            else if(p.hasPermission("woodbooster2")) prize = 60;

            playerDB.setMoney(p.getName(), (playerDB.getMoney(p.getName())+prize));
            p.sendActionBar("Вы получили: "+yellow+prize+"⛁");

            Bukkit.getScheduler().runTaskLater(XCore.getInstance(), task ->
                    schematic.paste(loc.clone().add(0, -1, 0), "wood"), 100);

        } else {
            Bukkit.getScheduler().runTask(XCore.getInstance(), task ->
                    loc.getBlock().setType(Material.OAK_LOG));
            count.put(loc, i);
            p.sendActionBar("Осталось сломать: "+azure+(3-i)+" §fраз(а)");
        }
    }

    public List<Location> getLocList() {
        List<Location> list = new ArrayList<>();
        cfg().getStringList("wood").forEach(str -> list.add(strToLoc(str, "\\s", true)));
        return list;
    }
}
