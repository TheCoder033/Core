package org.kolbasa3.xcore.modules.events;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.cmds.AbstractCMD;
import org.kolbasa3.xcore.enums.SoundType;

import java.util.Calendar;

import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class EventCMD extends AbstractCMD {

    public EventCMD() {
        super("event");
    }

    Events events = new Events();

    @Override
    public void execute(CommandSender sender, String label, String[] args) {

        if(args.length == 0 || (!args[0].equalsIgnoreCase("spawn")
                && !args[0].equalsIgnoreCase("tp"))
                || !(sender instanceof Player)) {
            Player player = (Player) sender;

            if(events.getEvent() == EventType.NULL) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(events.getLastEvent());
                calendar.add(Calendar.MINUTE, 30);
                long timeDiffInMillis = calendar.getTimeInMillis();

                if (events.getLastEvent() != 0)
                    timeDiffInMillis = timeDiffInMillis-System.currentTimeMillis();

                long timeDiffInSeconds = timeDiffInMillis / 1000;
                long remainingSeconds = timeDiffInSeconds % 3600;

                long minutes = remainingSeconds / 60;
                long seconds = remainingSeconds % 60;
                if (seconds <= 0) seconds = 0;

                player.sendMessage("До следующего ивента §7» "+orange+minutes+"мин. "+seconds+"сек.");

            } else {
                String str = switch(events.getEvent()) {
                    case NULL -> null;
                    case AIRDROP -> "Аирдроп";
                    case WATERCHEST -> "Подводное сокровище";
                    case ISLAND -> "Небесный сундук";
                    case PARKOUR -> "Небесный храм";
                };
                Location eventLoc = events.getLoc();
                if(events.getEvent().equals(EventType.PARKOUR)) {
                    player.sendMessage("");
                    player.sendMessage("Текущий ивент: "+blue+str);
                    player.sendMessage("Телепортация на ивент §7» "+orange+"/evt tp");
                    player.sendMessage("");
                } else {
                    player.sendMessage("");
                    player.sendMessage("Текущий ивент: "+blue+str);
                    player.sendMessage("Навести компас §7» "+orange+"/gps "+eventLoc.getBlockX()+" "+eventLoc.getBlockZ());
                    player.sendMessage("");
                }
            }

        } else if(args[0].equalsIgnoreCase("spawn")
        && sender.hasPermission("XCore.event.spawn")) {
            events.delEvent();
            EventType eventType = EventType.AIRDROP;
            if(args[1].equalsIgnoreCase("p")) eventType = EventType.PARKOUR;
            else if(args[1].equalsIgnoreCase("w")) {
                eventType = EventType.WATERCHEST;
            } else if(args[1].equalsIgnoreCase("i")) {
                eventType = EventType.ISLAND;
            } else if(args[1].equalsIgnoreCase("pk")) {
                eventType = EventType.PARKOUR;
            }
            events.spawnDrop(eventType);

        } else if(args[0].equalsIgnoreCase("tp")
                && events.getEvent().equals(EventType.PARKOUR)) {
            Player p = (Player) sender;
            if(!Events.eventPlayers.contains(p)) Events.eventPlayers.add(p);
            p.teleport(events.getLoc());
            sound(p, SoundType.SUCCESS);
        }
    }
}
