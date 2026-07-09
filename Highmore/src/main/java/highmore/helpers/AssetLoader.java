package highmore.helpers;

import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import highmore.HighmoreMod;
import highmore.core.ColorEnum;

import java.lang.reflect.Method;

public class AssetLoader {

    public static String cardArt(String fileName) {
        return HighmoreMod.cardArtPath(fileName);
    }

    public static String relicArt(String fileName) {
        return HighmoreMod.relicPath(fileName);
    }

    public static String relicOutlineArt(String fileName) {
        return HighmoreMod.relicOutlinePath(fileName);
    }

    private static final String[] CARD_ART_FILES = {
            "card_highmore_abyssgaze.png",
            "card_highmore_abysspull.png",
            "card_highmore_bloodrush.png",
            "card_highmore_bloodscale.png",
            "card_highmore_brinewall.png",
            "card_highmore_bulkdraw03.png",
            "card_highmore_bulkdraw08.png",
            "card_highmore_bulkfinale05.png",
            "card_highmore_bulkfinale30.png",
            "card_highmore_bulkguard02.png",
            "card_highmore_bulkguard07.png",
            "card_highmore_bulkstrike01.png",
            "card_highmore_bulkstrike06.png",
            "card_highmore_bulksweep04.png",
            "card_highmore_cut.png",
            "card_highmore_deaddrift.png",
            "card_highmore_defend.png",
            "card_highmore_drownembrace.png",
            "card_highmore_greattide.png",
            "card_highmore_jab.png",
            "card_highmore_riptide.png",
            "card_highmore_saltdraw.png",
            "card_highmore_saltharvest.png",
            "card_highmore_scytheswing.png",
            "card_highmore_strike.png",
            "card_highmore_surge.png",
            "card_highmore_tidepool.png",
            "card_highmore_tidereap.png",
            "card_highmore_ward.png",
            "card_highmore_whirlreap.png",
    };

    public static void preloadCardArts() {
        for (String file : CARD_ART_FILES) preloadToImgMap(cardArt(file));
    }

    private static void preloadToImgMap(String path) {
        if (CustomCard.imgMap.containsKey(path)) return;
        try {
            Method load = CustomCard.class.getDeclaredMethod("loadTextureFromString", String.class);
            load.setAccessible(true);
            load.invoke(null, path);
        } catch (Exception e) {
            System.err.println("[highmore] preload card art failed " + path + ": " + e.getMessage());
        }
    }

    public static void reloadColorTextures() {
        try {
            Method load = CustomCard.class.getDeclaredMethod("loadTextureFromString", String.class);
            load.setAccessible(true);
            Method get = CustomCard.class.getDeclaredMethod("getTextureFromString", String.class);
            get.setAccessible(true);
            AbstractCard.CardColor color = ColorEnum.HIGHMORE_COLOR;
            BaseMod.saveAttackBgTexture(color, texture(load, get, HighmoreMod.CARD_BG_512));
            BaseMod.saveSkillBgTexture(color, texture(load, get, HighmoreMod.CARD_BG_SKILL_512));
            BaseMod.savePowerBgTexture(color, texture(load, get, HighmoreMod.CARD_BG_POWER_512));
            BaseMod.saveEnergyOrbTexture(color, texture(load, get, HighmoreMod.ENERGY_ORB_512));
            BaseMod.saveEnergyOrbPortraitTexture(color, texture(load, get, HighmoreMod.ENERGY_ORB_1024));
            BaseMod.saveAttackBgPortraitTexture(color, texture(load, get, HighmoreMod.CARD_BG_1024));
            BaseMod.saveSkillBgPortraitTexture(color, texture(load, get, HighmoreMod.CARD_BG_SKILL_1024));
            BaseMod.savePowerBgPortraitTexture(color, texture(load, get, HighmoreMod.CARD_BG_POWER_1024));
        } catch (Exception e) {
            System.err.println("[highmore] color textures failed: " + e.getMessage());
        }
    }

    private static com.badlogic.gdx.graphics.Texture texture(Method load, Method get, String path) throws Exception {
        load.invoke(null, path);
        return (com.badlogic.gdx.graphics.Texture) get.invoke(null, path);
    }
}
