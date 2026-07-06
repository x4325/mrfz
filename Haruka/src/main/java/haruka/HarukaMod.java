package haruka;

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
import haruka.core.ClassEnum;
import haruka.core.ColorEnum;
import haruka.characters.Haruka;
import haruka.cards.HarukaStrike;
import haruka.cards.HarukaDefend;
import haruka.cards.HarukaSurge;
import haruka.cards.HarukaBulkSweep49;
import haruka.cards.HarukaBulkDraw48;
import haruka.cards.HarukaBulkGuard47;
import haruka.cards.HarukaBulkStrike46;
import haruka.cards.HarukaBulkFinale45;
import haruka.cards.HarukaBulkSweep44;
import haruka.cards.HarukaBulkDraw43;
import haruka.cards.HarukaBulkGuard42;
import haruka.cards.HarukaBulkStrike41;
import haruka.cards.HarukaBulkFinale40;
import haruka.cards.HarukaBulkSweep39;
import haruka.cards.HarukaBulkDraw38;
import haruka.cards.HarukaBulkGuard37;
import haruka.cards.HarukaBulkStrike36;
import haruka.cards.HarukaBulkFinale35;
import haruka.cards.HarukaBulkSweep34;
import haruka.cards.HarukaBulkDraw33;
import haruka.cards.HarukaBulkGuard32;
import haruka.cards.HarukaBulkStrike31;
import haruka.cards.HarukaBulkFinale30;
import haruka.cards.HarukaBulkSweep29;
import haruka.cards.HarukaBulkDraw28;
import haruka.cards.HarukaBulkGuard27;
import haruka.cards.HarukaBulkStrike26;
import haruka.cards.HarukaBulkFinale25;
import haruka.cards.HarukaBulkSweep24;
import haruka.cards.HarukaBulkDraw23;
import haruka.cards.HarukaBulkGuard22;
import haruka.cards.HarukaBulkStrike21;
import haruka.cards.HarukaBulkFinale20;
import haruka.cards.HarukaBulkSweep19;
import haruka.cards.HarukaBulkDraw18;
import haruka.cards.HarukaBulkGuard17;
import haruka.cards.HarukaBulkStrike16;
import haruka.cards.HarukaBulkFinale15;
import haruka.cards.HarukaBulkSweep14;
import haruka.cards.HarukaBulkDraw13;
import haruka.cards.HarukaBulkGuard12;
import haruka.cards.HarukaBulkStrike11;
import haruka.cards.HarukaBulkFinale10;
import haruka.cards.HarukaBulkSweep09;
import haruka.cards.HarukaBulkDraw08;
import haruka.cards.HarukaBulkGuard07;
import haruka.cards.HarukaBulkStrike06;
import haruka.cards.HarukaBulkFinale05;
import haruka.cards.HarukaBulkSweep04;
import haruka.cards.HarukaBulkDraw03;
import haruka.cards.HarukaBulkGuard02;
import haruka.cards.HarukaBulkStrike01;
import haruka.cards.HarukaEncoreShout;
import haruka.cards.HarukaSparkWave;
import haruka.cards.HarukaFinaleMark;
import haruka.cards.HarukaAshGuard;
import haruka.cards.HarukaDragonDance;
import haruka.cards.HarukaFestivalStep;
import haruka.cards.HarukaFireworkPrep;
import haruka.cards.HarukaSparkKick;
import haruka.cards.HarukaCut;
import haruka.cards.HarukaWard;
import haruka.cards.HarukaJab;
import haruka.relics.HarukaStarterRelic;
import haruka.relics.BulkRelic11;
import haruka.relics.BulkRelic10;
import haruka.relics.BulkRelic09;
import haruka.relics.BulkRelic08;
import haruka.relics.BulkRelic07;
import haruka.relics.BulkRelic06;
import haruka.relics.BulkRelic05;
import haruka.relics.BulkRelic04;
import haruka.relics.BulkRelic03;
import haruka.relics.BulkRelic02;
import haruka.relics.BulkRelic01;
import haruka.events.HarukaFestivalGate;
import haruka.events.HarukaFireworkFault;
import haruka.relics.HarukaLantern;
import haruka.relics.HarukaDrumBeat;
import haruka.relics.HarukaFestivalMask;
import haruka.relics.HarukaSparkler;
import haruka.helpers.AssetLoader;
import haruka.events.HarukaGrandFinale;
import haruka.screens.SkinSelectScreen;

