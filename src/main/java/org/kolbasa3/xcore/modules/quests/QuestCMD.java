package org.kolbasa3.xcore.modules.quests;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kolbasa3.xcore.cmds.AbstractCMD;

import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class QuestCMD extends AbstractCMD {

    public QuestCMD() {
        super("quest");
    }

    QuestTask questTask = new QuestTask();

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player p = (Player) sender;

        int amount = 3;
        if(p.hasPermission("quest.4")) amount = 4;
        else if(p.hasPermission("quest.5")) amount = 5;
        else if(p.hasPermission("quest.6")) amount = 6;
        else if(p.hasPermission("quest.7")) amount = 7;

        if(questTask.getAmount(p.getName()) >= amount) {
            p.sendMessage("");
            p.sendMessage("Вы превысили максимальное количество квестов за день.");
            p.sendMessage("Расширить ограничение §7» "+orange+"/don");
            p.sendMessage("");
            return;
        }

        p.sendMessage("");
        if(questTask.getTaskList(p.getName()) == null) {
            questTask.startQuest(p.getName());
            p.sendMessage(lime + "Квест успешно начат.");
            p.sendMessage("Задание: " + questTask.getFormattedTask(p.getName()));
            p.sendMessage("Приз: " + yellow + questTask.getPrize(p.getName()) + "⛁");
        } else {
            p.sendMessage("Текущий квест:");
            p.sendMessage("Задание: " + questTask.getFormattedTask(p.getName()));
            p.sendMessage("Прогресс: "+azure+questTask.getProgress(p.getName())+"§7/"+questTask.getTaskAmount(p.getName()));
            p.sendMessage("Приз: " + yellow + questTask.getPrize(p.getName()) + "⛁");
        }
        p.sendMessage("");
    }
}
