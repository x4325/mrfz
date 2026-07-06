package nymph;

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
import nymph.cards.NymphHexSpread;
import nymph.cards.NymphHeartBite;
import nymph.cards.NymphFearFeast;
import nymph.cards.NymphHexLink;
import nymph.cards.NymphNightEcho;
import nymph.cards.NymphSpiritClaw;
import nymph.cards.NymphLockTighten;
import nymph.cards.NymphShadowReap;
import nymph.core.ClassEnum;
import nymph.core.ColorEnum;
import nymph.characters.Nymph;
import nymph.cards.NymphStrike;
import nymph.cards.NymphDefend;
import nymph.cards.NymphSurge;
import nymph.cards.NymphBulkFinale30;
import nymph.cards.NymphBulkDraw08;
import nymph.cards.NymphBulkGuard07;
import nymph.cards.NymphBulkStrike06;
import nymph.cards.NymphBulkFinale05;
import nymph.cards.NymphBulkSweep04;
import nymph.cards.NymphBulkDraw03;
import nymph.cards.NymphBulkGuard02;
import nymph.cards.NymphBulkStrike01;
import nymph.cards.NymphVoidCut;
import nymph.cards.NymphNightmare;
import nymph.cards.NymphSoulBind;
import nymph.cards.NymphDreadGuard;
import nymph.cards.NymphCurseWave;
import nymph.cards.NymphHeartSeal;
import nymph.cards.NymphFearWhisper;
import nymph.cards.NymphHexBolt;
import nymph.cards.NymphCut;
import nymph.cards.NymphWard;
import nymph.cards.NymphJab;
import nymph.relics.NymphStarterRelic;
import nymph.events.NymphWhisperShrine;
import nymph.events.NymphMirrorPool;
import nymph.relics.NymphWaxSeal;
import nymph.relics.NymphHeartKey;
import nymph.relics.NymphFearBell;
import nymph.relics.NymphHexCharm;
import nymph.helpers.AssetLoader;
import nymph.events.NymphHeartDoor;
import nymph.screens.SkinSelectScreen;

@SpireInitializer
public class NymphMod implements
        EditCardsSubscriber,
        EditCharactersSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        OnStartBattleSubscriber,
        PostInitializeSubscriber,
        RenderSubscriber {

    public static final String MOD_ID = "nymph";
    public static final String MOD_NAME = "妮芙";
    public static final String RESOURCE_ROOT = "nymphResources";

    private static final Color MAIN = new Color(0.60f,0.40f,0.70f, 1.0f);
    private static final Color TRAIL = new Color(0.60f,0.40f,0.70f, 1.0f);

    public static void initialize() {
        new NymphMod();
    }

    public NymphMod() {
        BaseMod.subscribe(this);
        BaseMod.addColor(
                ColorEnum.NYMPH_COLOR,
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
        nymph.helpers.BridgeWatchdog.render(sb);
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
        System.out.println("[nymph] 0.4.8-clean loaded");
        SkinSelectScreen.Inst = new SkinSelectScreen();
        BaseMod.addEvent(NymphWhisperShrine.ID, NymphWhisperShrine.class, Exordium.ID);
        BaseMod.addEvent(NymphMirrorPool.ID, NymphMirrorPool.class, TheCity.ID);
        BaseMod.addEvent(NymphHeartDoor.ID, NymphHeartDoor.class, TheBeyond.ID);
    }

                                        @Override
    public void receiveEditCards() {
        AssetLoader.preloadCardArts();
        AssetLoader.reloadColorTextures();
        BaseMod.addCard(new NymphStrike());
        BaseMod.addCard(new NymphDefend());
        BaseMod.addCard(new NymphJab());
        BaseMod.addCard(new NymphWard());
        BaseMod.addCard(new NymphCut());
        BaseMod.addCard(new NymphSurge());
        BaseMod.addCard(new NymphHexBolt());
        BaseMod.addCard(new NymphFearWhisper());
        BaseMod.addCard(new NymphHeartSeal());
        BaseMod.addCard(new NymphCurseWave());
        BaseMod.addCard(new NymphDreadGuard());
        BaseMod.addCard(new NymphSoulBind());
        BaseMod.addCard(new NymphNightmare());
        BaseMod.addCard(new NymphVoidCut());
        BaseMod.addCard(new NymphBulkStrike01());
        BaseMod.addCard(new NymphBulkGuard02());
        BaseMod.addCard(new NymphBulkDraw03());
        BaseMod.addCard(new NymphBulkSweep04());
        BaseMod.addCard(new NymphBulkFinale05());
        BaseMod.addCard(new NymphBulkStrike06());
        BaseMod.addCard(new NymphBulkGuard07());
        BaseMod.addCard(new NymphBulkDraw08());
        BaseMod.addCard(new NymphBulkFinale30());
        BaseMod.addCard(new NymphHexSpread());
        BaseMod.addCard(new NymphHeartBite());
        BaseMod.addCard(new NymphFearFeast());
        BaseMod.addCard(new NymphHexLink());
        BaseMod.addCard(new NymphNightEcho());
        BaseMod.addCard(new NymphSpiritClaw());
        BaseMod.addCard(new NymphLockTighten());
        BaseMod.addCard(new NymphShadowReap());
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(
                new Nymph(CardCrawlGame.playerName),
                imgPath("charSelect/button.png"),
                imgPath("charSelect/portrait.png"),
                ClassEnum.NYMPH
        );
    }

                                        @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new NymphStarterRelic(), ColorEnum.NYMPH_COLOR);
        BaseMod.addRelicToCustomPool(new NymphHexCharm(), ColorEnum.NYMPH_COLOR);
        BaseMod.addRelicToCustomPool(new NymphFearBell(), ColorEnum.NYMPH_COLOR);
        BaseMod.addRelicToCustomPool(new NymphHeartKey(), ColorEnum.NYMPH_COLOR);
        BaseMod.addRelicToCustomPool(new NymphWaxSeal(), ColorEnum.NYMPH_COLOR);
    }

                                        @Override
    public void receiveEditKeywords() {
        BaseMod.addKeyword(MOD_ID, "咒灵", new String[]{"咒灵"}, "咒灵刻在敌人身上：每层使其受到的伤害额外 +2，回合结束时 -1 层。");
        BaseMod.addKeyword(MOD_ID, "恐惧", new String[]{"恐惧"}, "恐惧使敌人下一回合造成的伤害降为 0。");
    }

    @Override
    public void receiveEditStrings() {
        String base = RESOURCE_ROOT + "/localization/";
        BaseMod.loadCustomStringsFile(CardStrings.class, base + "nymph_cards-zh.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, base + "nymph_characters-zh.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, base + "nymph_relics-zh.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, base + "nymph_powers-zh.json");
        BaseMod.loadCustomStringsFile(EventStrings.class, base + "nymph_events-zh.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, base + "nymph_uis-zh.json");
    }

    @Override
    public void receiveOnBattleStart(com.megacrit.cardcrawl.rooms.AbstractRoom room) {
        if (com.megacrit.cardcrawl.dungeons.AbstractDungeon.player instanceof nymph.characters.Nymph) {
            ((nymph.characters.Nymph) com.megacrit.cardcrawl.dungeons.AbstractDungeon.player).playIntroAnimation();
        }
    }
}
