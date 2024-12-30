package org.kolbasa3.xcore.cmds;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.kolbasa3.xcore.XCore;
import org.kolbasa3.xcore.modules.bosses.BossStarter;
import org.kolbasa3.xcore.utils.ItemUtil;
import org.kolbasa3.xcore.enums.SoundType;
import org.kolbasa3.xcore.enums.Items;

import java.util.List;

import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class CoreCMD extends AbstractCMD {

    public CoreCMD() {
        super("core");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if(!sender.hasPermission("XCore.core")) return;

        if(args.length > 0) {
            if(sender instanceof Player player) {
                if (args[0].equalsIgnoreCase("setwood")) {
                    List<String> list = cfg().getStringList("wood");
                    list.add(locToStr(player.getLocation(), " ", true));
                    cfg().set("wood", list);
                    XCore.getInstance().saveConfig();
                    player.sendMessage(lime + "Позиция дерева успешно сохранена в конфиге.");
                    sound(player, SoundType.SUCCESS);
                    return;

                } else if (args[0].equalsIgnoreCase("rtp")) {
                    cfg().set("rtp", locToStr(player.getLocation(), " ", true));
                    XCore.getInstance().saveConfig();
                    player.sendMessage(lime + "Позиция портала рандомной телепортации успешно сохранена.");
                    sound(player, SoundType.SUCCESS);
                    return;

                } else if(args[0].equalsIgnoreCase("item")) {
                    ItemStack is;
                    if (args[1].equalsIgnoreCase("ruby")) is = customOre(true);
                    else if (args[1].equalsIgnoreCase("ametist")) is = customOre(false);
                    else if (args[1].equalsIgnoreCase("ruby_posoh")) is = posoh(true);
                    else if (args[1].equalsIgnoreCase("ametist_posoh")) is = posoh(false);
                    else {
                        String val = null;
                        if (args.length >= 3) val = args[2];
                        is = Items.valueOf(args[1]).get(val);
                    }
                    player.getInventory().addItem(is);
                    return;

                } else if(args[0].equalsIgnoreCase("head")) {
                    String type = "ds";
                    String data = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg3M2MxMmJmZmI1MjUxYTBiODhkNWFlNzVjNzI0N2NiMzlhNzVmZjFhODFjYmU0YzhhMzliMzExZGRlZGEifX19";
                    if (args[1].equalsIgnoreCase("don")) {
                        type = "don";
                        data = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjNjZWJlMjNkZjExYWE0ZDc1Y2YxNzI2MDA3ZjU4YTkzZTU0ZTg0Y2JlNDVhYzExZmIzZGM5OGFkMzIwOTgifX19";
                    }

                    ArmorStand a = player.getWorld().spawn(player.getLocation().toCenterLocation(), ArmorStand.class, CreatureSpawnEvent.SpawnReason.COMMAND);
                    a.setSilent(true);
                    a.setGravity(false);
                    a.setVisible(false);
                    a.setInvulnerable(true);
                    a.setCanMove(false);
                    a.setMarker(false);
                    NamespacedKey key = new NamespacedKey(XCore.getInstance(), "custom-head");
                    a.getPersistentDataContainer().set(key, PersistentDataType.STRING,type);
                    if(a.getEquipment() != null) a.getEquipment().setHelmet(new ItemUtil(Material.PLAYER_HEAD, "", null).build(data));

                } else if (args[0].equalsIgnoreCase("chram")) {
                    cfg().set("chram", locToStr(player.getLocation(), " ", false));
                    XCore.getInstance().saveConfig();
                    player.sendMessage(lime + "Позиция небесного храма успешно сохранена.");
                    sound(player, SoundType.SUCCESS);
                    return;
                }
            }
            if(args[0].equalsIgnoreCase("max_online") && args.length >= 2) {
                cfg().set("max-online", Integer.parseInt(args[1]));
                XCore.getInstance().saveConfig();
                sender.sendMessage(lime+"Максимальный онлайн: "+Integer.parseInt(args[1]));
                if(sender instanceof Player player) sound(player, SoundType.SUCCESS);

            } else if(args[0].equalsIgnoreCase("bosses")) {
                new BossStarter().check(true);
            }
        }
    }
}
