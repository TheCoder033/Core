package org.kolbasa3.xcore.utils.region;

import java.util.List;

import static org.kolbasa3.xcore.XCore.regionDB;

public class RegionUpgrade {

    public int getLvl(String owner, String name, String key) {
        for (String str : regionDB.getList(owner, name, false)) {
            if(str != null && str.contains(key)) {
                return Integer.parseInt(str.replace(key, ""));
            }
        }
        int defaultLvl = 2;
        if(key.equals("players")) defaultLvl = 1;
        return defaultLvl;
    }

    public void upgrade(String owner, String name, String key, int toLvl, int addedLvl) {
        List<String> upgrades = regionDB.getList(owner, name, false);
        upgrades.remove(key+(toLvl-addedLvl));
        String to = "";
        if(toLvl != 0) to = toLvl+"";
        upgrades.add(key+to);
        regionDB.updateList(owner, name, false, upgrades);
    }

    public int getPrice(String key, int lvl) {
        return switch (key) {
            case "size" -> lvl*3000;
            case "players" -> lvl*2000;
            case "radar" -> 85000;

            case "pvp" -> 10000;
            case "build", "destroy" -> 5000;
            default -> 0;
        };
    }
}
