package highmore;

import basemod.BaseMod;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
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
import highmore.core.ClassEnum;
import highmore.core.ColorEnum;
import highmore.characters.Highmore;
import highmore.cards.HighmoreStrike;
import highmore.cards.HighmoreDefend;
import highmore.cards.HighmoreSurge;
import highmore.cards.HighmoreBulkSweep49;
import highmore.cards.HighmoreBulkDraw48;
import highmore.cards.HighmoreBulkGuard47;
import highmore.cards.HighmoreBulkStrike46;
import highmore.cards.HighmoreBulkFinale45;
import highmore.cards.HighmoreBulkSweep44;
import highmore.cards.HighmoreBulkDraw43;
import highmore.cards.HighmoreBulkGuard42;
import highmore.cards.HighmoreBulkStrike41;
import highmore.cards.HighmoreBulkFinale40;
import highmore.cards.HighmoreBulkSweep39;
import highmore.cards.HighmoreBulkDraw38;
import highmore.cards.HighmoreBulkGuard37;
import highmore.cards.HighmoreBulkStrike36;
import highmore.cards.HighmoreBulkFinale35;
import highmore.cards.HighmoreBulkSweep34;
import highmore.cards.HighmoreBulkDraw33;
import highmore.cards.HighmoreBulkGuard32;
import highmore.cards.HighmoreBulkStrike31;
import highmore.cards.HighmoreBulkFinale30;
import highmore.cards.HighmoreBulkSweep29;
import highmore.cards.HighmoreBulkDraw28;
import highmore.cards.HighmoreBulkGuard27;
import highmore.cards.HighmoreBulkStrike26;
import highmore.cards.HighmoreBulkFinale25;
import highmore.cards.HighmoreBulkSweep24;
import highmore.cards.HighmoreBulkDraw23;
import highmore.cards.HighmoreBulkGuard22;
import highmore.cards.HighmoreBulkStrike21;
import highmore.cards.HighmoreBulkFinale20;
import highmore.cards.HighmoreBulkSweep19;
import highmore.cards.HighmoreBulkDraw18;
import highmore.cards.HighmoreBulkGuard17;
import highmore.cards.HighmoreBulkStrike16;
import highmore.cards.HighmoreBulkFinale15;
import highmore.cards.HighmoreBulkSweep14;
import highmore.cards.HighmoreBulkDraw13;
import highmore.cards.HighmoreBulkGuard12;
import highmore.cards.HighmoreBulkStrike11;
import highmore.cards.HighmoreBulkFinale10;
import highmore.cards.HighmoreBulkSweep09;
import highmore.cards.HighmoreBulkDraw08;
import highmore.cards.HighmoreBulkGuard07;
import highmore.cards.HighmoreBulkStrike06;
import highmore.cards.HighmoreBulkFinale05;
import highmore.cards.HighmoreBulkSweep04;
import highmore.cards.HighmoreBulkDraw03;
import highmore.cards.HighmoreBulkGuard02;
import highmore.cards.HighmoreBulkStrike01;
import highmore.cards.HighmoreCoralCut;
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
import highmore.relics.BulkRelic11;
import highmore.relics.BulkRelic10;
import highmore.relics.BulkRelic09;
import highmore.relics.BulkRelic08;
import highmore.relics.BulkRelic07;
import highmore.relics.BulkRelic06;
import highmore.relics.BulkRelic05;
import highmore.relics.BulkRelic04;
import highmore.relics.BulkRelic03;
import highmore.relics.BulkRelic02;
import highmore.relics.BulkRelic01;
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
        PostInitializeSubscriber {

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
        BaseMod.addCard(new HighmoreCoralCut());
        BaseMod.addCard(new HighmoreBulkStrike01());
        BaseMod.addCard(new HighmoreBulkGuard02());
        BaseMod.addCard(new HighmoreBulkDraw03());
        BaseMod.addCard(new HighmoreBulkSweep04());
        BaseMod.addCard(new HighmoreBulkFinale05());
        BaseMod.addCard(new HighmoreBulkStrike06());
        BaseMod.addCard(new HighmoreBulkGuard07());
        BaseMod.addCard(new HighmoreBulkDraw08());
        BaseMod.addCard(new HighmoreBulkSweep09());
        BaseMod.addCard(new HighmoreBulkFinale10());
        BaseMod.addCard(new HighmoreBulkStrike11());
        BaseMod.addCard(new HighmoreBulkGuard12());
        BaseMod.addCard(new HighmoreBulkDraw13());
        BaseMod.addCard(new HighmoreBulkSweep14());
        BaseMod.addCard(new HighmoreBulkFinale15());
        BaseMod.addCard(new HighmoreBulkStrike16());
        BaseMod.addCard(new HighmoreBulkGuard17());
        BaseMod.addCard(new HighmoreBulkDraw18());
        BaseMod.addCard(new HighmoreBulkSweep19());
        BaseMod.addCard(new HighmoreBulkFinale20());
        BaseMod.addCard(new HighmoreBulkStrike21());
        BaseMod.addCard(new HighmoreBulkGuard22());
        BaseMod.addCard(new HighmoreBulkDraw23());
        BaseMod.addCard(new HighmoreBulkSweep24());
        BaseMod.addCard(new HighmoreBulkFinale25());
        BaseMod.addCard(new HighmoreBulkStrike26());
        BaseMod.addCard(new HighmoreBulkGuard27());
        BaseMod.addCard(new HighmoreBulkDraw28());
        BaseMod.addCard(new HighmoreBulkSweep29());
        BaseMod.addCard(new HighmoreBulkFinale30());
        BaseMod.addCard(new HighmoreBulkStrike31());
        BaseMod.addCard(new HighmoreBulkGuard32());
        BaseMod.addCard(new HighmoreBulkDraw33());
        BaseMod.addCard(new HighmoreBulkSweep34());
        BaseMod.addCard(new HighmoreBulkFinale35());
        BaseMod.addCard(new HighmoreBulkStrike36());
        BaseMod.addCard(new HighmoreBulkGuard37());
        BaseMod.addCard(new HighmoreBulkDraw38());
        BaseMod.addCard(new HighmoreBulkSweep39());
        BaseMod.addCard(new HighmoreBulkFinale40());
        BaseMod.addCard(new HighmoreBulkStrike41());
        BaseMod.addCard(new HighmoreBulkGuard42());
        BaseMod.addCard(new HighmoreBulkDraw43());
        BaseMod.addCard(new HighmoreBulkSweep44());
        BaseMod.addCard(new HighmoreBulkFinale45());
        BaseMod.addCard(new HighmoreBulkStrike46());
        BaseMod.addCard(new HighmoreBulkGuard47());
        BaseMod.addCard(new HighmoreBulkDraw48());
        BaseMod.addCard(new HighmoreBulkSweep49());
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
        BaseMod.addRelicToCustomPool(new BulkRelic01(), ColorEnum.HIGHMORE_COLOR);
        BaseMod.addRelicToCustomPool(new BulkRelic02(), ColorEnum.HIGHMORE_COLOR);
        BaseMod.addRelicToCustomPool(new BulkRelic03(), ColorEnum.HIGHMORE_COLOR);
        BaseMod.addRelicToCustomPool(new BulkRelic04(), ColorEnum.HIGHMORE_COLOR);
        BaseMod.addRelicToCustomPool(new BulkRelic05(), ColorEnum.HIGHMORE_COLOR);
        BaseMod.addRelicToCustomPool(new BulkRelic06(), ColorEnum.HIGHMORE_COLOR);
        BaseMod.addRelicToCustomPool(new BulkRelic07(), ColorEnum.HIGHMORE_COLOR);
        BaseMod.addRelicToCustomPool(new BulkRelic08(), ColorEnum.HIGHMORE_COLOR);
        BaseMod.addRelicToCustomPool(new BulkRelic09(), ColorEnum.HIGHMORE_COLOR);
        BaseMod.addRelicToCustomPool(new BulkRelic10(), ColorEnum.HIGHMORE_COLOR);
        BaseMod.addRelicToCustomPool(new BulkRelic11(), ColorEnum.HIGHMORE_COLOR);
    }

                                    @Override
    public void receiveEditKeywords() {
        BaseMod.addKeyword(MOD_ID, "收割", new String[]{"收割"}, "收割使海沫攻击时回复生命，越战越勇。");
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
}
