package org.kolbasa3.xcore.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.kolbasa3.xcore.utils.ItemUtil;

import java.util.ArrayList;
import java.util.List;

import static org.kolbasa3.xcore.utils.PluginUtil.*;

public class DonateGUI {

    public DonateGUI(Player p) {
        Inventory inv = Bukkit.createInventory(null
                , 36, Component.text("           §0⁘ Привилегии ⁘"));

        ItemStack is = new ItemUtil(Material.LIME_STAINED_GLASS_PANE, "§f", null).build(null);
        for(int i = 0; i < 4; i++) {
            inv.setItem((i * 9), is);
        }
        inv.setItem(8, is);
        inv.setItem(17, is);
        inv.setItem(26, is);
        inv.setItem(35, is);

        List<String> lore = new ArrayList<>();

        lore.add("");
        lore.add(hex("&#5988FF§l/kit §7- набор &#5988FFʟ&#59A2FFɪ&#59BBFFᴛ&#59D5FFᴇ"));
        lore.add(hex("&#5988FF§l/hat §7- Одеть блок на голову"));
        lore.add(hex("&#5988FF§l/salary §7- Зарплата (&#5988FF3к§7)"));
        lore.add("");
        lore.add(hex("&#5988FF2 §fточки дома"));
        lore.add(hex("&#5988FF2 §fблока региона"));
        lore.add(hex("&#5988FF2 §fслота на аукционе"));
        lore.add("");
        lore.add(hex("&#5988FF§lskyworld.net"));
        lore.add("");

        is = new ItemUtil(Material.LEATHER_CHESTPLATE, hex("&#5988FFʟ&#59A2FFɪ&#59BBFFᴛ&#59D5FFᴇ §7| ")+orange+"10 руб.", lore).build(null);
        inv.setItem(11, is);

        lore.clear();
        lore.add("");
        lore.add(hex("&#00AF7B§l/kit §7- набор &#00AF7Bᴘ&#1ECA85ʀ&#3BE48Fᴇ&#59FF99ᴍ"));
        lore.add(hex("&#00AF7B§l/msgtoggle §7- вкл/выкл лс"));
        lore.add(hex("&#00AF7B§l/paytoggle §7- вкл/выкл переводы"));
        lore.add(hex("&#00AF7B§l/ext §7- потушить себя"));
        lore.add(hex("&#00AF7B§l/salary §7- Зарплата (&#00AF7B5к§7)"));
        lore.add("");
        lore.add(hex("&#00AF7B§l3 §fточки дома"));
        lore.add(hex("&#00AF7B§l3 §fблока региона"));
        lore.add(hex("&#00AF7B§l3 §fслота на аукционе"));
        lore.add(hex("&#00AF7B§l+ &#00AF7BВозможности предыдущих"));
        lore.add(hex("&#00AF7Bпривилегий."));
        lore.add("");
        lore.add(hex("&#00AF7B§lskyworld.net"));
        lore.add("");

        is = new ItemUtil(Material.CHAINMAIL_CHESTPLATE, hex("&#00AF7Bᴘ&#1ECA85ʀ&#3BE48Fᴇ&#59FF99ᴍ §7| ") + orange + "30 руб.", lore).build(null);
        inv.setItem(12, is);

        lore.clear();
        lore.add("");
        lore.add(hex("&#FFE85B§l/kit §7- набор &#FFE85Bɢ&#FFD65Cᴏ&#FFC55Cʟ&#FFB35Dᴅ"));
        lore.add(hex("&#FFE85B§l/ignore §7(ник) - игнорировать игрока"));
        lore.add(hex("&#FFE85B§l/stonecutter §7- открыть камнерез"));
        lore.add(hex("&#FFE85B§l/loom §7- открыть ткацкий станок"));
        lore.add(hex("&#FFE85B§l/craft §7- открыть верстак"));
        lore.add(hex("&#FFE85B§l/smithtable §7- открыть коптильню"));
        lore.add(hex("&#FFE85B§l/salary §7- Зарплата (&#FFE85B8к§7)"));
        lore.add("");
        lore.add(hex("&#FFE85B§l4 §fточки дома"));
        lore.add(hex("&#FFE85B§l4 §fблока региона"));
        lore.add(hex("&#FFE85B§l4 §fслота на аукционе"));
        lore.add(hex("&#FFE85B§l+ &#FFE85BВозможности предыдущих"));
        lore.add(hex("&#FFE85Bпривилегий."));
        lore.add("");
        lore.add(hex("&#FFE85B§lskyworld.net"));
        lore.add("");

        is = new ItemUtil(Material.GOLDEN_CHESTPLATE, hex("&#FFE85Bɢ&#FFD65Cᴏ&#FFC55Cʟ&#FFB35Dᴅ §7| ")+orange+"50 руб.", lore).build(null);
        inv.setItem(13, is);

        lore.clear();
        lore.add("");
        lore.add(hex("&#EE3600§l/kit §7- набор &#EE3600ᴀ&#F24B0Cᴅ&#F75F18ᴍ&#FB7423ɪ&#FF882Fɴ"));
        lore.add(hex("&#EE3600§l/heal §7- вылечить себя"));
        lore.add(hex("&#EE3600§l/feed §7- утолить голод"));
        lore.add(hex("&#EE3600§l/tptoggle §7- вкл/выкл тп"));
        lore.add(hex("&#EE3600§l/bc §7- объявление"));
        lore.add(hex("&#EE3600§l/seen §7(ник) - информация об игроке"));
        lore.add(hex("&#EE3600§l/salary §7- Зарплата (&#EE360013к§7)"));
        lore.add("");
        lore.add(hex("&#EE3600§l5 §fточки дома"));
        lore.add(hex("&#EE3600§l5 §fблока региона"));
        lore.add(hex("&#EE3600§l5 §fслота на аукционе"));
        lore.add(hex("&#EE3600§l+ &#EE3600Возможности предыдущих"));
        lore.add(hex("&#EE3600привилегий."));
        lore.add("");
        lore.add(hex("&#EE3600§lskyworld.net"));
        lore.add("");

        is = new ItemUtil(Material.IRON_CHESTPLATE, hex("&#EE3600ᴀ&#F24B0Cᴅ&#F75F18ᴍ&#FB7423ɪ&#FF882Fɴ §7| ")+orange+"70 руб.", lore).build(null);
        inv.setItem(14, is);

        lore.clear();
        lore.add("");
        lore.add(hex("&#EE6C00§l/kit §7- набор &#EE6C00ʙ&#F4821Aᴏ&#F99933ꜱ&#FFAF4Dꜱ"));
        lore.add(hex("&#EE6C00§l/fly §7- режим полёта"));
        lore.add(hex("&#EE6C00§l/tpahere §7(ник) - запрос на тп к себе"));
        lore.add(hex("&#EE6C00§l/ec §7- открыть эндер-сундук"));
        lore.add(hex("&#EE6C00§l/bal §7(ник) - узнать баланс игрока"));
        lore.add(hex("&#EE6C00§l/near §7- игроки рядом §7(радиус: &#EE6C0050 §7блоков)"));
        lore.add(hex("&#EE6C00§l/salary §7- Зарплата (&#EE6C0016к§7)"));
        lore.add("");
        lore.add(hex("&#EE6C00§l6 §fточки дома"));
        lore.add(hex("&#EE6C00§l6 §fблока региона"));
        lore.add(hex("&#EE6C00§l6 §fслота на аукционе"));
        lore.add(hex("&#EE6C00§l+ &#EE6C00Возможности предыдущих"));
        lore.add(hex("&#EE6C00привилегий."));
        lore.add("");
        lore.add(hex("&#EE6C00§lskyworld.net"));
        lore.add("");

        is = new ItemUtil(Material.DIAMOND_CHESTPLATE, hex("&#EE6C00ʙ&#F4821Aᴏ&#F99933ꜱ&#FFAF4Dꜱ §7| ")+orange+"100 руб.", lore).build(null);
        inv.setItem(15, is);

        lore.clear();
        lore.add("");
        lore.add(hex("&#4FFF1E§l/kit §7- набор &#4FFF1Eᴜ&#6DFF2Bʟ&#8BFF39ᴛ&#A8FF46ʀ&#C6FF53ᴀ"));
        lore.add(hex("&#4FFF1E§l/inv §7(ник) - посмотреть инвентарь игрока"));
        lore.add(hex("&#4FFF1E§l/repair §7- починить предмет"));
        lore.add(hex("&#4FFF1E§l/near §7- игроки рядом §7(радиус: &#4FFF1E100 §7блоков)"));
        lore.add(hex("&#4FFF1E§l/gstone §7- открыть точило"));
        lore.add(hex("&#4FFF1E§l/salary §7- Зарплата (&#4FFF1E19к§7)"));
        lore.add("");
        lore.add(hex("&#4FFF1E§l7 §fточки дома"));
        lore.add(hex("&#4FFF1E§l7 §fблока региона"));
        lore.add(hex("&#4FFF1E§l7 §fслота на аукционе"));
        lore.add(hex("&#4FFF1E§l+ &#4FFF1EВозможности предыдущих"));
        lore.add(hex("&#4FFF1Eпривилегий."));
        lore.add("");
        lore.add(hex("&#4FFF1E§lskyworld.net"));
        lore.add("");

        is = new ItemUtil(Material.NETHERITE_CHESTPLATE, hex("&#4FFF1Eᴜ&#6DFF2Bʟ&#8BFF39ᴛ&#A8FF46ʀ&#C6FF53ᴀ §7| ")+orange+"170 руб.", lore).build(null);
        inv.setItem(22, is);

        p.openInventory(inv);
    }
}
