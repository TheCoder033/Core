package org.kolbasa3.xcore.modules.kits;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.kolbasa3.xcore.cmds.AbstractCMD;
import org.kolbasa3.xcore.utils.Cooldowns;

import static java.util.Calendar.HOUR;
import static org.kolbasa3.xcore.utils.PluginUtil.addPlayerInv;
import static org.kolbasa3.xcore.utils.PluginUtil.orange;

public class KitCMD extends AbstractCMD {

    public KitCMD() {
        super("kit");
    }

    Kits kits = new Kits();

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;
        if(new Cooldowns().addCd((Player) sender, "kit", HOUR, 6)) return;

        String kit = "start";
        if(p.hasPermission("kit.lite")) kit = "lite";
        if(p.hasPermission("kit.prem")) kit = "prem";
        if(p.hasPermission("kit.gold")) kit = "gold";
        if(p.hasPermission("kit.admin")) kit = "admin";
        if(p.hasPermission("kit.boss")) kit = "boss";
        if(p.hasPermission("kit.ultra")) kit = "ultra";

        for(ItemStack is : kits.getKit(kit)) {
            if(is.getType().toString().contains("HELMET")
                    && p.getInventory().getHelmet() == null) {
                p.getInventory().setHelmet(is);
                continue;

            } else if(is.getType().toString().contains("CHESTPLATE")
                    && p.getInventory().getChestplate() == null) {
                p.getInventory().setChestplate(is);
                continue;

            } else if(is.getType().toString().contains("LEGGINGS")
                    && p.getInventory().getLeggings() == null) {
                p.getInventory().setLeggings(is);
                continue;

            } else if(is.getType().toString().contains("BOOTS")
                    && p.getInventory().getBoots() == null) {
                p.getInventory().setBoots(is);
                continue;
            }
            addPlayerInv(p, is);
        }

        String finalKit = kit;
        p.sendMessage("Выдаём набор "+orange+finalKit);
    }
}
