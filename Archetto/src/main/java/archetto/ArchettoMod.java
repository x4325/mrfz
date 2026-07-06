package archetto;

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
import archetto.core.ClassEnum;
import archetto.core.ColorEnum;
import archetto.characters.Archetto;
import archetto.cards.ArchettoStrike;
import archetto.cards.ArchettoDefend;
import archetto.cards.ArchettoSurge;
import archetto.cards.ArchettoBulkFinale30;
import archetto.cards.ArchettoBulkDraw08;
import archetto.cards.ArchettoBulkGuard07;
import archetto.cards.ArchettoBulkStrike06;
import archetto.cards.ArchettoBulkFinale05;
import archetto.cards.ArchettoBulkSweep04;
import archetto.cards.ArchettoBulkDraw03;
import archetto.cards.ArchettoBulkGuard02;
import archetto.cards.ArchettoBulkStrike01;
import archetto.cards.ArchettoPierceShot;
import archetto.cards.ArchettoConcerto;
import archetto.cards.ArchettoWindPrep;
import archetto.cards.ArchettoFanVolley;
import archetto.cards.ArchettoReload;
import archetto.cards.ArchettoBurstArrow;
import archetto.cards.ArchettoMarkTarget;
import archetto.cards.ArchettoQuickShot;
import archetto.cards.ArchettoCut;
import archetto.cards.ArchettoWard;
import archetto.cards.ArchettoJab;
import archetto.relics.ArchettoStarterRelic;
import archetto.events.ArchettoMusicHall;
import archetto.events.ArchettoBrokenString;
import archetto.relics.ArchettoEncoreBow;
import archetto.relics.ArchettoSalePoster;
import archetto.relics.ArchettoTuningFork;
import archetto.relics.ArchettoQuiver;
import archetto.helpers.AssetLoader;
import archetto.events.ArchettoFanMail;
import archetto.screens.SkinSelectScreen;

@SpireInitializer
public class ArchettoMod implements
        EditCardsSubscriber,
        EditCharactersSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        PostInitializeSubscriber {

    public static final String MOD_ID = "archetto";
    public static final String MOD_NAME = "空弦";
    public static final String RESOURCE_ROOT = "archettoResources";

    private static final Color MAIN = new Color(0.85f,0.75f,0.45f, 1.0f);
    private static final Color TRAIL = new Color(0.85f,0.75f,0.45f, 1.0f);

    public static void initialize() {
        new ArchettoMod();
    }

    public ArchettoMod() {
        BaseMod.subscribe(this);
        BaseMod.addColor(
                ColorEnum.ARCHETTO_COLOR,
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
        BaseMod.addEvent(ArchettoMusicHall.ID, ArchettoMusicHall.class, Exordium.ID);
        BaseMod.addEvent(ArchettoBrokenString.ID, ArchettoBrokenString.class, TheCity.ID);
        BaseMod.addEvent(ArchettoFanMail.ID, ArchettoFanMail.class, TheBeyond.ID);
    }

                                    @Override
    public void receiveEditCards() {
        AssetLoader.preloadCardArts();
        AssetLoader.reloadColorTextures();
        BaseMod.addCard(new ArchettoStrike());
        BaseMod.addCard(new ArchettoDefend());
        BaseMod.addCard(new ArchettoJab());
        BaseMod.addCard(new ArchettoWard());
        BaseMod.addCard(new ArchettoCut());
        BaseMod.addCard(new ArchettoSurge());
        BaseMod.addCard(new ArchettoQuickShot());
        BaseMod.addCard(new ArchettoMarkTarget());
        BaseMod.addCard(new ArchettoBurstArrow());
        BaseMod.addCard(new ArchettoReload());
        BaseMod.addCard(new ArchettoFanVolley());
        BaseMod.addCard(new ArchettoWindPrep());
        BaseMod.addCard(new ArchettoConcerto());
        BaseMod.addCard(new ArchettoPierceShot());
        BaseMod.addCard(new ArchettoBulkStrike01());
        BaseMod.addCard(new ArchettoBulkGuard02());
        BaseMod.addCard(new ArchettoBulkDraw03());
        BaseMod.addCard(new ArchettoBulkSweep04());
        BaseMod.addCard(new ArchettoBulkFinale05());
        BaseMod.addCard(new ArchettoBulkStrike06());
        BaseMod.addCard(new ArchettoBulkGuard07());
        BaseMod.addCard(new ArchettoBulkDraw08());
        BaseMod.addCard(new ArchettoBulkFinale30());
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(
                new Archetto(CardCrawlGame.playerName),
                imgPath("charSelect/button.png"),
                imgPath("charSelect/portrait.png"),
                ClassEnum.ARCHETTO
        );
    }

                                    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new ArchettoStarterRelic(), ColorEnum.ARCHETTO_COLOR);
        BaseMod.addRelicToCustomPool(new ArchettoQuiver(), ColorEnum.ARCHETTO_COLOR);
        BaseMod.addRelicToCustomPool(new ArchettoTuningFork(), ColorEnum.ARCHETTO_COLOR);
        BaseMod.addRelicToCustomPool(new ArchettoSalePoster(), ColorEnum.ARCHETTO_COLOR);
        BaseMod.addRelicToCustomPool(new ArchettoEncoreBow(), ColorEnum.ARCHETTO_COLOR);
    }

                                    @Override
    public void receiveEditKeywords() {
        BaseMod.addKeyword(MOD_ID, "瞄准", new String[]{"瞄准"}, "瞄准叠加后强化下一次攻击；部分攻击牌回费。");
    }

    @Override
    public void receiveEditStrings() {
        String base = RESOURCE_ROOT + "/localization/";
        BaseMod.loadCustomStringsFile(CardStrings.class, base + "archetto_cards-zh.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, base + "archetto_characters-zh.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, base + "archetto_relics-zh.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, base + "archetto_powers-zh.json");
        BaseMod.loadCustomStringsFile(EventStrings.class, base + "archetto_events-zh.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, base + "archetto_uis-zh.json");
    }
}
