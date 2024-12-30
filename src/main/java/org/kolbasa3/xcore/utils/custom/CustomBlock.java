package org.kolbasa3.xcore.utils.custom;

import org.bukkit.Location;

import java.util.HashMap;

public class CustomBlock {

    private final HashMap<Location, String> blocks = new HashMap<>();

    public void add(Location center, String key) {
        blocks.put(center, key);
    }

    public void delete(Location center) {
        blocks.remove(center);
    }

    public HashMap<Location, String> list() {
        return blocks;
    }
}
