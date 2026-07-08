package haruka.helpers;

import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import haruka.HarukaMod;
import haruka.core.ColorEnum;

import java.lang.reflect.Method;

public class AssetLoader {

    public static String cardArt(String fileName) {
        return HarukaMod.cardArtPath(fileName);
    }

    public static String relicArt(String fileName) {
        return HarukaMod.relicPath(fileName);
    }

    public static String relicOutlineArt(String fileName) {
        return HarukaMod.relicOutlinePath(fileName);
    }

    private static final String[] CARD_ART_FILES = {
            "card_haruka_addfuel.png",
            "card_haruka_afterheat.png",
            "card_haruka_ashguard.png",
            "card_haruka_bulkdraw03.png",
            "card_haruka_bulkdraw08.png",
            "card_haruka_bulkfinale05.png",
            "card_haruka_bulkfinale30.png",
            "card_haruka_bulkguard02.png",
            "card_haruka_bulkguard07.png",
            "card_haruka_bulkstrike01.png",
            "card_haruka_bulkstrike06.png",
            "card_haruka_bulksweep04.png",
            "card_haruka_cut.png",
            "card_haruka_defend.png",
            "card_haruka_dragondance.png",
            "card_haruka_duetspark.png",
            "card_haruka_earlyburst.png",
            "card_haruka_encoreshout.png",
            "card_haruka_festivalstep.png",
            "card_haruka_finaldance.png",
            "card_haruka_finalemark.png",
            "card_haruka_firetree.png",
            "card_haruka_fireworkprep.png",
            "card_haruka_flameheart.png",
            "card_haruka_gatherflame.png",
            "card_haruka_jab.png",
            "card_haruka_sparkkick.png",
            "card_haruka_sparkwave.png",
            "card_haruka_strike.png",
            "card_haruka_surge.png",
            "card_haruka_ward.png",
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
            System.err.println("[haruka] preload card art failed " + path + ": " + e.getMessage());
        }
    }

    public static void reloadColorTextures() {
        try {
            Method load = CustomCard.class.getDeclaredMethod("loadTextureFromString", String.class);
            load.setAccessible(true);
            Method get = CustomCard.class.getDeclaredMethod("getTextureFromString", String.class);
            get.setAccessible(true);
            AbstractCard.CardColor color = ColorEnum.HARUKA_COLOR;
            BaseMod.saveAttackBgTexture(color, texture(load, get, HarukaMod.CARD_BG_512));
            BaseMod.saveSkillBgTexture(color, texture(load, get, HarukaMod.CARD_BG_SKILL_512));
            BaseMod.savePowerBgTexture(color, texture(load, get, HarukaMod.CARD_BG_POWER_512));
            BaseMod.saveEnergyOrbTexture(color, texture(load, get, HarukaMod.ENERGY_ORB_512));
            BaseMod.saveEnergyOrbPortraitTexture(color, texture(load, get, HarukaMod.ENERGY_ORB_1024));
            BaseMod.saveAttackBgPortraitTexture(color, texture(load, get, HarukaMod.CARD_BG_1024));
            BaseMod.saveSkillBgPortraitTexture(color, texture(load, get, HarukaMod.CARD_BG_SKILL_1024));
            BaseMod.savePowerBgPortraitTexture(color, texture(load, get, HarukaMod.CARD_BG_POWER_1024));
        } catch (Exception e) {
            System.err.println("[haruka] color textures failed: " + e.getMessage());
        }
    }

    private static com.badlogic.gdx.graphics.Texture texture(Method load, Method get, String path) throws Exception {
        load.invoke(null, path);
        return (com.badlogic.gdx.graphics.Texture) get.invoke(null, path);
    }
}
