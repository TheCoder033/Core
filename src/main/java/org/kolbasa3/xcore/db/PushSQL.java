package org.kolbasa3.xcore.db;

import org.bukkit.Bukkit;
import org.kolbasa3.xcore.XCore;
import org.kolbasa3.xcore.enums.PushReason;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PushSQL {

    private final String url;

    public PushSQL(File file) throws SQLException {
        url = "jdbc:sqlite:" + file.getAbsolutePath();

        try (Connection cn = connect(); Statement st = cn.createStatement()) {
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS push (" +
                            "player TEXT NOT NULL, " +
                            "ban TEXT NOT NULL, " +
                            "time TEXT NOT NULL," +
                            "PRIMARY KEY (player))");
        }
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public void ban(String player, PushReason reason) {
        Bukkit.getScheduler().runTask(XCore.getInstance(), task -> {
            String str = "INSERT INTO push (player, ban, time) VALUES (?, ?, ?)";
            if (getWrited().contains(player)) str = "UPDATE push SET ban = ? AND time = ? WHERE player = ?";

            Calendar c = Calendar.getInstance();
            if (reason.equals(PushReason.DUPE)) c.add(Calendar.DAY_OF_MONTH, 30);
            else if(reason.equals(PushReason.HACK3)) c.add(Calendar.HOUR, 3);
            else c.add(Calendar.DAY_OF_MONTH, 10);

            try (Connection cn = connect(); PreparedStatement pst = cn.prepareStatement(
                    str)) {
                if (getWrited().contains(player)) {
                    pst.setString(1, reason.toString());
                    pst.setLong(2, c.getTimeInMillis());
                    pst.setString(3, player);
                } else {
                    pst.setString(1, player);
                    pst.setString(2, reason.toString());
                    pst.setLong(3, c.getTimeInMillis());
                }
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public String getPush(String player) {
        try(Connection cn = connect(); PreparedStatement pst = cn.prepareStatement(
                "SELECT ban FROM push WHERE player = ?")) {
            pst.setString(1, player);
            try(ResultSet rs = pst.executeQuery()) {
                if(rs.next()) {
                    return rs.getString("ban");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long getTime(String player) {
        try(Connection cn = connect(); PreparedStatement pst = cn.prepareStatement(
                "SELECT time FROM push WHERE player = ?")) {
            pst.setString(1, player);
            try(ResultSet rs = pst.executeQuery()) {
                if(rs.next()) {
                    return rs.getLong("time");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void delPush(String player) {
        Bukkit.getScheduler().runTask(XCore.getInstance(), task -> {
            String str = "DELETE FROM push WHERE player = ?";
            try (Connection cn = connect(); PreparedStatement pst = cn.prepareStatement(
                    str)) {
                pst.setString(1, player);
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public List<String> getWrited() {
        List<String> list = new ArrayList<>();
        try(Connection cn = connect(); PreparedStatement pst = cn.prepareStatement(
                "SELECT player FROM push")) {
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
