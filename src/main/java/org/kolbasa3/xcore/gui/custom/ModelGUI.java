package org.kolbasa3.xcore.gui.custom;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.kolbasa3.xcore.utils.ItemUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.kolbasa3.xcore.XCore.playerDB;
import static org.kolbasa3.xcore.utils.PluginUtil.*;
import static org.kolbasa3.xcore.utils.PluginUtil.hex;

public class ModelGUI {

    public ModelGUI(Player p) {
        Inventory inv = Bukkit.createInventory(null,
                54, Component.text("            §0⁘ Модельки ⁘"));

        ItemStack is = new ItemUtil(Material.LIME_STAINED_GLASS_PANE, "§f", null).build(null);
        inv.setItem(0, is);
        inv.setItem(1, is);
        inv.setItem(9, is);

        inv.setItem(7, is);
        inv.setItem(8, is);
        inv.setItem(17, is);

        inv.setItem(36, is);
        inv.setItem(45, is);
        inv.setItem(46, is);

        inv.setItem(44, is);
        inv.setItem(52, is);
        inv.setItem(53, is);

        inv.setItem(10, getModelItem(p.getName(), azure+"Утка", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzg4MmViNDlkODlmMWViOGM2ODgxNGU3MWM2MjYzYmJlYzdiYTYzM2YyN2JmZjZlZmRiOTJhNjc5OWJhIn19fQ==", false));
        inv.setItem(11, getModelItem(p.getName(), azure+"Земля", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmUyY2M0MjAxNWU2Njc4ZjhmZDQ5Y2NjMDFmYmY3ODdmMWJhMmMzMmJjZjU1OWEwMTUzMzJmYzVkYjUwIn19fQ==", false));
        inv.setItem(12, getModelItem(p.getName(), azure+"Футбольный мяч", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGU0YTcwYjdiYmNkN2E4YzMyMmQ1MjI1MjA0OTFhMjdlYTZiODNkNjBlY2Y5NjFkMmI0ZWZiYmY5ZjYwNWQifX19", false));
        inv.setItem(13, getModelItem(p.getName(), azure+"Баскетбольный мяч", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWRmODQ3MTVhNjRkYzQ1NTg2ZjdhNjA3OWY4ZTQ5YTk0NzdjMGZlOTY1ODliNGNmZDcxY2JhMzIyNTRhYzgifX19", false));
        inv.setItem(14, getModelItem(p.getName(), azure+"Часы", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODQ3N2RhZmM4YzllYTA3OTk2MzMzODE3OTM4NjZkMTQ2YzlhMzlmYWQ0YzY2ODRlNzExN2Q5N2U5YjZjMyJ9fX0=", false));
        inv.setItem(15, getModelItem(p.getName(), azure+"Кубок", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTM0YTU5MmE3OTM5N2E4ZGYzOTk3YzQzMDkxNjk0ZmMyZmI3NmM4ODNhNzZjY2U4OWYwMjI3ZTVjOWYxZGZlIn19fQ==", false));
        inv.setItem(16, getModelItem(p.getName(), azure+"Динамит", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjg4ZmI2YjJiM2VmYTZhYjI5OWY5ZjRlN2Q4YzFhMWQ3MzM4NzUzYmRmOGZlZjgxMDc1ZjIxNTU5NDNiYzY5In19fQ==", false));

        if(p.hasPermission("tags.snegovik")) inv.setItem(19, getModelItem(p.getName(), azure+"Снеговик", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTI3Njk1YmVlMmJhYmY5YmFlZjExYWUxOWZlNWY4MTdmYTA4OGNkNTJjNGM0NzNjYzdiNDQxZDVjM2Y1MGZlIn19fQ==", true));
        if(p.hasPermission("tags.aquarium")) inv.setItem(20, getModelItem(p.getName(), azure+"Аквариум", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmRkOGE5MjU4MDkyZjI2ZjExNzM1N2UxYjUyYmFjOGY0Y2VkYzU3NWI5ODJmNTJhODVkZjNhMzEzNjk2ZDg4ZiJ9fX0=", true));
        if(p.hasPermission("tags.creeper")) inv.setItem(21, getModelItem(p.getName(), azure+"Золотой крипер", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjNjZWJlMjNkZjExYWE0ZDc1Y2YxNzI2MDA3ZjU4YTkzZTU0ZTg0Y2JlNDVhYzExZmIzZGM5OGFkMzIwOTgifX19", true));

        inv.setItem(49, new ItemUtil(Material.PLAYER_HEAD, azure+"Убрать модельку", null).build("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmM5MmUzZjQ1YjQ5ZTQwNTY3MDIyNDg5MmY5M2ViYzg0ZmE3ZjhjOTZjMzZhYWIyNGE4ODU0ZjJjYmYwYjgifX19"));

        p.openInventory(inv);
    }

    private ItemStack getModelItem(String player, String key, String data, boolean donate) {
        List<String> lore = new ArrayList<>();

        boolean buyed = donate || playerDB.getModels(player).contains(key
                .replace(azure, "")
                .replace(" ", "_"));
        boolean setted = models.containsKey(player)
                && Objects.equals(models.get(player).getCustomName(), key
                .replace(azure, "")
                .replace(" ", "_"));

        String st = red+"Не куплено";
        if(buyed) st = red+"Не установлено";

        if(setted) st = lime+"Установлено";

        lore.add("");
        lore.add(hex("§fСтатус: "+st));
        if(setted) lore.add("§7Нажмите чтобы убрать.");
        else if(buyed) lore.add("§7Нажмите чтобы установить.");

        if(!buyed) {
            lore.add(hex("§fЦена: "+orange+"50К"));
            lore.add(hex("§fБаланс: "+yellow+playerDB.getCoin(player)+"К"));
            lore.add("§7Нажмите для покупки.");
        }
        lore.add("");
        return new ItemUtil(Material.PLAYER_HEAD, key, lore).build(data);
    }
}
