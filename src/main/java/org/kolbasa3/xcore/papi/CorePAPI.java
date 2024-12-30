package org.kolbasa3.xcore.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.kolbasa3.xcore.modules.cases.CaseType;
import org.kolbasa3.xcore.enums.TopList;

import java.util.Objects;

import static org.kolbasa3.xcore.XCore.playerDB;
import static org.kolbasa3.xcore.utils.PluginUtil.blue;
import static org.kolbasa3.xcore.utils.PluginUtil.red;

public class CorePAPI extends PlaceholderExpansion {

    @Override
    @NotNull
    public String getAuthor() {
        return "Kolbasa3";
    }

    @Override
    @NotNull
    public String getIdentifier() {
        return "XCore";
    }

    @Override
    @NotNull
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if (params.equalsIgnoreCase("donateKey"))
            return playerDB.getKey(player.getName(), CaseType.DONATE)+"";

        else if (params.equalsIgnoreCase("itemsKey"))
            return playerDB.getKey(player.getName(), CaseType.ITEMS)+"";

        else if (params.equalsIgnoreCase("moneyKey"))
            return playerDB.getKey(player.getName(), CaseType.MONEY)+"";

        else if (params.equalsIgnoreCase("coin"))
            return playerDB.getCoin(player.getName())+"";

        else if(params.equalsIgnoreCase("woodbooster")) {
            Player p = Bukkit.getPlayer(Objects.requireNonNull(player.getName()));
            if (p != null) {
                if (p.hasPermission("woodbooster.6")) return blue + "x6 \uD83D\uDD25";
                else if (p.hasPermission("woodbooster.4")) return blue + "x4 \uD83D\uDD25";
                else if (p.hasPermission("woodbooster.2")) return blue + "x2 \uD83D\uDD25";
            }
            return red + "Не активен ×";

        } else if(params.contains("top_")) {
            String[] str = params.split("_");
            return TopList.valueOf(str[1].toUpperCase()).getTopPlayer(Integer.parseInt(str[2]));

        } else if(params.contains("amount_")) {
            String[] str = params.split("_");
            String str2 = TopList.valueOf(str[1].toUpperCase()).getTopPlayer(Integer.parseInt(str[2]));
            if(str[1].equalsIgnoreCase("kills")) return Bukkit.getOfflinePlayer(str2).getStatistic(Statistic.PLAYER_KILLS)+"";
            //else if(str[1].equalsIgnoreCase("money")) return ((int) getEconomy().getBalance(str2))+"";
            else if(str[1].equalsIgnoreCase("coins")) return playerDB.getCoin(str2)+"";
        }
        return null;
    }
}
