package org.kolbasa3.xcore.db;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.kolbasa3.xcore.XCore;

import java.io.File;
import java.sql.*;
import java.util.HashMap;

import static org.kolbasa3.xcore.utils.PluginUtil.locToStr;
import static org.kolbasa3.xcore.utils.PluginUtil.strToLoc;

public class WarpSQL {

    private final String url;

    public WarpSQL(File file) throws SQLException {
        url = "jdbc:sqlite:" + file.getAbsolutePath();

        try (Connection cn = connect(); Statement st = cn.createStatement()) {
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS warps (" +
                            "name varchar(16) PRIMARY KEY, " +
                            "player varchar(16), " +
                            "loc varchar(50), " +
                            "rating varchar(4), " +
                            "icon varchar(16))");
        }
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public void setWarp(String player, String name, Location loc) {
        Bukkit.getScheduler().runTask(XCore.getInstance(), task -> {
            String str = "INSERT INTO warps (name, player, loc, rating, icon) VALUES (?, ?, ?, ?, ?)";
            try (Connection cn = connect(); PreparedStatement pst = cn.prepareStatement(
                    str)) {
                pst.setString(1, name);
                pst.setString(2, player);
                pst.setString(3, locToStr(loc, " ", false));
                pst.setInt(4, 100);
                pst.setString(5, "WHITE_BED");
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public HashMap<String, Location> getWarps() {
        HashMap<String, Location> map = new HashMap<>();
        try(Connection cn = connect(); PreparedStatement pst = cn.prepareStatement(
                "SELECT name, loc FROM warps")) {
            try(ResultSet rs = pst.executeQuery()) {
                while(rs.next()) {
                    map.put(rs.getString("name")
                            , strToLoc(rs.getString("loc"), "\\s", false));
                }
                return map;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public Material getIcon(String name) {
        try(Connection cn = connect(); PreparedStatement pst = cn.prepareStatement(
                "SELECT icon FROM warps WHERE name = ?")) {
            pst.setString(1, name);
            try(ResultSet rs = pst.executeQuery()) {
                if(rs.next()) return Material.valueOf(rs.getString("icon"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getOwner(String name) {
        try(Connection cn = connect(); PreparedStatement pst = cn.prepareStatement(
                "SELECT player FROM warps WHERE name = ?")) {
            pst.setString(1, name);
            try(ResultSet rs = pst.executeQuery()) {
                if(rs.next()) return rs.getString("player");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getRating(String name) {
        try(Connection cn = connect(); PreparedStatement pst = cn.prepareStatement(
                "SELECT rating FROM warps WHERE name = ?")) {
            pst.setString(1, name);
            try(ResultSet rs = pst.executeQuery()) {
                if(rs.next()) return rs.getInt("rating");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void updateIcon(String name, Material mat) {
        Bukkit.getScheduler().runTask(XCore.getInstance(), task -> {
            String str = "UPDATE warps SET icon = ? WHERE name = ?";
            try (Connection cn = connect(); PreparedStatement pst = cn.prepareStatement(
                    str)) {
                pst.setString(1, mat.toString());
                pst.setString(2, name);
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void updateRating(String name, int rating) {
        Bukkit.getScheduler().runTask(XCore.getInstance(), task -> {
            String str = "UPDATE warps SET rating = ? WHERE name = ?";
            try (Connection cn = connect(); PreparedStatement pst = cn.prepareStatement(
                    str)) {
                pst.setInt(1, rating);
                pst.setString(2, name);
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void delWarp(String name) {
        Bukkit.getScheduler().runTask(XCore.getInstance(), task -> {
            String str = "DELETE FROM warps WHERE name = ?";
            try (Connection cn = connect(); PreparedStatement pst = cn.prepareStatement(
                    str)) {
                pst.setString(1, name);
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void close() throws SQLException {
        connect().close();
    }
}
