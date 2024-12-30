package org.kolbasa3.xcore.utils;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.kolbasa3.xcore.XCore;

import java.util.List;
import java.util.Random;

import static org.kolbasa3.xcore.utils.PluginUtil.orange;

public class AutoBroadcast {

    private final List<String> msgList = Lists.newArrayList(
            "Покупка привилегий §7» "+orange+"sw1.easydonate.ru",
            "Discord-группа сервера §7» "+orange+"https://discord.gg/rGWEnjEu",
            "Привилегии §7» "+orange+"/don",
            "Донат-магазин §7» "+orange+"/dshop",
            "Помощь по серверу §7» "+orange+"/help",
            "Создать регион §7» "+orange+"/rg new §7(название)",
            "Создать клан §7» "+orange+"/c new §7(название)",
            "Варпы §7» "+orange+"/warps",
            "Аукцион §7» "+orange+"/ah",
            "Новые крафты §7» "+orange+"/crafts",
            "Новые чары §7» "+orange+"/ench"
    );

    public AutoBroadcast() {}
    public BukkitTask task;
    public int lastMsg;
    public boolean taskStarted;

    public void start() {
        if(taskStarted) return;
        taskStarted = true;
        task = Bukkit.getScheduler().runTaskTimer(XCore.getInstance(), () ->
                Bukkit.getOnlinePlayers().forEach(player ->
                player.sendMessage(getMsg())), 0, 3000);
    }

    public void stop() {
        if(task != null && !task.isCancelled()) task.cancel();
    }

    private String getMsg() {
        int random = new Random().nextInt(msgList.size());
        if(lastMsg == random) return getMsg();
        return msgList.get(random);
    }
}
