package haruka;

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
import haruka.cards.HarukaEarlyBurst;
import haruka.cards.HarukaGatherFlame;
import haruka.cards.HarukaFlameHeart;
import haruka.cards.HarukaDuetSpark;
import haruka.cards.HarukaFireTree;
import haruka.cards.HarukaAddFuel;
import haruka.cards.HarukaAfterHeat;
import haruka.cards.HarukaFinalDance;
import haruka.core.ClassEnum;
import haruka.core.ColorEnum;
import haruka.characters.Haruka;
import haruka.cards.HarukaStrike;
import haruka.cards.HarukaDefend;
import haruka.cards.HarukaSurge;
import haruka.cards.HarukaBulkFinale30;
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
        OnStartBattleSubscriber,
        PostInitializeSubscriber,
        RenderSubscriber {

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

    @Override
    public void receiveRender(com.badlogic.gdx.graphics.g2d.SpriteBatch sb) {
        haruka.helpers.BridgeWatchdog.render(sb);
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
        System.out.println("[haruka] 0.5.4-cmefix loaded");
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
        BaseMod.addCard(new HarukaBulkFinale30());
        BaseMod.addCard(new HarukaEarlyBurst());
        BaseMod.addCard(new HarukaGatherFlame());
        BaseMod.addCard(new HarukaFlameHeart());
        BaseMod.addCard(new HarukaDuetSpark());
        BaseMod.addCard(new HarukaFireTree());
        BaseMod.addCard(new HarukaAddFuel());
        BaseMod.addCard(new HarukaAfterHeat());
        BaseMod.addCard(new HarukaFinalDance());
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

    @Override
    public void receiveOnBattleStart(com.megacrit.cardcrawl.rooms.AbstractRoom room) {
        if (com.megacrit.cardcrawl.dungeons.AbstractDungeon.player instanceof haruka.characters.Haruka) {
            ((haruka.characters.Haruka) com.megacrit.cardcrawl.dungeons.AbstractDungeon.player).playIntroAnimation();
        }
    }
}