@SpireInitializer
public class HarukaMod implements
        EditCardsSubscriber,
        EditCharactersSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        PostInitializeSubscriber {

    public static final String MOD_ID = "haruka";
    public static final String MOD_NAME = "遥";
    public static final String RESOURCE_ROOT = "harukaResources";

    private static final Color MAIN = new Color(0.90f,0.55f,0.65f, 1.0f);
    private static final Color TRAIL = new Color(0.90f,0.55f,0.65f, 1.0f);

    public static void initialize() {
        new HarukaMod();
    }

    public HarukaMod() {
        BaseMod.subscribe(this);
        BaseMod.addColor(
                ColorEnum.HARUKA_COLOR,
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
        BaseMod.addEvent(HarukaFestivalGate.ID, HarukaFestivalGate.class, Exordium.ID);
        BaseMod.addEvent(HarukaFireworkFault.ID, HarukaFireworkFault.class, TheCity.ID);
        BaseMod.addEvent(HarukaGrandFinale.ID, HarukaGrandFinale.class, TheBeyond.ID);
    }

                                    @Override
    public void receiveEditCards() {
        AssetLoader.preloadCardArts();
        AssetLoader.reloadColorTextures();
        BaseMod.addCard(new HarukaStrike());
        BaseMod.addCard(new HarukaDefend());
        BaseMod.addCard(new HarukaJab());
        BaseMod.addCard(new HarukaWard());
        BaseMod.addCard(new HarukaCut());
        BaseMod.addCard(new HarukaSurge());
        BaseMod.addCard(new HarukaSparkKick());
        BaseMod.addCard(new HarukaFireworkPrep());
        BaseMod.addCard(new HarukaFestivalStep());
        BaseMod.addCard(new HarukaDragonDance());
        BaseMod.addCard(new HarukaAshGuard());
        BaseMod.addCard(new HarukaFinaleMark());
        BaseMod.addCard(new HarukaSparkWave());
        BaseMod.addCard(new HarukaEncoreShout());
        BaseMod.addCard(new HarukaBulkStrike01());
        BaseMod.addCard(new HarukaBulkGuard02());
        BaseMod.addCard(new HarukaBulkDraw03());
        BaseMod.addCard(new HarukaBulkSweep04());
        BaseMod.addCard(new HarukaBulkFinale05());
        BaseMod.addCard(new HarukaBulkStrike06());
        BaseMod.addCard(new HarukaBulkGuard07());
        BaseMod.addCard(new HarukaBulkDraw08());
        BaseMod.addCard(new HarukaBulkSweep09());
        BaseMod.addCard(new HarukaBulkFinale10());
        BaseMod.addCard(new HarukaBulkStrike11());
        BaseMod.addCard(new HarukaBulkGuard12());
        BaseMod.addCard(new HarukaBulkDraw13());
        BaseMod.addCard(new HarukaBulkSweep14());
        BaseMod.addCard(new HarukaBulkFinale15());
        BaseMod.addCard(new HarukaBulkStrike16());
        BaseMod.addCard(new HarukaBulkGuard17());
        BaseMod.addCard(new HarukaBulkDraw18());
        BaseMod.addCard(new HarukaBulkSweep19());
        BaseMod.addCard(new HarukaBulkFinale20());
        BaseMod.addCard(new HarukaBulkStrike21());
        BaseMod.addCard(new HarukaBulkGuard22());
        BaseMod.addCard(new HarukaBulkDraw23());
        BaseMod.addCard(new HarukaBulkSweep24());
        BaseMod.addCard(new HarukaBulkFinale25());
        BaseMod.addCard(new HarukaBulkStrike26());
        BaseMod.addCard(new HarukaBulkGuard27());
        BaseMod.addCard(new HarukaBulkDraw28());
        BaseMod.addCard(new HarukaBulkSweep29());
        BaseMod.addCard(new HarukaBulkFinale30());
        BaseMod.addCard(new HarukaBulkStrike31());
        BaseMod.addCard(new HarukaBulkGuard32());
        BaseMod.addCard(new HarukaBulkDraw33());
        BaseMod.addCard(new HarukaBulkSweep34());
        BaseMod.addCard(new HarukaBulkFinale35());
        BaseMod.addCard(new HarukaBulkStrike36());
        BaseMod.addCard(new HarukaBulkGuard37());
        BaseMod.addCard(new HarukaBulkDraw38());
        BaseMod.addCard(new HarukaBulkSweep39());
        BaseMod.addCard(new HarukaBulkFinale40());
        BaseMod.addCard(new HarukaBulkStrike41());
        BaseMod.addCard(new HarukaBulkGuard42());
        BaseMod.addCard(new HarukaBulkDraw43());
        BaseMod.addCard(new HarukaBulkSweep44());
        BaseMod.addCard(new HarukaBulkFinale45());
        BaseMod.addCard(new HarukaBulkStrike46());
        BaseMod.addCard(new HarukaBulkGuard47());
        BaseMod.addCard(new HarukaBulkDraw48());
        BaseMod.addCard(new HarukaBulkSweep49());
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(
                new Haruka(CardCrawlGame.playerName),
                imgPath("charSelect/button.png"),
                imgPath("charSelect/portrait.png"),
                ClassEnum.HARUKA
        );
    }

                                    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new HarukaStarterRelic(), ColorEnum.HARUKA_COLOR);
        BaseMod.addRelicToCustomPool(new HarukaSparkler(), ColorEnum.HARUKA_COLOR);
        BaseMod.addRelicToCustomPool(new HarukaFestivalMask(), ColorEnum.HARUKA_COLOR);
        BaseMod.addRelicToCustomPool(new HarukaDrumBeat(), ColorEnum.HARUKA_COLOR);
        BaseMod.addRelicToCustomPool(new HarukaLantern(), ColorEnum.HARUKA_COLOR);
        BaseMod.addRelicToCustomPool(new BulkRelic01(), ColorEnum.HARUKA_COLOR);
        BaseMod.addRelicToCustomPool(new BulkRelic02(), ColorEnum.HARUKA_COLOR);
        BaseMod.addRelicToCustomPool(new BulkRelic03(), ColorEnum.HARUKA_COLOR);
        BaseMod.addRelicToCustomPool(new BulkRelic04(), ColorEnum.HARUKA_COLOR);
        BaseMod.addRelicToCustomPool(new BulkRelic05(), ColorEnum.HARUKA_COLOR);
        BaseMod.addRelicToCustomPool(new BulkRelic06(), ColorEnum.HARUKA_COLOR);
        BaseMod.addRelicToCustomPool(new BulkRelic07(), ColorEnum.HARUKA_COLOR);
        BaseMod.addRelicToCustomPool(new BulkRelic08(), ColorEnum.HARUKA_COLOR);
        BaseMod.addRelicToCustomPool(new BulkRelic09(), ColorEnum.HARUKA_COLOR);
        BaseMod.addRelicToCustomPool(new BulkRelic10(), ColorEnum.HARUKA_COLOR);
        BaseMod.addRelicToCustomPool(new BulkRelic11(), ColorEnum.HARUKA_COLOR);
    }

                                    @Override
    public void receiveEditKeywords() {
        BaseMod.addKeyword(MOD_ID, "花火", new String[]{"花火"}, "花火在攻击时累积，达到阈值引爆对全体敌人造成法术爆发。");
    }

    @Override
    public void receiveEditStrings() {
        String base = RESOURCE_ROOT + "/localization/";
        BaseMod.loadCustomStringsFile(CardStrings.class, base + "haruka_cards-zh.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, base + "haruka_characters-zh.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, base + "haruka_relics-zh.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, base + "haruka_powers-zh.json");
        BaseMod.loadCustomStringsFile(EventStrings.class, base + "haruka_events-zh.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, base + "haruka_uis-zh.json");
    }
}
