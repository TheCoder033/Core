package org.kolbasa3.xcore.modules.smith;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.kolbasa3.xcore.XCore;

import java.util.Objects;

import static org.kolbasa3.xcore.utils.PluginUtil.posoh;

public class SmithCraft {

    private final ItemStack slot1, slot2, slot3,
    slot4, slot5, slot6,
    slot7, slot8, slot9;

    public SmithCraft(Inventory inv) {
        slot1 = inv.getItem(2);
        slot2 = inv.getItem(3);
        slot3 = inv.getItem(4);
        slot4 = inv.getItem(11);
        slot5 = inv.getItem(12);
        slot6 = inv.getItem(13);
        slot7 = inv.getItem(20);
        slot8 = inv.getItem(21);
        slot9 = inv.getItem(22);
    }

    public ItemStack get() {

        if (isPosoh(true)) return posoh(true);
        else if (isPosoh(false)) return posoh(false);

        return null;
    }

    private boolean isPosoh(boolean ruby) {
        NamespacedKey key = new NamespacedKey(XCore.getInstance(), "custom-item");
        String type = "ametist";
        if(ruby) type = "ruby";
        return slot2 != null && slot4 != null && slot6 != null && slot5 != null && slot8 != null
                && slot2.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)
        && Objects.equals(slot2.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING), type)
                && slot4.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)
                && Objects.equals(slot4.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING), type)
                && slot6.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)
                && Objects.equals(slot6.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING), type)
                && slot2.getAmount() == 3
                && slot4.getAmount() == 3
                && slot6.getAmount() == 3
                && slot5.getType().equals(Material.STICK)
                && slot8.getType().equals(Material.STICK);
    }
}
