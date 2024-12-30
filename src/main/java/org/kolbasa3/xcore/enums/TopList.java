package org.kolbasa3.xcore.enums;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;

import java.util.*;

import static org.kolbasa3.xcore.XCore.playerDB;
import static org.kolbasa3.xcore.utils.PluginUtil.red;

public enum TopList {

    KILLS,
    MONEY,
    COINS;

    public String getTopPlayer(int lvl) {
        HashMap<String, Integer> kills = new HashMap<>();
        Bukkit.getOnlinePlayers().forEach(pl -> {
            int val = switch (this) {
                case KILLS -> pl.getStatistic(Statistic.PLAYER_KILLS);
                case MONEY -> playerDB.getMoney(pl.getName());
                case COINS -> playerDB.getCoin(pl.getName());
            };
            kills.put(pl.getName(), val);
        });
        Arrays.stream(Bukkit.getOfflinePlayers()).forEach(pl -> {
            int val = switch (this) {
                case KILLS -> pl.getStatistic(Statistic.PLAYER_KILLS);
                case MONEY -> playerDB.getMoney(pl.getName());
                case COINS -> playerDB.getCoin(pl.getName());
            };
            kills.put(pl.getName(), val);
        });

        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(kills.entrySet());
        sortedEntries.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        if (lvl > 0 && lvl <= sortedEntries.size()) {
            return sortedEntries.get(lvl - 1).getKey();
        } else {
            return red+"Пусто";
        }
    }
}
