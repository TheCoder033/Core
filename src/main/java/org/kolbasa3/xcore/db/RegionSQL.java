package org.kolbasa3.xcore.db;

import org.bukkit.Location;
import org.kolbasa3.xcore.utils.region.RegionTask;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.kolbasa3.xcore.utils.PluginUtil.locToStr;
import static org.kolbasa3.xcore.utils.PluginUtil.strToLoc;

public class RegionSQL {

    private final String url;

    public RegionSQL(File file) throws SQLException {
        url = "jdbc:sqlite:" + file.getAbsolutePath();

        try (Connection cn = connect(); Statement st = cn.createStatement()) {
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS regions (" +
                            "owner TEXT NOT NULL, " +
                            "name TEXT NOT NULL, " +
                            "loc TEXT, " +
                            "players TEXT, " +
                            "upgrades TEXT, " +
                            "PRIMARY KEY (owner, name));");
        }
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public void createRegion(String owner, String name, Location loc) {
        try {
            PreparedStatement pst = connect().prepareStatement("INSERT INTO regions (owner, name, loc, players, upgrades) VALUES (?, ?, ?, ?, ?);");
            pst.setString(1, owner);
            pst.setString(2, name);
            pst.setString(3, locToStr(loc, " ", true));
            pst.setString(4, null);
            pst.setString(5, null);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delRegion(String owner, String name) {
        new RegionTask().del(getLoc(owner, name));
        try {
            PreparedStatement pst = connect().prepareStatement("DELETE FROM regions WHERE owner = ? AND name = ?");
            pst.setString(1, owner);
            pst.setString(2, name);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getRegions(String owner) {
        List<String> list = new ArrayList<>();
        try(Connection c = connect(); PreparedStatement pst = c.prepareStatement(
                "SELECT owner, name FROM regions;")) {
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                if(owner == null || resultSet.getString("owner").equals(owner)) list.add(resultSet.getString("name"));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateList(String owner, String name, boolean players, List<String> list) {
        StringBuilder sb = new StringBuilder();
        list.forEach(str -> {
           if(!sb.isEmpty()) sb.append(" ");
           sb.append(str);
        });

        String type = "upgrades";
        if(players) type = "players";
        try {
            PreparedStatement pst = connect().prepareStatement("UPDATE regions SET "+type+" = ? WHERE owner = ? AND name = ?");
            pst.setString(1, sb.toString());
            pst.setString(2, owner);
            pst.setString(3, name);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getList(String owner, String name, boolean players) {
        List<String> list = new ArrayList<>();
        String str = "upgrades";
        if(players) str = "players";
        try(Connection c = connect(); PreparedStatement pst = c.prepareStatement(
                "SELECT "+str+" FROM regions WHERE owner = ? AND name = ?;")) {
            pst.setString(1, owner);
            pst.setString(2, name);
            ResultSet resultSet = pst.executeQuery();
            if(resultSet.next() && resultSet.getString(str) != null)
                list.addAll(Arrays.stream(resultSet.getString(str).split("\\s")).toList());
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Location getLoc(String owner, String name) {
        try(Connection c = connect(); PreparedStatement pst = c.prepareStatement(
                "SELECT loc FROM regions WHERE owner = ? AND name = ?;")) {
            pst.setString(1, owner);
            pst.setString(2, name);
            ResultSet resultSet = pst.executeQuery();
            if(resultSet.next()) return strToLoc(resultSet.getString("loc"), "\\s", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getOwner(String name) {
        try(Connection c = connect(); PreparedStatement pst = c.prepareStatement(
                "SELECT name, owner FROM regions;")) {
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                if(resultSet.getString("name").equals(name))
                    return resultSet.getString("owner");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close() throws SQLException {
        connect().close();
    }
}
