package org.kolbasa3.xcore.modules.quests;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.kolbasa3.xcore.XCore;

public class QuestEvent implements Listener {

    QuestTask questTask = new QuestTask();

    @EventHandler
    public void onJump(PlayerJumpEvent e) {
        Player p = e.getPlayer();
        if(questTask.getTaskList(p.getName()) != null
                && questTask.getTaskList(p.getName()).containsKey("jump")) {
            questTask.addProgress(p);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player p && !(e.getEntity() instanceof Player)) {
            if(questTask.getTaskList(p.getName()) != null && questTask.getTaskList(p.getName()).containsKey("kill")
                    && questTask.getTaskList(p.getName()).get("kill").equals(e.getEntity().getType().toString())) {
                Bukkit.getScheduler().runTaskLater(XCore.getInstance(), task -> {
                    if(e.getEntity().isDead()) questTask.addProgress(p);
                }, 10);
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();

        if(questTask.getTaskList(player.getName()) != null && questTask.getTaskList(player.getName()).containsKey("mine")
                && questTask.getTaskList(player.getName()).get("mine").equals(block.getType().toString())) {
            questTask.addProgress(player);
        }
    }
}
