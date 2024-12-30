package org.kolbasa3.xcore.db;

import org.bukkit.Location;
import org.bukkit.Material;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.kolbasa3.xcore.utils.PluginUtil.locToStr;
import static org.kolbasa3.xcore.utils.PluginUtil.strToLoc;

public class ClanSQL {

    private final String url;

    public ClanSQL(File file) throws SQLException {
        url = "jdbc:sqlite:" + file.getAbsolutePath();

        try (Connection cn = connect(); Statement st = cn.createStatement()) {
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS clans (" +
                            "name TEXT NOT NULL, " +
                            "owner TEXT NOT NULL, " +
                            "loc TEXT, " +
                            "players TEXT, " +
                            "upgrades TEXT, " +
                            "icon TEXT, " +
                            "bal INTEGER, " +
                            "PRIMARY KEY (name));");
        }
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public void createClan(String name, String owner) {
        try {
            PreparedStatement pst = connect().prepareStatement("INSERT INTO clans (name, owner, loc, players, upgrades, icon, bal) VALUES (?, ?, ?, ?, ?, ?, ?);");
            pst.setString(1, name);
            pst.setString(2, owner);
            pst.setString(3, null);
            pst.setString(4, null);
            pst.setString(5, null);
            pst.setString(6, null);
            pst.setInt(7, 0);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delClan(String name) {
        try {
            PreparedStatement pst = connect().prepareStatement(
                    "DELETE FROM clans WHERE name = ?");
            pst.setString(1, name);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getClans(String owner) {
        List<String> list = new ArrayList<>();
        try(Connection c = connect(); PreparedStatement pst = c.prepareStatement(
                "SELECT name, owner FROM clans;")) {
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                if(owner == null || resultSet.getString("owner").equals(owner)
                || getList(resultSet.getString("name"), true).contains(owner)) {
                    list.add(resultSet.getString("name"));
                    if(owner != null) return list;
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateLoc(String name, Location loc) {
        try {
            PreparedStatement pst = connect().prepareStatement("UPDATE clans SET loc = ? WHERE name = ?");
            pst.setString(1, locToStr(loc, " ", false));
            pst.setString(2, name);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateList(String name, boolean players, List<String> list) {
        StringBuilder sb = new StringBuilder();
        list.forEach(str -> {
           if(!sb.isEmpty()) sb.append(" ");
           sb.append(str);
        });

        String type = "upgrades";
        if(players) type = "players";
        try {
            PreparedStatement pst = connect().prepareStatement("UPDATE clans SET "+type+" = ? WHERE name = ?");
            pst.setString(1, sb.toString());
            pst.setString(2, name);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateIcon(String name, Material mat) {
        try {
            PreparedStatement pst = connect().prepareStatement("UPDATE clans SET icon = ? WHERE name = ?");
            pst.setString(1, mat.toString());
            pst.setString(2, name);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateBal(String name, Integer val) {
        try {
            PreparedStatement pst = connect().prepareStatement("UPDATE clans SET bal = ? WHERE name = ?");
            pst.setInt(1, val);
            pst.setString(2, name);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getList(String name, boolean players) {
        List<String> list = new ArrayList<>();
        String str = "upgrades";
        if(players) str = "players";
        try(Connection c = connect(); PreparedStatement pst = c.prepareStatement(
                "SELECT "+str+" FROM clans WHERE name = ?;")) {
            pst.setString(1, name);
            ResultSet resultSet = pst.executeQuery();
            if(resultSet.next() && resultSet.getString(str) != null) {
                if(players) {
                    Arrays.stream(resultSet.getString(str).split("\\s")).toList().forEach(plStr -> {
                        if(plStr.contains(":")) list.add(plStr.split(":")[0]);
                        else list.add(plStr);
                    });
                } else list.addAll(Arrays.stream(resultSet.getString(str).split("\\s")).toList());
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<String> getPerms(String name, String player) {
        try(Connection c = connect(); PreparedStatement pst = c.prepareStatement(
                "SELECT players FROM clans WHERE name = ?;")) {
            pst.setString(1, name);
            ResultSet resultSet = pst.executeQuery();
            if(resultSet.next() && resultSet.getString("players") != null) {
                for (String plStr : Arrays.stream(resultSet.getString("players").split("\\s")).toList()) {
                    if (plStr.contains(player) && plStr.contains(":")
                    && plStr.split(":").length > 1) return Arrays.stream(plStr
                                    .split(":")[1]
                            .split(",")).toList();
                }
            }
            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Location getLoc(String name) {
        try(Connection c = connect(); PreparedStatement pst = c.prepareStatement(
                "SELECT loc FROM clans WHERE name = ?;")) {
            pst.setString(1, name);
            ResultSet resultSet = pst.executeQuery();
            if(resultSet.next()) return strToLoc(resultSet.getString("loc"), "\\s", false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Material getIcon(String name) {
        try(Connection c = connect(); PreparedStatement pst = c.prepareStatement(
                "SELECT icon FROM clans WHERE name = ?;")) {
            pst.setString(1, name);
            ResultSet resultSet = pst.executeQuery();
            if(resultSet.next()) return Material.valueOf(resultSet.getString("icon"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Material.MAP;
    }

    public String getOwner(String name) {
        try(Connection c = connect(); PreparedStatement pst = c.prepareStatement(
                "SELECT name, owner FROM clans;")) {
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

    public Integer getBal(String name) {
        try(Connection c = connect(); PreparedStatement pst = c.prepareStatement(
                "SELECT bal FROM clans WHERE name = ?;")) {
            pst.setString(1, name);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next())
                return resultSet.getInt("bal");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close() throws SQLException {
        connect().close();
    }
}
