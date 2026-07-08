package nymph.helpers;

import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import nymph.NymphMod;
import nymph.core.ColorEnum;

import java.lang.reflect.Method;

public class AssetLoader {

    public static String cardArt(String fileName) {
        return NymphMod.cardArtPath(fileName);
    }

    public static String relicArt(String fileName) {
        return NymphMod.relicPath(fileName);
    }

    public static String relicOutlineArt(String fileName) {
        return NymphMod.relicOutlinePath(fileName);
    }

    private static final String[] CARD_ART_FILES = {
            "card_nymph_bulkdraw03.png",
            "card_nymph_bulkdraw08.png",
            "card_nymph_bulkfinale05.png",
            "card_nymph_bulkfinale30.png",
            "card_nymph_bulkguard02.png",
            "card_nymph_bulkguard07.png",
            "card_nymph_bulkstrike01.png",
            "card_nymph_bulkstrike06.png",
            "card_nymph_bulksweep04.png",
            "card_nymph_cursewave.png",
            "card_nymph_cut.png",
            "card_nymph_defend.png",
            "card_nymph_dreadguard.png",
            "card_nymph_fearfeast.png",
            "card_nymph_fearwhisper.png",
            "card_nymph_heartbite.png",
            "card_nymph_heartseal.png",
            "card_nymph_hexbolt.png",
            "card_nymph_hexlink.png",
            "card_nymph_hexspread.png",
            "card_nymph_jab.png",
            "card_nymph_locktighten.png",
            "card_nymph_nightecho.png",
            "card_nymph_nightmare.png",
            "card_nymph_shadowreap.png",
            "card_nymph_soulbind.png",
            "card_nymph_spiritclaw.png",
            "card_nymph_strike.png",
            "card_nymph_surge.png",
            "card_nymph_voidcut.png",
            "card_nymph_ward.png",
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
            System.err.println("[nymph] preload card art failed " + path + ": " + e.getMessage());
        }
    }

    public static void reloadColorTextures() {
        try {
            Method load = CustomCard.class.getDeclaredMethod("loadTextureFromString", String.class);
            load.setAccessible(true);
            Method get = CustomCard.class.getDeclaredMethod("getTextureFromString", String.class);
            get.setAccessible(true);
            AbstractCard.CardColor color = ColorEnum.NYMPH_COLOR;
            BaseMod.saveAttackBgTexture(color, texture(load, get, NymphMod.CARD_BG_512));
            BaseMod.saveSkillBgTexture(color, texture(load, get, NymphMod.CARD_BG_SKILL_512));
            BaseMod.savePowerBgTexture(color, texture(load, get, NymphMod.CARD_BG_POWER_512));
            BaseMod.saveEnergyOrbTexture(color, texture(load, get, NymphMod.ENERGY_ORB_512));
            BaseMod.saveEnergyOrbPortraitTexture(color, texture(load, get, NymphMod.ENERGY_ORB_1024));
            BaseMod.saveAttackBgPortraitTexture(color, texture(load, get, NymphMod.CARD_BG_1024));
            BaseMod.saveSkillBgPortraitTexture(color, texture(load, get, NymphMod.CARD_BG_SKILL_1024));
            BaseMod.savePowerBgPortraitTexture(color, texture(load, get, NymphMod.CARD_BG_POWER_1024));
        } catch (Exception e) {
            System.err.println("[nymph] color textures failed: " + e.getMessage());
        }
    }

    private static com.badlogic.gdx.graphics.Texture texture(Method load, Method get, String path) throws Exception {
        load.invoke(null, path);
        return (com.badlogic.gdx.graphics.Texture) get.invoke(null, path);
    }
}
