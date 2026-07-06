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
            "card_attack.png", "card_skill.png", "card_power.png",
            "card_strike.png", "card_defend.png",
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
