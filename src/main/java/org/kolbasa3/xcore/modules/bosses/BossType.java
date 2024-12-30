package org.kolbasa3.xcore.modules.bosses;

import static org.kolbasa3.xcore.utils.PluginUtil.hex;

public enum BossType {

    GUARDIAN,
    HAMSTER,
    FLAME,
    SHULKER,
    SLIME,

    NETHER_ZOMBIE,
    NETHER_MAGE,

    END_SPIDER;

    public String getCustomName() {
        return hex(switch (this) {
            case GUARDIAN -> "&#FB6E08С&#FC8225т&#FD9641р&#FEAA5Eа&#FFBE7Aж";
            case HAMSTER -> "&#0873FBХ&#2580FCо&#418CFDм&#5E99FEя&#7AA5FFк";
            case FLAME -> "&#FF8735О&#FF853Fг&#FF8349н&#FF8153е&#FF805Cн&#FF7E66н&#FF7C70ы&#FF7A7Aй";
            case SHULKER -> "&#8C47FFВ&#8949FFл&#864BFFа&#824DFFс&#7F4FFFт&#7C51FFе&#7953FFл&#7555FFи&#7257FFн &#6C5CFFш&#685EFFа&#6560FFл&#6262FFк&#5F64FFе&#5B66FFр&#5868FFо&#556AFFв";
            case SLIME -> "&#47FFE1Б&#48FFD7о&#49FFCDл&#4AFFC3ь&#4BFFB9ш&#4CFFAFо&#4DFFA5й &#4FFF91с&#50FF87л&#51FF7Dи&#52FF73з&#53FF69е&#54FF5Fн&#55FF55ь";

            case NETHER_ZOMBIE -> "&#FFCC35О&#FFC537г&#FFBE3Aн&#FFB73Cе&#FFB03Eн&#FFA941н&#FFA243ы&#FF9C45й &#FF8E4Aз&#FF874Cо&#FF804Eм&#FF7951б&#FF7253и";
            case NETHER_MAGE -> "&#6000C9М&#5A31E4а&#5362FFг";

            case END_SPIDER -> "&#0895FBС&#1291FBк&#1B8CFCо&#2588FCр&#2F84FCо&#3880FCс&#427BFDт&#4C77FDн&#5573FDо&#5F6EFEй &#7266FEп&#7C62FEа&#855DFFу&#8F59FFк";
        });
    }
}
