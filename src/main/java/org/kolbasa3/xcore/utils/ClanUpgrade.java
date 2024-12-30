package org.kolbasa3.xcore.utils;

import java.util.List;

import static org.kolbasa3.xcore.XCore.clanDB;

public class ClanUpgrade {

    public int getLvl(String name, String key) {
        for (String str : clanDB.getList(name, false)) {
            if(str != null && str.contains(key))
                return Integer.parseInt(str.replace(key, ""));
        }
        int defaultLvl = 15000;
        if(key.equals("players")) defaultLvl = 1;
        return defaultLvl;
    }

    public void upgrade(String name, String key, int toLvl, int addedLvl) {
        List<String> upgrades = clanDB.getList(name, false);
        upgrades.remove(key+(toLvl-addedLvl));
        String to = "";
        if(toLvl != 0) to = toLvl+"";
        upgrades.add(key+to);
        clanDB.updateList(name, false, upgrades);
    }

    public int getPrice(String key, int lvl) {
        return switch (key) {
            case "players" -> lvl*2000;
            case "bal" -> lvl/70;
            default -> 0;
        };
    }
}
