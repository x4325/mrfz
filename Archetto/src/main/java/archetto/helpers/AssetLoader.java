package archetto.helpers;

import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import archetto.ArchettoMod;
import archetto.core.ColorEnum;

import java.lang.reflect.Method;

public class AssetLoader {

    public static String cardArt(String fileName) {
        return ArchettoMod.cardArtPath(fileName);
    }

    public static String relicArt(String fileName) {
        return ArchettoMod.relicPath(fileName);
    }

    public static String relicOutlineArt(String fileName) {
        return ArchettoMod.relicOutlinePath(fileName);
    }

    private static final String[] CARD_ART_FILES = {
            "card_archetto_arrowstorm.png",
            "card_archetto_bulkdraw03.png",
            "card_archetto_bulkdraw08.png",
            "card_archetto_bulkfinale05.png",
            "card_archetto_bulkfinale30.png",
            "card_archetto_bulkguard02.png",
            "card_archetto_bulkguard07.png",
            "card_archetto_bulkstrike01.png",
            "card_archetto_bulkstrike06.png",
            "card_archetto_bulksweep04.png",
            "card_archetto_burstarrow.png",
            "card_archetto_concerto.png",
            "card_archetto_cut.png",
            "card_archetto_defend.png",
            "card_archetto_fanvolley.png",
            "card_archetto_hawkeye.png",
            "card_archetto_jab.png",
            "card_archetto_marktarget.png",
            "card_archetto_pierceshot.png",
            "card_archetto_quickshot.png",
            "card_archetto_quiverrefill.png",
            "card_archetto_reload.png",
            "card_archetto_snapstring.png",
            "card_archetto_steadybreath.png",
            "card_archetto_strike.png",
            "card_archetto_surge.png",
            "card_archetto_swiftstep.png",
            "card_archetto_triplearrow.png",
            "card_archetto_trueshot.png",
            "card_archetto_ward.png",
            "card_archetto_windprep.png",
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
            System.err.println("[archetto] preload card art failed " + path + ": " + e.getMessage());
        }
    }

    public static void reloadColorTextures() {
        try {
            Method load = CustomCard.class.getDeclaredMethod("loadTextureFromString", String.class);
            load.setAccessible(true);
            Method get = CustomCard.class.getDeclaredMethod("getTextureFromString", String.class);
            get.setAccessible(true);
            AbstractCard.CardColor color = ColorEnum.ARCHETTO_COLOR;
            BaseMod.saveAttackBgTexture(color, texture(load, get, ArchettoMod.CARD_BG_512));
            BaseMod.saveSkillBgTexture(color, texture(load, get, ArchettoMod.CARD_BG_SKILL_512));
            BaseMod.savePowerBgTexture(color, texture(load, get, ArchettoMod.CARD_BG_POWER_512));
            BaseMod.saveEnergyOrbTexture(color, texture(load, get, ArchettoMod.ENERGY_ORB_512));
            BaseMod.saveEnergyOrbPortraitTexture(color, texture(load, get, ArchettoMod.ENERGY_ORB_1024));
            BaseMod.saveAttackBgPortraitTexture(color, texture(load, get, ArchettoMod.CARD_BG_1024));
            BaseMod.saveSkillBgPortraitTexture(color, texture(load, get, ArchettoMod.CARD_BG_SKILL_1024));
            BaseMod.savePowerBgPortraitTexture(color, texture(load, get, ArchettoMod.CARD_BG_POWER_1024));
        } catch (Exception e) {
            System.err.println("[archetto] color textures failed: " + e.getMessage());
        }
    }

    private static com.badlogic.gdx.graphics.Texture texture(Method load, Method get, String path) throws Exception {
        load.invoke(null, path);
        return (com.badlogic.gdx.graphics.Texture) get.invoke(null, path);
    }
}
