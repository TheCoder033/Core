package org.kolbasa3.xcore.utils;

import org.bukkit.entity.Player;

import java.util.Calendar;

import static org.kolbasa3.xcore.XCore.cdDB;
import static org.kolbasa3.xcore.utils.PluginUtil.orange;
import static org.kolbasa3.xcore.utils.PluginUtil.red;

public class Cooldowns {

    public boolean addCd(Player p, String type, int date, int time) {
        if(p.hasPermission("cd.off")) {
            if((cdDB.getCd(p.getName()).containsKey(type))) cdDB.delCd(p.getName(), type);
            return false;
        }

        if(type.equals("kit")) {
            if(p.hasPermission("kit.lite")) {
                date = Calendar.HOUR;
                time = 12;
            }
            if(p.hasPermission("kit.prem")) {
                date = Calendar.HOUR;
                time = 20;
            }
            if(p.hasPermission("kit.gold")) {
                date = Calendar.HOUR;
                time = 24;
            }
            if(p.hasPermission("kit.admin")) {
                date = Calendar.HOUR;
                time = 30;
            }
            if(p.hasPermission("kit.boss")) {
                date = Calendar.HOUR;
                time = 35;
            }
            if(p.hasPermission("kit.ultra")) {
                date = Calendar.HOUR;
                time = 48;
            }
        }

        Calendar from = Calendar.getInstance(), to = Calendar.getInstance();
        if(cdDB.getCd(p.getName()).containsKey(type)) {
            to.setTimeInMillis(cdDB.getCd(p.getName()).get(type));
            to.add(date, time);

            if(from.after(to)) {
                cdDB.delCd(p.getName(), type);
                return false;
            } else {
                long different = to.getTimeInMillis()-from.getTimeInMillis();
                StringBuilder sb = new StringBuilder();

                long second = (different / 1000) % 60;
                long minute = (different / (1000 * 60)) % 60;
                long hour = (different / (1000 * 60 * 60)) % 24;
                long days = different / (1000 * 60 * 60 * 24);
                if(days != 0) sb.append(hour).append("дн. ");
                if(hour != 0) sb.append(hour).append("ч. ");
                if(minute != 0) sb.append(minute).append("мин. ");
                sb.append(second).append("сек.");
                p.sendMessage(red+"Команда будет доступна через "+orange+sb);
                return true;
            }
        } else {
            cdDB.addCd(p.getName(), type);
            return false;
        }
    }
}
