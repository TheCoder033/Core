package org.kolbasa3.xcore.cmds.homes;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.cmds.AbstractCMD;
import org.kolbasa3.xcore.utils.region.RegionUpgrade;

import static org.kolbasa3.xcore.XCore.homesDB;
import static org.kolbasa3.xcore.XCore.regionDB;
import static org.kolbasa3.xcore.utils.PluginUtil.orange;
import static org.kolbasa3.xcore.utils.PluginUtil.red;

public class SetHomeCMD extends AbstractCMD {

    public SetHomeCMD() {
        super("sethome");
    }

    private final RegionUpgrade regionUpgrade = new RegionUpgrade();

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;

        int size = 2;
        if(p.hasPermission("sethome.3")) size = 3;
        else if(p.hasPermission("sethome.4")) size = 4;
        else if(p.hasPermission("sethome.5")) size = 5;
        else if(p.hasPermission("sethome.6")) size = 6;
        else if(p.hasPermission("sethome.7")) size = 7;

        if(homesDB.getHomes(p.getName()).size() >= size) {
            p.sendMessage("");
            p.sendMessage(red+"Вы превысили максимальное количество точек домов.");
            p.sendMessage("Расширить ограничение §7» "+orange+"/don");
            p.sendMessage("");
            return;
        }

        for (String nearbyRg : regionDB.getRegions(null)) {
            String owner = regionDB.getOwner(nearbyRg);
            if (regionDB.getLoc(owner, nearbyRg).getWorld().getName().equals(p.getWorld().getName())
                    && regionDB.getLoc(owner, nearbyRg)
                    .distance(p.getLocation()) <= regionUpgrade.getLvl(owner, nearbyRg, "size")) {
                if (!owner.equals(p.getName()) && !regionDB.getList(owner, nearbyRg, true)
                        .contains(p.getName())) {
                    p.sendMessage(red + "Рядом есть чужой регион.");
                    return;
                }
            }
        }

        String home = "home";
        if(args.length > 0) home = args[0];

        if(homesDB.getHomes(p.getName()).containsKey(home)) {
            p.sendMessage(red+"Точка дома "+orange+home+red+" уже существует.");
            return;
        }

        homesDB.setHome(p.getName(), home, p.getLocation());
        p.sendMessage("Точка дома "+orange+home+" §fуспешно создана.");
    }
}
