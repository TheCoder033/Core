package org.kolbasa3.xcore.cmds;

import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.kolbasa3.xcore.XCore;
import org.kolbasa3.xcore.utils.Cooldowns;

import java.util.HashMap;

import static java.util.Calendar.SECOND;
import static org.kolbasa3.xcore.utils.PluginUtil.red;

public class SitCMD extends AbstractCMD {

    public SitCMD() {
        super("sit");
    }

    public static HashMap<String, ArmorStand> sit = new HashMap<>();

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;
        if(new Cooldowns().addCd(p, "sit", SECOND, 1)) return;

        if(!p.isOnGround()) {
            p.sendMessage(red+"Вы не можете сидеть на воздухе.");
            return;
        }

        ArmorStand a = (ArmorStand) p.getWorld().spawnEntity(p.getLocation().clone().add(0, -1, 0), EntityType.ARMOR_STAND);
        a.setSilent(false);
        a.setGravity(false);
        a.setVisible(false);
        a.setSmall(true);
        a.setMarker(false);
        a.setCanMove(false);
        a.setInvulnerable(true);

        NamespacedKey key = new NamespacedKey(XCore.getInstance(), "sit");
        a.getPersistentDataContainer().set(key, PersistentDataType.STRING, "sit");

        a.addPassenger(p);
        sit.put(p.getName(), a);
    }
}
