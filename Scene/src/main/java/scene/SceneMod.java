package scene;

import basemod.BaseMod;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.RenderSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import scene.cards.SceneDevelopRush;
import scene.cards.SceneWideFrame;
import scene.cards.SceneBurstShutter;
import scene.cards.SceneNegative;
import scene.cards.SceneOverExposure;
import scene.cards.SceneTimerShutter;
import scene.cards.SceneDarkroomWash;
import scene.cards.SceneMasterLens;
import scene.core.ClassEnum;
import scene.core.ColorEnum;
import scene.characters.Scene;
import scene.cards.SceneStrike;
import scene.cards.SceneDefend;
import scene.cards.SceneSurge;
import scene.cards.SceneBulkFinale30;
import scene.cards.SceneBulkDraw08;
import scene.cards.SceneBulkGuard07;
import scene.cards.SceneBulkStrike06;
import scene.cards.SceneBulkFinale05;
import scene.cards.SceneBulkSweep04;
import scene.cards.SceneBulkDraw03;
import scene.cards.SceneBulkGuard02;
import scene.cards.SceneBulkStrike01;
import scene.cards.SceneFreezeFrame;
import scene.cards.SceneDevelop;
import scene.cards.SceneLongExposure;
import scene.cards.ScenePanScan;
import scene.cards.SceneLensDraw;
import scene.cards.SceneSnapshot;
import scene.cards.SceneShutterGuard;
import scene.cards.SceneFocusShot;
import scene.cards.SceneCut;
import scene.cards.SceneWard;
import scene.cards.SceneJab;
import scene.relics.SceneStarterRelic;
import scene.events.SceneDarkroom;
import scene.events.SceneGallery;
import scene.relics.SceneOldCamera;
import scene.relics.SceneWideLens;
import scene.relics.SceneFilmRoll;
import scene.relics.SceneTripod;
import scene.helpers.AssetLoader;
import scene.events.SceneBrokenShutter;
import scene.screens.SkinSelectScreen;

