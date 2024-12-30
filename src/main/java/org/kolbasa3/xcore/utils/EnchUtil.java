package org.kolbasa3.xcore.utils;

import com.google.common.collect.Lists;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static org.kolbasa3.xcore.utils.PluginUtil.hex;

public class EnchUtil {

    public List<String> list(ItemStack is) {
        List<String> list = new ArrayList<>();
        if(is.getItemMeta().getLore() != null) {
            List<String> lore = is.getItemMeta().getLore();

            if(lore != null && lore.contains("§fНовые чары:")) {
                int start = lore.indexOf("§fНовые чары:")+1;

                for (int i = start; i < lore.size(); i++) {
                    String str = lore.get(i);

                    List<String> ench = Lists.newArrayList("бур", "плавильня"
                            , "магнетизм", "опыт", "отравление", "гнев_зевса", "заражение"
                            , "отталкивание", "защитная_аура", "лаваход", "громоотвод");
                    ench.forEach(str2 -> {
                        if (str.replace("§7| ", "")
                                .equals(getFormattedName(str2))) list.add(str2);
                    });
                }
                return list;
            }
        }
        return list;
    }

    public void add(ItemStack is, String key) {
        if(!list(is).contains(key)) {
            List<String> lore = new ArrayList<>();
            if (is.getItemMeta().getLore() != null) lore = is.getItemMeta().getLore();

            if(list(is).isEmpty()) {
                lore.add("");
                lore.add("§fНовые чары:");
            }
            lore.add("§7| " + getFormattedName(key));
            is.setLore(lore);
        }
    }

    public String getFormattedName(String key) {
        return hex(switch(key) {
            case "бур" -> "&#084CFBБ&#2F86FDу&#55C0FFр";
            case "плавильня" -> "&#084CFBП&#125BFCл&#1B69FCа&#2578FDв&#2F86FDи&#3895FEл&#42A3FEь&#4BB2FFн&#55C0FFя";
            case "магнетизм" -> "&#084CFBМ&#125BFCа&#1B69FCг&#2578FDн&#2F86FDе&#3895FEт&#42A3FEи&#4BB2FFз&#55C0FFм";
            case "опыт" -> "&#084CFBО&#2273FCп&#3B99FEы&#55C0FFт";

            case "отравление" -> "&#084CFBО&#1159FBт&#1966FCр&#2273FCа&#2A80FDв&#338CFDл&#3B99FEе&#44A6FEн&#4CB3FFи&#55C0FFе";
            case "гнев_зевса" -> "&#084CFBГ&#125BFCн&#1B69FCе&#2578FDв &#2F86FDЗ&#3895FEе&#42A3FEв&#4BB2FFс&#55C0FFа";
            case "заражение" -> "&#084CFBЗ&#125BFCа&#1B69FCр&#2578FDа&#2F86FDж&#3895FEе&#42A3FEн&#4BB2FFи&#55C0FFе";
            case "отталкивание" -> "&#084CFBО&#0F57FBт&#1661FCт&#1D6CFCа&#2476FCл&#2B81FDк&#328BFDи&#3996FEв&#40A0FEа&#47ABFEн&#4EB5FFи&#55C0FFе";

            case "защитная_аура" -> "&#084CFBЗ&#0F57FBа&#1661FCщ&#1D6CFCи&#2476FCт&#2B81FDн&#328BFDа&#3996FEя &#40A0FEа&#47ABFEу&#4EB5FFр&#55C0FFа";
            case "лаваход" -> "&#084CFBЛ&#155FFCа&#2273FCв&#2F86FDо&#3B99FEх&#48ADFEо&#55C0FFд";
            case "громоотвод" -> "&#084CFBГ&#1159FBр&#1966FCо&#2273FCм&#2A80FDо&#338CFDо&#3B99FEт&#44A6FEв&#4CB3FFо&#55C0FFд";

            default -> null;
        });
    }
}
