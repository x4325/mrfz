package highmore;

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
import highmore.cards.HighmoreAbyssPull;
import highmore.cards.HighmoreBloodScale;
import highmore.cards.HighmoreRipTide;
import highmore.cards.HighmoreGreatTide;
import highmore.cards.HighmoreAbyssGaze;
import highmore.cards.HighmoreDrownEmbrace;
import highmore.cards.HighmoreSaltDraw;
import highmore.cards.HighmoreTidePool;
import highmore.core.ClassEnum;
import highmore.core.ColorEnum;
import highmore.characters.Highmore;
import highmore.cards.HighmoreStrike;
import highmore.cards.HighmoreDefend;
import highmore.cards.HighmoreSurge;
import highmore.cards.HighmoreBulkFinale30;
import highmore.cards.HighmoreBulkDraw08;
import highmore.cards.HighmoreBulkGuard07;
import highmore.cards.HighmoreBulkStrike06;
import highmore.cards.HighmoreBulkFinale05;
import highmore.cards.HighmoreBulkSweep04;
import highmore.cards.HighmoreBulkDraw03;
import highmore.cards.HighmoreBulkGuard02;
import highmore.cards.HighmoreBulkStrike01;
import highmore.cards.HighmoreDeadDrift;
import highmore.cards.HighmoreWhirlReap;
import highmore.cards.HighmoreBloodRush;
import highmore.cards.HighmoreBrineWall;
import highmore.cards.HighmoreSaltHarvest;
import highmore.cards.HighmoreScytheSwing;
import highmore.cards.HighmoreTideReap;
import highmore.cards.HighmoreCut;
import highmore.cards.HighmoreWard;
import highmore.cards.HighmoreJab;
import highmore.relics.HighmoreStarterRelic;
import highmore.events.HighmoreBeachedWhisper;
import highmore.events.HighmoreSaltPond;
import highmore.relics.HighmoreShell;
import highmore.relics.HighmoreAnchor;
import highmore.relics.HighmoreCoralScythe;
import highmore.relics.HighmoreTideCharm;
import highmore.helpers.AssetLoader;
import highmore.events.HighmoreCoralOffering;
import highmore.screens.SkinSelectScreen;

@SpireInitializer
public class HighmoreMod implements
        EditCardsSubscriber,
        EditCharactersSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        OnStartBattleSubscriber,
        PostInitializeSubscriber,
        RenderSubscriber {

    public static final String MOD_ID = "highmore";
    public static final String MOD_NAME = "海沫";
    public static final String RESOURCE_ROOT = "highmoreResources";

    private static final Color MAIN = new Color(0.40f,0.70f,0.75f, 1.0f);
    private static final Color TRAIL = new Color(0.40f,0.70f,0.75f, 1.0f);

    public static void initialize() {
        new HighmoreMod();
    }

    public HighmoreMod() {
        BaseMod.subscribe(this);
        BaseMod.addColor(
                ColorEnum.HIGHMORE_COLOR,
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
        highmore.helpers.BridgeWatchdog.render(sb);
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
        System.out.println("[highmore] 0.4.6-equipment loaded");
        SkinSelectScreen.Inst = new SkinSelectScreen();
        BaseMod.addEvent(HighmoreBeachedWhisper.ID, HighmoreBeachedWhisper.class, Exordium.ID);
        BaseMod.addEvent(HighmoreSaltPond.ID, HighmoreSaltPond.class, TheCity.ID);
        BaseMod.addEvent(HighmoreCoralOffering.ID, HighmoreCoralOffering.class, TheBeyond.ID);
    }

                                    @Override
    public void receiveEditCards() {
        AssetLoader.preloadCardArts();
        AssetLoader.reloadColorTextures();
        BaseMod.addCard(new HighmoreStrike());
        BaseMod.addCard(new HighmoreDefend());
        BaseMod.addCard(new HighmoreJab());
        BaseMod.addCard(new HighmoreWard());
        BaseMod.addCard(new HighmoreCut());
        BaseMod.addCard(new HighmoreSurge());
        BaseMod.addCard(new HighmoreTideReap());
        BaseMod.addCard(new HighmoreScytheSwing());
        BaseMod.addCard(new HighmoreSaltHarvest());
        BaseMod.addCard(new HighmoreBrineWall());
        BaseMod.addCard(new HighmoreBloodRush());
        BaseMod.addCard(new HighmoreWhirlReap());
        BaseMod.addCard(new HighmoreDeadDrift());
        BaseMod.addCard(new HighmoreBulkStrike01());
        BaseMod.addCard(new HighmoreBulkGuard02());
        BaseMod.addCard(new HighmoreBulkDraw03());
        BaseMod.addCard(new HighmoreBulkSweep04());
        BaseMod.addCard(new HighmoreBulkFinale05());
        BaseMod.addCard(new HighmoreBulkStrike06());
        BaseMod.addCard(new HighmoreBulkGuard07());
        BaseMod.addCard(new HighmoreBulkDraw08());
        BaseMod.addCard(new HighmoreBulkFinale30());
        BaseMod.addCard(new HighmoreAbyssPull());
        BaseMod.addCard(new HighmoreBloodScale());
        BaseMod.addCard(new HighmoreRipTide());
        BaseMod.addCard(new HighmoreGreatTide());
        BaseMod.addCard(new HighmoreAbyssGaze());
        BaseMod.addCard(new HighmoreDrownEmbrace());
        BaseMod.addCard(new HighmoreSaltDraw());
        BaseMod.addCard(new HighmoreTidePool());
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(
                new Highmore(CardCrawlGame.playerName),
                imgPath("charSelect/button.png"),
                imgPath("charSelect/portrait.png"),
                ClassEnum.HIGHMORE
        );
    }

                                    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new HighmoreStarterRelic(), ColorEnum.HIGHMORE_COLOR);
        BaseMod.addRelicToCustomPool(new HighmoreTideCharm(), ColorEnum.HIGHMORE_COLOR);
        BaseMod.addRelicToCustomPool(new HighmoreCoralScythe(), ColorEnum.HIGHMORE_COLOR);
        BaseMod.addRelicToCustomPool(new HighmoreAnchor(), ColorEnum.HIGHMORE_COLOR);
        BaseMod.addRelicToCustomPool(new HighmoreShell(), ColorEnum.HIGHMORE_COLOR);
    }

                                    @Override
    public void receiveEditKeywords() {
        BaseMod.addKeyword(MOD_ID, "收割", new String[]{"收割"}, "每层收割使海沫的攻击每次命中回复 1 点生命。");
        BaseMod.addKeyword(MOD_ID, "禁疗", new String[]{"禁疗"}, "海沫无法被正常治疗：外部治疗只有 25% 生效，只有收割与潮汐来源全额回复。");
    }

    @Override
    public void receiveEditStrings() {
        String base = RESOURCE_ROOT + "/localization/";
        BaseMod.loadCustomStringsFile(CardStrings.class, base + "highmore_cards-zh.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, base + "highmore_characters-zh.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, base + "highmore_relics-zh.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, base + "highmore_powers-zh.json");
        BaseMod.loadCustomStringsFile(EventStrings.class, base + "highmore_events-zh.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, base + "highmore_uis-zh.json");
    }

    @Override
    public void receiveOnBattleStart(com.megacrit.cardcrawl.rooms.AbstractRoom room) {
        if (com.megacrit.cardcrawl.dungeons.AbstractDungeon.player instanceof highmore.characters.Highmore) {
            ((highmore.characters.Highmore) com.megacrit.cardcrawl.dungeons.AbstractDungeon.player).playIntroAnimation();
        }
    }
}