@SpireInitializer
public class SceneMod implements
        EditCardsSubscriber,
        EditCharactersSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        OnStartBattleSubscriber,
        PostInitializeSubscriber,
        RenderSubscriber {

    public static final String MOD_ID = "scene";
    public static final String MOD_NAME = "稀音";
    public static final String RESOURCE_ROOT = "sceneResources";

    private static final Color MAIN = new Color(0.55f,0.65f,0.80f, 1.0f);
    private static final Color TRAIL = new Color(0.55f,0.65f,0.80f, 1.0f);

    public static void initialize() {
        new SceneMod();
    }

    public SceneMod() {
        BaseMod.subscribe(this);
        BaseMod.addColor(
                ColorEnum.SCENE_COLOR,
                MAIN, MAIN, MAIN, MAIN, MAIN, MAIN, MAIN,
                CARD_BG_512, CARD_BG_SKILL_512, CARD_BG_POWER_512,
                ENERGY_ORB_512,
                CARD_BG_1024, CARD_BG_SKILL_1024, CARD_BG_POWER_1024,
                ENERGY_ORB_1024,
                CARD_SMALL_ORB
        );
    }

    // Card background / orb texture paths (img/512, img/1024, img/char)
    public static final String CARD_BG_512        = imgPath("512/bg_attack.png");
    public static final String CARD_BG_SKILL_512  = imgPath("512/bg_skill.png");
    public static final String CARD_BG_POWER_512  = imgPath("512/bg_power.png");
    public static final String ENERGY_ORB_512     = imgPath("512/energy.png");
    public static final String CARD_BG_1024       = imgPath("1024/bg_attack.png");
    public static final String CARD_BG_SKILL_1024 = imgPath("1024/bg_skill.png");
    public static final String CARD_BG_POWER_1024 = imgPath("1024/bg_power.png");
    public static final String ENERGY_ORB_1024    = imgPath("1024/energy.png");
    public static final String CARD_SMALL_ORB     = imgPath("char/small_orb.png");

    @Override
    public void receiveRender(com.badlogic.gdx.graphics.g2d.SpriteBatch sb) {
        scene.helpers.BridgeWatchdog.render(sb);
    }

    public static String makeID(String id) {
        return MOD_ID + ":" + id;
    }

    public static String resourcePath(String rel) {
        return RESOURCE_ROOT + "/" + rel;
    }

    public static String imgPath(String rel) {
        return resourcePath("img/" + rel);
    }

    public static String cardArtPath(String fileName) {
        return resourcePath("img/cards/" + fileName);
    }

    public static String relicPath(String fileName) {
        return resourcePath("img/relics/" + fileName);
    }

    public static String relicOutlinePath(String fileName) {
        return resourcePath("img/relics/outline/" + fileName);
    }

                                @Override
    public void receivePostInitialize() {
        System.out.println("[scene] 0.5.3-bellyfix loaded");
        SkinSelectScreen.Inst = new SkinSelectScreen();
        BaseMod.addEvent(SceneDarkroom.ID, SceneDarkroom.class, Exordium.ID);
        BaseMod.addEvent(SceneGallery.ID, SceneGallery.class, TheCity.ID);
        BaseMod.addEvent(SceneBrokenShutter.ID, SceneBrokenShutter.class, TheBeyond.ID);
    }

                                    @Override
    public void receiveEditCards() {
        AssetLoader.preloadCardArts();
        AssetLoader.reloadColorTextures();
        BaseMod.addCard(new SceneStrike());
        BaseMod.addCard(new SceneDefend());
        BaseMod.addCard(new SceneJab());
        BaseMod.addCard(new SceneWard());
        BaseMod.addCard(new SceneCut());
        BaseMod.addCard(new SceneSurge());
        BaseMod.addCard(new SceneFocusShot());
        BaseMod.addCard(new SceneShutterGuard());
        BaseMod.addCard(new SceneSnapshot());
        BaseMod.addCard(new SceneLensDraw());
        BaseMod.addCard(new ScenePanScan());
        BaseMod.addCard(new SceneLongExposure());
        BaseMod.addCard(new SceneDevelop());
        BaseMod.addCard(new SceneFreezeFrame());
        BaseMod.addCard(new SceneBulkStrike01());
        BaseMod.addCard(new SceneBulkGuard02());
        BaseMod.addCard(new SceneBulkDraw03());
        BaseMod.addCard(new SceneBulkSweep04());
        BaseMod.addCard(new SceneBulkFinale05());
        BaseMod.addCard(new SceneBulkStrike06());
        BaseMod.addCard(new SceneBulkGuard07());
        BaseMod.addCard(new SceneBulkDraw08());
        BaseMod.addCard(new SceneBulkFinale30());
        BaseMod.addCard(new SceneDevelopRush());
        BaseMod.addCard(new SceneWideFrame());
        BaseMod.addCard(new SceneBurstShutter());
        BaseMod.addCard(new SceneNegative());
        BaseMod.addCard(new SceneOverExposure());
        BaseMod.addCard(new SceneTimerShutter());
        BaseMod.addCard(new SceneDarkroomWash());
        BaseMod.addCard(new SceneMasterLens());
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(
                new Scene(CardCrawlGame.playerName),
                imgPath("charSelect/button.png"),
                imgPath("charSelect/portrait.png"),
                ClassEnum.SCENE
        );
    }

                                    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new SceneStarterRelic(), ColorEnum.SCENE_COLOR);
        BaseMod.addRelicToCustomPool(new SceneTripod(), ColorEnum.SCENE_COLOR);
        BaseMod.addRelicToCustomPool(new SceneFilmRoll(), ColorEnum.SCENE_COLOR);
        BaseMod.addRelicToCustomPool(new SceneWideLens(), ColorEnum.SCENE_COLOR);
        BaseMod.addRelicToCustomPool(new SceneOldCamera(), ColorEnum.SCENE_COLOR);
    }

                                    @Override
    public void receiveEditKeywords() {
        BaseMod.addKeyword(MOD_ID, "取景", new String[]{"取景"}, "取景是稀音的资源，部分卡牌获取或消耗取景。");
    }

    @Override
    public void receiveEditStrings() {
        String base = RESOURCE_ROOT + "/localization/";
        BaseMod.loadCustomStringsFile(CardStrings.class, base + "scene_cards-zh.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, base + "scene_characters-zh.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, base + "scene_relics-zh.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, base + "scene_powers-zh.json");
        BaseMod.loadCustomStringsFile(EventStrings.class, base + "scene_events-zh.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, base + "scene_uis-zh.json");
    }

    @Override
    public void receiveOnBattleStart(com.megacrit.cardcrawl.rooms.AbstractRoom room) {
        if (com.megacrit.cardcrawl.dungeons.AbstractDungeon.player instanceof scene.characters.Scene) {
            ((scene.characters.Scene) com.megacrit.cardcrawl.dungeons.AbstractDungeon.player).playIntroAnimation();
        }
    }
}
