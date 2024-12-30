package org.kolbasa3.xcore.db;

import org.kolbasa3.xcore.modules.cases.CaseType;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerSQL {

    private final String url;

    public PlayerSQL(File file) throws SQLException {
        url = "jdbc:sqlite:" + file.getAbsolutePath();

        try (Connection cn = connect(); Statement st = cn.createStatement()) {
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS players (" +
                            "player TEXT NOT NULL, " +
                            "coin INTEGER NOT NULL, " +
                            "donatekey INTEGER NOT NULL, " +
                            "itemskey INTEGER NOT NULL, " +
                            "moneykey INTEGER NOT NULL, " +
                            "invested INTEGER NOT NULL, " +
                            "models TEXT, " +
                            "joinmsg TEXT, " +
                            "money INTEGER NOT NULL, " +
                            "PRIMARY KEY (player));");
        }
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public void setKey(String player, CaseType caseType, int amount) {
        try {
            PreparedStatement pst = connect().prepareStatement(
                    "INSERT OR REPLACE INTO players (player, coin, donatekey, itemskey, moneykey, invested, models, joinmsg, money) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
            pst.setString(1, player);
            pst.setInt(2, getCoin(player));
            switch (caseType) {
                case ITEMS -> {
                    pst.setInt(3, getKey(player, CaseType.DONATE));
                    pst.setInt(4, amount);
                    pst.setInt(5, getKey(player, CaseType.MONEY));
                }
                case MONEY -> {
                    pst.setInt(3, getKey(player, CaseType.DONATE));
                    pst.setInt(4, getKey(player, CaseType.ITEMS));
                    pst.setInt(5, amount);
                }
                default -> {
                    pst.setInt(3, amount);
                    pst.setInt(4, getKey(player, CaseType.ITEMS));
                    pst.setInt(5, getKey(player, CaseType.MONEY));
                }
            }
            pst.setInt(6, getInvested(player));
            pst.setString(7, getModelsStr(player));
            pst.setString(8, getJoinMsg(player));
            pst.setInt(9, getMoney(player));
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer getKey(String player, CaseType caseType) {
        try(Connection c = connect(); PreparedStatement pst = c.prepareStatement(
                "SELECT player, "+caseType.toString().toLowerCase()+"key FROM players WHERE player = ?;")) {
            pst.setString(1, player);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next())
                return resultSet.getInt(caseType.toString().toLowerCase()+"key");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setCoin(String player, int amount) {
        try {
            PreparedStatement pst = connect().prepareStatement(
                    "INSERT OR REPLACE INTO players (player, coin, donatekey, itemskey, moneykey, invested, models, joinmsg, money) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
            pst.setString(1, player);
            pst.setInt(2, amount);
            pst.setInt(3, getKey(player, CaseType.DONATE));
            pst.setInt(4, getKey(player, CaseType.ITEMS));
            pst.setInt(5, getKey(player, CaseType.MONEY));
            pst.setInt(6, getInvested(player));
            pst.setString(7, getModelsStr(player));
            pst.setString(8, getJoinMsg(player));
            pst.setInt(9, getMoney(player));
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer getCoin(String player) {
        try(Connection c = connect(); PreparedStatement pst = c.prepareStatement(
                "SELECT player, coin FROM players WHERE player = ?;")) {
            pst.setString(1, player);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next())
                return resultSet.getInt("coin");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setInvested(String player, int amount) {
        try {
            PreparedStatement pst = connect().prepareStatement(
                    "INSERT OR REPLACE INTO players (player, coin, donatekey, itemskey, moneykey, invested, models, joinmsg, money) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
            pst.setString(1, player);
            pst.setInt(2, getCoin(player));
            pst.setInt(3, getKey(player, CaseType.DONATE));
            pst.setInt(4, getKey(player, CaseType.ITEMS));
            pst.setInt(5, getKey(player, CaseType.MONEY));
            pst.setInt(6, amount);
            pst.setString(7, getModelsStr(player));
            pst.setString(8, getJoinMsg(player));
            pst.setInt(9, getMoney(player));
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer getInvested(String player) {
        try(Connection c = connect(); PreparedStatement pst = c.prepareStatement(
                "SELECT player, invested FROM players WHERE player = ?;")) {
            pst.setString(1, player);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next())
                return resultSet.getInt("invested");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<String> getModels(String player) {
        try(Connection c = connect(); PreparedStatement pst = c.prepareStatement(
                "SELECT player, models FROM players WHERE player = ?;")) {
            pst.setString(1, player);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next())
                return Arrays.stream(resultSet.getString("models").split("\\s")).toList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public String getModelsStr(String player) {
        StringBuilder sb = new StringBuilder();
        getModels(player).forEach(model -> {
            if(!sb.isEmpty()) sb.append(" ");
            sb.append(model);
        });
        return sb.toString();
    }

    public void setModels(String player, List<String> list) {
        StringBuilder sb = new StringBuilder();
        list.forEach(model -> {
            if(!sb.isEmpty()) sb.append(" ");
            sb.append(model);
        });
        try {
            PreparedStatement pst = connect().prepareStatement("INSERT OR REPLACE INTO players (player, coin, donatekey, itemskey, moneykey, invested, models, joinmsg, money) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
            pst.setString(1, player);
            pst.setInt(2, getCoin(player));
            pst.setInt(3, getKey(player, CaseType.DONATE));
            pst.setInt(4, getKey(player, CaseType.ITEMS));
            pst.setInt(5, getKey(player, CaseType.MONEY));
            pst.setInt(6, getInvested(player));
            pst.setString(7, sb.toString());
            pst.setString(8, getJoinMsg(player));
            pst.setInt(9, getMoney(player));
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setJoinMsg(String player, String str) {
        try {
            PreparedStatement pst = connect().prepareStatement("INSERT OR REPLACE INTO players (player, coin, donatekey, itemskey, moneykey, invested, models, joinmsg, money) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
            pst.setString(1, player);
            pst.setInt(2, getCoin(player));
            pst.setInt(3, getKey(player, CaseType.DONATE));
            pst.setInt(4, getKey(player, CaseType.ITEMS));
            pst.setInt(5, getKey(player, CaseType.MONEY));
            pst.setInt(6, getInvested(player));
            pst.setString(7, getModelsStr(player));
            pst.setString(8, str);
            pst.setInt(9, getMoney(player));
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getJoinMsg(String player) {
        try(Connection c = connect(); PreparedStatement pst = c.prepareStatement(
                "SELECT player, joinmsg FROM players WHERE player = ?;")) {
            pst.setString(1, player);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next())
                return resultSet.getString("joinmsg");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setMoney(String player, int amount) {
        try {
            PreparedStatement pst = connect().prepareStatement(
                    "INSERT OR REPLACE INTO players (player, coin, donatekey, itemskey, moneykey, invested, models, joinmsg, money) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
            pst.setString(1, player);
            pst.setInt(2, getCoin(player));
            pst.setInt(3, getKey(player, CaseType.DONATE));
            pst.setInt(4, getKey(player, CaseType.ITEMS));
            pst.setInt(5, getKey(player, CaseType.MONEY));
            pst.setInt(6, getInvested(player));
            pst.setString(7, getModelsStr(player));
            pst.setString(8, getJoinMsg(player));
            pst.setInt(9, amount);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer getMoney(String player) {
        try(Connection c = connect(); PreparedStatement pst = c.prepareStatement(
                "SELECT player, money FROM players WHERE player = ?;")) {
            pst.setString(1, player);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next())
                return resultSet.getInt("money");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void close() throws SQLException {
        connect().close();
    }
}
