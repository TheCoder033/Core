package org.kolbasa3.xcore.cmds;

import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.gui.region.RegionFlagsGUI;
import org.kolbasa3.xcore.gui.region.RegionUpgradesGUI;
import org.kolbasa3.xcore.enums.Items;
import org.kolbasa3.xcore.utils.region.RegionUpgrade;
import org.kolbasa3.xcore.enums.SoundType;

import java.util.List;

import static org.kolbasa3.xcore.XCore.playerDB;
import static org.kolbasa3.xcore.XCore.regionDB;
import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class RegionCMD extends AbstractCMD {

    public RegionCMD() {
        super("region");
    }

    RegionUpgrade regionUpgrade = new RegionUpgrade();

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;

        if(args.length == 0
        || (args.length < 2 && (args[0].equalsIgnoreCase("new")
        || args[0].equalsIgnoreCase("del")
        || args[0].equalsIgnoreCase("upgrade")
                || args[0].equalsIgnoreCase("flags")
        || args[0].equalsIgnoreCase("pl")))
        || (args.length < 3 && (args[0].equalsIgnoreCase("add")
        || args[0].equalsIgnoreCase("kick")))
        || (!args[0].equalsIgnoreCase("new") && !args[0].equalsIgnoreCase("del")
        && !args[0].equalsIgnoreCase("add") && !args[0].equalsIgnoreCase("kick")
        && !args[0].equalsIgnoreCase("upgrade") && !args[0].equalsIgnoreCase("flags")
        && !args[0].equalsIgnoreCase("list") && !args[0].equalsIgnoreCase("pl"))) {
            player.sendMessage("");
            player.sendMessage("Регионы:");
            player.sendMessage(orange + "/rg new §7(название) §f» Создать регион. §7("+yellow+"15000⛁§7)");
            player.sendMessage(orange + "/rg del §7(название) §f» Удалить регион.");
            player.sendMessage(orange + "/rg add §7(название) (ник) §f» Добавить игрока в регион.");
            player.sendMessage(orange + "/rg kick §7(название) (ник) §f» Кикнуть игрока с региона.");
            player.sendMessage(orange + "/rg upgrade §7(название) §f» Улучшения региона.");
            player.sendMessage(orange + "/rg flags §7(название) §f» Флаги региона.");
            player.sendMessage(orange + "/rg pl §7(название) §f» Список участников региона.");
            player.sendMessage(orange + "/rg list §f» Список регионов.");
            player.sendMessage("");
            sound(player, SoundType.WRONG);
            return;
        }

        if(args[0].equalsIgnoreCase("new")) {
            if (args[1].length() < 4 && !player.hasPermission("rg.length.ignore")) {
                player.sendMessage(red + "Минимальная длина названия региона §7» " + orange + "4 " + red + "символа.");
                sound(player, SoundType.WRONG);
                return;
            }
            if (args[1].length() > 16) {
                player.sendMessage(red + "Максимальная длина названия региона §7» " + orange + "16 " + red + "символов.");
                sound(player, SoundType.WRONG);
                return;
            }

            if(playerDB.getMoney(player.getName()) < 15000) {
                player.sendMessage("");
                player.sendMessage(red + "Недостаточно средств. §7(Баланс: " + yellow + playerDB.getMoney(player.getName()) + "⛁§7)");
                player.sendMessage("Вам нужно " + yellow + "15000⛁");
                player.sendMessage("");
                sound(player, SoundType.WRONG);
                return;
            }
            playerDB.setMoney(player.getName(), (playerDB.getMoney(player.getName())-15000));

            addPlayerInv(player, Items.RG_BLOCK.get(args[1]));
            player.sendMessage(orange+"Поставьте блок региона в желаемом месте.");
            sound(player, SoundType.SELECT);

        } else if(args[0].equalsIgnoreCase("del")) {
            if(!regionDB.getRegions(player.getName()).contains(args[1])) {
                sendRegionNull(player, args[1]);
                return;
            }
            regionDB.getLoc(player.getName(), args[1]).getBlock().setType(Material.AIR);
            regionDB.delRegion(player.getName(), args[1]);
            player.sendMessage("Регион " + orange + args[1] + " §fуспешно удалён.");
            sound(player, SoundType.SUCCESS);

        } else if(args[0].equalsIgnoreCase("add")) {
            if(player.getName().equals(args[2])) return;
            if(!regionDB.getRegions(player.getName()).contains(args[1])) {
                sendRegionNull(player, args[1]);
                return;
            }
            List<String> list = regionDB.getList(player.getName(), args[1], true);

            if(list.size() >= regionUpgrade.getLvl(player.getName(), args[1], "players")) {
                player.sendMessage("");
                player.sendMessage(red+"Вы превысили максимальное количество участников.");
                player.sendMessage("Расширить ограничение §7» "+azure+"/rg upgrade "+args[1]);
                player.sendMessage("");
                sound(player, SoundType.WRONG);
                return;
            }

            if(list.contains(args[2])) {
                player.sendMessage(red+"Игрок "+azure+args[2]+red+" уже в вашем регионе "+orange+args[1]);
                sound(player, SoundType.WRONG);
                return;
            }
            list.add(args[2]);
            regionDB.updateList(player.getName(), args[1], true, list);
            player.sendMessage("Игрок " + azure + args[2] + " §fуспешно добавлен в регион "+orange+args[1]);
            sound(player, SoundType.SUCCESS);

        } else if(args[0].equalsIgnoreCase("kick")) {
            if(!regionDB.getRegions(player.getName()).contains(args[1])) {
                sendRegionNull(player, args[1]);
                return;
            }
            List<String> list = regionDB.getList(player.getName(), args[1], true);
            if (!list.contains(args[2])) {
                player.sendMessage(red + "Игрок " + azure + args[2] + red + " не в вашем регионе " + orange + args[1]);
                sound(player, SoundType.WRONG);
                return;
            }
            list.remove(args[2]);
            regionDB.updateList(player.getName(), args[1], true, list);
            player.sendMessage("Игрок " + azure + args[2] + " §fуспешно кикнут с региона " + orange + args[1]);
            sound(player, SoundType.SUCCESS);

        } else if(args[0].equalsIgnoreCase("upgrade")) {
            if(!regionDB.getRegions(player.getName()).contains(args[1])) {
                sendRegionNull(player, args[1]);
                return;
            }
            new RegionUpgradesGUI(player, args[1]);

        } else if(args[0].equalsIgnoreCase("flags")) {
            if (!regionDB.getRegions(player.getName()).contains(args[1])) {
                sendRegionNull(player, args[1]);
                return;
            }
            new RegionFlagsGUI(player, args[1]);

        } else if(args[0].equalsIgnoreCase("pl")) {
            if (!regionDB.getRegions(player.getName()).contains(args[1])) {
                sendRegionNull(player, args[1]);
                return;
            }
            StringBuilder sb = new StringBuilder();
            regionDB.getList(player.getName(), args[1], true).forEach(pl -> {
                if(!sb.isEmpty()) sb.append("§f, ");
                sb.append(azure).append(pl);
            });
            player.sendMessage("Список участников региона "+orange+args[1]+" §7» "+ sb);
            sound(player, SoundType.SELECT);

        } else if(args[0].equalsIgnoreCase("list")) {
            if(regionDB.getRegions(player.getName()).isEmpty()) {
                player.sendMessage("");
                player.sendMessage(red+"У вас нет регионов.");
                player.sendMessage("Создать регион §7» "+orange+"/rg new §7(название)");
                player.sendMessage("");
                sound(player, SoundType.WRONG);
                return;
            }
            player.sendMessage(getRegionList(player.getName()));
            sound(player, SoundType.SELECT);
        }
    }

    private TextComponent getRegionList(String player) {
        TextComponent text = new TextComponent("Список регионов §7» ");
        regionDB.getRegions(player).forEach(rgName -> {
           if(text.getText().length() > 20) text.addExtra("§f, ");
           TextComponent name = new TextComponent(hexTextComponent(azure+rgName));
            Location loc = regionDB.getLoc(player, rgName);
           name.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hexBaseComponent("Позиция: "+orange+loc.getBlockX()
                   +"x "+loc.getBlockY()+"y "+ loc.getBlockZ()+"z")));
           text.addExtra(name);
        });
        return text;
    }

    private void sendRegionNull(Player player, String rgName) {
        if(!regionDB.getRegions(player.getName()).isEmpty()) {
            TextComponent text = new TextComponent();
            text.addExtra("\n");
            text.addExtra(hexTextComponent(red + "Регион " + orange + rgName + red + " не найден."));
            text.addExtra("\n");
            text.addExtra(getRegionList(player.getName()));
            text.addExtra("\n");
            player.sendMessage(text);
        } else player.sendMessage(red + "Регион " + orange + rgName + red + " не найден.");
        sound(player, SoundType.WRONG);
    }
}
