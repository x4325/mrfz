package scene.helpers;

import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import scene.SceneMod;
import scene.core.ColorEnum;

import java.lang.reflect.Method;

public class AssetLoader {

    public static String cardArt(String fileName) {
        return SceneMod.cardArtPath(fileName);
    }

    public static String relicArt(String fileName) {
        return SceneMod.relicPath(fileName);
    }

    public static String relicOutlineArt(String fileName) {
        return SceneMod.relicOutlinePath(fileName);
    }

    private static final String[] CARD_ART_FILES = {
            "card_scene_bulkdraw03.png",
            "card_scene_bulkdraw08.png",
            "card_scene_bulkfinale05.png",
            "card_scene_bulkfinale30.png",
            "card_scene_bulkguard02.png",
            "card_scene_bulkguard07.png",
            "card_scene_bulkstrike01.png",
            "card_scene_bulkstrike06.png",
            "card_scene_bulksweep04.png",
            "card_scene_burstshutter.png",
            "card_scene_cut.png",
            "card_scene_darkroomwash.png",
            "card_scene_defend.png",
            "card_scene_develop.png",
            "card_scene_developrush.png",
            "card_scene_focusshot.png",
            "card_scene_freezeframe.png",
            "card_scene_jab.png",
            "card_scene_lensdraw.png",
            "card_scene_longexposure.png",
            "card_scene_masterlens.png",
            "card_scene_negative.png",
            "card_scene_overexposure.png",
            "card_scene_panscan.png",
            "card_scene_shutterguard.png",
            "card_scene_snapshot.png",
            "card_scene_strike.png",
            "card_scene_surge.png",
            "card_scene_timershutter.png",
            "card_scene_ward.png",
            "card_scene_wideframe.png",
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
            System.err.println("[scene] preload card art failed " + path + ": " + e.getMessage());
        }
    }

    public static void reloadColorTextures() {
        try {
            Method load = CustomCard.class.getDeclaredMethod("loadTextureFromString", String.class);
            load.setAccessible(true);
            Method get = CustomCard.class.getDeclaredMethod("getTextureFromString", String.class);
            get.setAccessible(true);
            AbstractCard.CardColor color = ColorEnum.SCENE_COLOR;
            BaseMod.saveAttackBgTexture(color, texture(load, get, SceneMod.CARD_BG_512));
            BaseMod.saveSkillBgTexture(color, texture(load, get, SceneMod.CARD_BG_SKILL_512));
            BaseMod.savePowerBgTexture(color, texture(load, get, SceneMod.CARD_BG_POWER_512));
            BaseMod.saveEnergyOrbTexture(color, texture(load, get, SceneMod.ENERGY_ORB_512));
            BaseMod.saveEnergyOrbPortraitTexture(color, texture(load, get, SceneMod.ENERGY_ORB_1024));
            BaseMod.saveAttackBgPortraitTexture(color, texture(load, get, SceneMod.CARD_BG_1024));
            BaseMod.saveSkillBgPortraitTexture(color, texture(load, get, SceneMod.CARD_BG_SKILL_1024));
            BaseMod.savePowerBgPortraitTexture(color, texture(load, get, SceneMod.CARD_BG_POWER_1024));
        } catch (Exception e) {
            System.err.println("[scene] color textures failed: " + e.getMessage());
        }
    }

    private static com.badlogic.gdx.graphics.Texture texture(Method load, Method get, String path) throws Exception {
        load.invoke(null, path);
        return (com.badlogic.gdx.graphics.Texture) get.invoke(null, path);
    }
}
