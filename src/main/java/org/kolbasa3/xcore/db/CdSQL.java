package org.kolbasa3.xcore.db;

import org.bukkit.Bukkit;
import org.kolbasa3.xcore.XCore;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CdSQL {

    private final String url;

    public CdSQL(File file) throws SQLException {
        url = "jdbc:sqlite:" + file.getAbsolutePath();

        try (Connection cn = connect(); Statement st = cn.createStatement()) {
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS cd (" +
                            "player TEXT NOT NULL, " +
                            "type TEXT NOT NULL, " +
                            "time TEXT NOT NULL," +
                            "PRIMARY KEY (player, type))");
        }
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public void addCd(String player, String type) {
        Bukkit.getScheduler().runTask(XCore.getInstance(), task -> {
            String str = "INSERT INTO cd (player, type, time) VALUES (?, ?, ?)";
            try (Connection cn = connect(); PreparedStatement pst = cn.prepareStatement(
                    str)) {
                pst.setString(1, player);
                pst.setString(2, type);
                pst.setLong(3, System.currentTimeMillis());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public HashMap<String, Long> getCd(String player) {
        HashMap<String, Long> map = new HashMap<>();
        try(Connection cn = connect(); PreparedStatement pst = cn.prepareStatement(
                "SELECT type, time FROM cd WHERE player = ?")) {
            pst.setString(1, player);
            try(ResultSet rs = pst.executeQuery()) {
                while(rs.next()) {
                    map.put(rs.getString("type")
                            , rs.getLong("time"));
                }
                return map;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public void delCd(String player, String type) {
        Bukkit.getScheduler().runTask(XCore.getInstance(), task -> {
            String str = "DELETE FROM cd WHERE player = ? AND type = ?";
            try (Connection cn = connect(); PreparedStatement pst = cn.prepareStatement(
                    str)) {
                pst.setString(1, player);
                pst.setString(2, type);
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public List<String> getWrited() {
        List<String> list = new ArrayList<>();
        try(Connection cn = connect(); PreparedStatement pst = cn.prepareStatement(
                "SELECT player FROM cd")) {
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
