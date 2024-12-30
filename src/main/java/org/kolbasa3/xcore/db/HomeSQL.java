package org.kolbasa3.xcore.db;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.kolbasa3.xcore.XCore;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.kolbasa3.xcore.utils.PluginUtil.locToStr;
import static org.kolbasa3.xcore.utils.PluginUtil.strToLoc;

public class HomeSQL {

    private final String url;

    public HomeSQL(File file) throws SQLException {
        url = "jdbc:sqlite:" + file.getAbsolutePath();

        try (Connection cn = connect(); Statement st = cn.createStatement()) {
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS homes (" +
                            "player TEXT NOT NULL, " +
                            "name TEXT NOT NULL, " +
                            "loc TEXT NOT NULL, " +
                            "icon TEXT NOT NULL," +
                            "PRIMARY KEY (player, name))");
        }
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public void setHome(String player, String name, Location loc) {
        Bukkit.getScheduler().runTask(XCore.getInstance(), task -> {
            String str = "INSERT INTO homes (player, name, loc, icon) VALUES (?, ?, ?, ?)";
            try (Connection cn = connect(); PreparedStatement pst = cn.prepareStatement(
                    str)) {
                pst.setString(1, player);
                pst.setString(2, name);
                pst.setString(3, locToStr(loc, " ", false));
                pst.setString(4, "WHITE_BED");
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public HashMap<String, Location> getHomes(String player) {
        HashMap<String, Location> map = new HashMap<>();
        try(Connection cn = connect(); PreparedStatement pst = cn.prepareStatement(
                "SELECT name, loc FROM homes WHERE player = ?")) {
            pst.setString(1, player);
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

    public Material getIcon(String player, String name) {
        try(Connection cn = connect(); PreparedStatement pst = cn.prepareStatement(
                "SELECT icon FROM homes WHERE player = ? AND name = ?")) {
            pst.setString(1, player);
            pst.setString(2, name);
            try(ResultSet rs = pst.executeQuery()) {
                if(rs.next()) return Material.valueOf(rs.getString("icon"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateIcon(String player, String name, Material mat) {
        Bukkit.getScheduler().runTask(XCore.getInstance(), task -> {
            String str = "UPDATE homes SET icon = ? WHERE player = ? AND name = ?";
            try (Connection cn = connect(); PreparedStatement pst = cn.prepareStatement(
                    str)) {
                pst.setString(1, mat.toString());
                pst.setString(2, player);
                pst.setString(3, name);
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void delHome(String player, String name) {
        Bukkit.getScheduler().runTask(XCore.getInstance(), task -> {
            String str = "DELETE FROM homes WHERE player = ? AND name = ?";
            try (Connection cn = connect(); PreparedStatement pst = cn.prepareStatement(
                    str)) {
                pst.setString(1, player);
                pst.setString(2, name);
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public List<String> getWrited() {
        List<String> list = new ArrayList<>();
        try(Connection cn = connect(); PreparedStatement pst = cn.prepareStatement(
                "SELECT player FROM homes")) {
            try(ResultSet rs = pst.executeQuery()) {
                while(rs.next()) {
                    list.add(rs.getString("player"));
                }
                return list;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void close() throws SQLException {
        connect().close();
    }
}
