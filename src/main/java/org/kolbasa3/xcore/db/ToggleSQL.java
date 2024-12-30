package org.kolbasa3.xcore.db;

import org.bukkit.Bukkit;
import org.kolbasa3.xcore.XCore;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ToggleSQL {

    private final String url;

    public ToggleSQL(File file) throws SQLException {
        url = "jdbc:sqlite:" + file.getAbsolutePath();

        try (Connection cn = connect(); Statement st = cn.createStatement()) {
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS toggle (" +
                            "player TEXT NOT NULL PRIMARY KEY, " +
                            "tp TEXT NOT NULL, " +
                            "msg TEXT NOT NULL, " +
                            "pay TEXT NOT NULL)");
        }
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public void setToggle(String player, String key, boolean state) {
        Bukkit.getScheduler().runTask(XCore.getInstance(), task -> {
            String str = "INSERT INTO toggle (player, tp, msg, pay) VALUES (?, ?, ?, ?)";
            boolean contains = getWrited().contains(player);
            if (contains) str = "UPDATE toggle SET " + key + " = ? WHERE player = ?";
            try (Connection cn = connect(); PreparedStatement pst = cn.prepareStatement(
                    str)) {
                if (contains) {
                    pst.setBoolean(1, state);
                    pst.setString(2, player);
                } else {
                    pst.setString(1, player);
                    pst.setBoolean(2, false);
                    pst.setBoolean(3, false);
                    pst.setBoolean(4, false);
                }
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public boolean getToggle(String player, String key) {
        try(Connection cn = connect(); PreparedStatement pst = cn.prepareStatement(
                "SELECT "+key+" FROM toggle WHERE player = ?")) {
            pst.setString(1, player);
            try(ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(key);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getWrited() {
        List<String> list = new ArrayList<>();
        try(Connection cn = connect(); PreparedStatement pst = cn.prepareStatement(
                "SELECT player FROM toggle")) {
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
