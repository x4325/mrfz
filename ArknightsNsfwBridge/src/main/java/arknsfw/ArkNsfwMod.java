package arknsfw;

import basemod.BaseMod;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostUpdateSubscriber;
import basemod.interfaces.RenderSubscriber;
import basemod.interfaces.OnPlayerTurnStartSubscriber;
import basemod.interfaces.OnPlayerLoseHpSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.helpers.RelicType;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import arknsfw.cards.eyja.*;
import arknsfw.cards.muel.*;
import arknsfw.events.eyja.*;
import arknsfw.events.muel.*;
import arknsfw.helpers.ArkCharacterSetup;
import arknsfw.cards.curses.eyja.*;
import arknsfw.cards.curses.muel.*;
import arknsfw.cards.nymph.*;
import arknsfw.cards.curses.nymph.*;
import arknsfw.relics.nymph.*;
import arknsfw.relics.curses.nymph.*;
import arknsfw.events.nymph.*;

import arknsfw.cards.haruka.*;
import arknsfw.cards.curses.haruka.*;
import arknsfw.relics.haruka.*;
import arknsfw.relics.curses.haruka.*;
import arknsfw.events.haruka.*;

import arknsfw.cards.archetto.*;
import arknsfw.cards.curses.archetto.*;
import arknsfw.relics.archetto.*;
import arknsfw.relics.curses.archetto.*;
import arknsfw.events.archetto.*;

import arknsfw.cards.highmore.*;
import arknsfw.cards.curses.highmore.*;
import arknsfw.relics.highmore.*;
import arknsfw.relics.curses.highmore.*;
import arknsfw.events.highmore.*;

import arknsfw.cards.scene.*;
import arknsfw.cards.curses.scene.*;
import arknsfw.relics.scene.*;
import arknsfw.relics.curses.scene.*;
import arknsfw.events.scene.*;

import arknsfw.relics.eyja.*;
import arknsfw.relics.muel.*;
import arknsfw.relics.curses.eyja.*;
import arknsfw.relics.curses.muel.*;
import liesecore.potions.ClimaxDraughtPotion;
import arknsfw.potions.eyja.*;
import arknsfw.potions.muel.*;
import Eyjafjalla.modcore.ColorEnum;

@SpireInitializer
public class ArkNsfwMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        PostBattleSubscriber,
        PostUpdateSubscriber,
        RenderSubscriber,
        OnPlayerTurnStartSubscriber,
        OnPlayerLoseHpSubscriber,
        PostInitializeSubscriber {

    public static final String modID = "arknsfw";
    public static final String RESOURCES = "arknsfwResources";

    private static final Color EYJA_LIQUID = new Color(0.88f, 0.35f, 0.22f, 1f);
    private static final Color EYJA_HYBRID = new Color(0.95f, 0.55f, 0.25f, 1f);
    private static final Color EYJA_SPOTS = new Color(1f, 0.75f, 0.35f, 1f);
    private static final Color MUEL_LIQUID = new Color(0.25f, 0.72f, 0.55f, 1f);
    private static final Color MUEL_HYBRID = new Color(0.45f, 0.85f, 0.7f, 1f);
    private static final Color MUEL_SPOTS = new Color(0.85f, 0.95f, 0.55f, 1f);

    public static void initialize() {
        new ArkNsfwMod();
    }

    public ArkNsfwMod() {
        BaseMod.subscribe(this);
    }

    public static String makeID(String id) {
        return modID + ":" + id;
    }

    public static String makePath(String rel) {
        return RESOURCES + "/" + rel;
    }

    public static String makeCardPath(String file) {
        return makePath("images/cards/" + file);
    }

    public static String makeRelicPath(String file) {
        return makePath("images/relics/" + file);
    }

    public static String makeRelicOutlinePath(String file) {
        return makePath("images/relics/outline/" + file);
    }

    public static String makeImagePath(String rel) {
        return makePath("images/" + rel);
    }

    @Override
    public void receivePostInitialize() {
        System.out.println("[arknsfw] 0.4.2-verify loaded (portrait+postbattle render active)");
        ArkCharacterSetup.registerCharacters();
        registerEvents();
        registerPotions();
    }

    private static void registerPotions() {
        // 正确注册方式：potionID 用药水自身 ID，并限定所属角色（参照缪尔赛思 mod 的做法）。
        // 此前误把颜色名当 potionID，导致所有药水互相覆盖、只有最后一瓶生效。
        com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass eyjaClass =
                Eyjafjalla.modcore.ClassEnum.Eyjafjalla_CLASS;
        com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass muelClass =
                Muelsyse.patches.ClassEnum.Muelsyse_CLASS;
        BaseMod.addPotion(CloudWarmTonicPotion.class, EYJA_LIQUID, EYJA_HYBRID, EYJA_SPOTS, CloudWarmTonicPotion.ID, eyjaClass);
        BaseMod.addPotion(PyroAphroPotion.class, EYJA_LIQUID, EYJA_HYBRID, EYJA_SPOTS, PyroAphroPotion.ID, eyjaClass);
        BaseMod.addPotion(VolcanicNectarPotion.class, EYJA_LIQUID, EYJA_HYBRID, EYJA_SPOTS, VolcanicNectarPotion.ID, eyjaClass);
        BaseMod.addPotion(AshDregPotion.class, EYJA_LIQUID, EYJA_HYBRID, EYJA_SPOTS, AshDregPotion.ID, eyjaClass);
        BaseMod.addPotion(FeverSedimentPotion.class, EYJA_LIQUID, EYJA_HYBRID, EYJA_SPOTS, FeverSedimentPotion.ID, eyjaClass);
        BaseMod.addPotion(EmberLockPotion.class, EYJA_LIQUID, EYJA_HYBRID, EYJA_SPOTS, EmberLockPotion.ID, eyjaClass);
        BaseMod.addPotion(HeatLingerPotion.class, EYJA_LIQUID, EYJA_HYBRID, EYJA_SPOTS, HeatLingerPotion.ID, eyjaClass);
        BaseMod.addPotion(EmberDraughtPotion.class, EYJA_LIQUID, EYJA_HYBRID, EYJA_SPOTS, EmberDraughtPotion.ID, eyjaClass);
        BaseMod.addPotion(LavaBloomPotion.class, EYJA_LIQUID, EYJA_HYBRID, EYJA_SPOTS, LavaBloomPotion.ID, eyjaClass);
        BaseMod.addPotion(CollarSootPotion.class, EYJA_LIQUID, EYJA_HYBRID, EYJA_SPOTS, CollarSootPotion.ID, eyjaClass);
        BaseMod.addPotion(ContractSedimentPotion.class, EYJA_LIQUID, EYJA_HYBRID, EYJA_SPOTS, ContractSedimentPotion.ID, eyjaClass);
        BaseMod.addPotion(MagmaEchoDraughtPotion.class, EYJA_LIQUID, EYJA_HYBRID, EYJA_SPOTS, MagmaEchoDraughtPotion.ID, eyjaClass);
        BaseMod.addPotion(AshShameMistPotion.class, EYJA_LIQUID, EYJA_HYBRID, EYJA_SPOTS, AshShameMistPotion.ID, eyjaClass);
        // 绝顶药剂两位角色通用：不限定角色注册一次（同一 potionID 只能注册一次）
        BaseMod.addPotion(ClimaxDraughtPotion.class, EYJA_LIQUID, EYJA_HYBRID, EYJA_SPOTS, ClimaxDraughtPotion.ID);
        BaseMod.addPotion(BubbleSerumPotion.class, MUEL_LIQUID, MUEL_HYBRID, MUEL_SPOTS, BubbleSerumPotion.ID, muelClass);
        BaseMod.addPotion(CloneDripPotion.class, MUEL_LIQUID, MUEL_HYBRID, MUEL_SPOTS, CloneDripPotion.ID, muelClass);
        BaseMod.addPotion(RootDewPotion.class, MUEL_LIQUID, MUEL_HYBRID, MUEL_SPOTS, RootDewPotion.ID, muelClass);
        BaseMod.addPotion(FloodWastePotion.class, MUEL_LIQUID, MUEL_HYBRID, MUEL_SPOTS, FloodWastePotion.ID, muelClass);
        BaseMod.addPotion(SeedSludgePotion.class, MUEL_LIQUID, MUEL_HYBRID, MUEL_SPOTS, SeedSludgePotion.ID, muelClass);
        BaseMod.addPotion(MuteFoamPotion.class, MUEL_LIQUID, MUEL_HYBRID, MUEL_SPOTS, MuteFoamPotion.ID, muelClass);
        BaseMod.addPotion(MistSprayPotion.class, MUEL_LIQUID, MUEL_HYBRID, MUEL_SPOTS, MistSprayPotion.ID, muelClass);
        BaseMod.addPotion(TwinSapPotion.class, MUEL_LIQUID, MUEL_HYBRID, MUEL_SPOTS, TwinSapPotion.ID, muelClass);
        BaseMod.addPotion(GreenhouseNectarPotion.class, MUEL_LIQUID, MUEL_HYBRID, MUEL_SPOTS, GreenhouseNectarPotion.ID, muelClass);
        BaseMod.addPotion(FloodMarkDraughtPotion.class, MUEL_LIQUID, MUEL_HYBRID, MUEL_SPOTS, FloodMarkDraughtPotion.ID, muelClass);
        BaseMod.addPotion(BubbleMuteDraughtPotion.class, MUEL_LIQUID, MUEL_HYBRID, MUEL_SPOTS, BubbleMuteDraughtPotion.ID, muelClass);
        BaseMod.addPotion(EchoSludgePotion.class, MUEL_LIQUID, MUEL_HYBRID, MUEL_SPOTS, EchoSludgePotion.ID, muelClass);
        BaseMod.addPotion(BubbleShameMistPotion.class, MUEL_LIQUID, MUEL_HYBRID, MUEL_SPOTS, BubbleShameMistPotion.ID, muelClass);
        // 五名自制干员共用的色情/堕落药水（不限定角色，桥接 mod 仅在这些角色环境下使用）
        com.badlogic.gdx.graphics.Color fiveLiquid = new com.badlogic.gdx.graphics.Color(0.95f, 0.45f, 0.65f, 1f);
        com.badlogic.gdx.graphics.Color fiveHybrid = new com.badlogic.gdx.graphics.Color(1f, 0.65f, 0.8f, 1f);
        com.badlogic.gdx.graphics.Color fiveSpots = new com.badlogic.gdx.graphics.Color(1f, 0.85f, 0.92f, 1f);
        BaseMod.addPotion(arknsfw.potions.shared.AphroDraughtPotion.class, fiveLiquid, fiveHybrid, fiveSpots, arknsfw.potions.shared.AphroDraughtPotion.ID);
        BaseMod.addPotion(arknsfw.potions.shared.HoneyDewPotion.class, fiveLiquid, fiveHybrid, fiveSpots, arknsfw.potions.shared.HoneyDewPotion.ID);
        BaseMod.addPotion(arknsfw.potions.shared.PleasureBombPotion.class, fiveLiquid, fiveHybrid, fiveSpots, arknsfw.potions.shared.PleasureBombPotion.ID);
        BaseMod.addPotion(arknsfw.potions.shared.SensitiveMistPotion.class, fiveLiquid, fiveHybrid, fiveSpots, arknsfw.potions.shared.SensitiveMistPotion.ID);
        BaseMod.addPotion(arknsfw.potions.shared.SuppressantPotion.class, fiveLiquid, fiveHybrid, fiveSpots, arknsfw.potions.shared.SuppressantPotion.ID);
        BaseMod.addPotion(arknsfw.potions.shared.HeatPerfumePotion.class, fiveLiquid, fiveHybrid, fiveSpots, arknsfw.potions.shared.HeatPerfumePotion.ID);
        BaseMod.addPotion(arknsfw.potions.shared.CorruptionEssencePotion.class, fiveLiquid, fiveHybrid, fiveSpots, arknsfw.potions.shared.CorruptionEssencePotion.ID);
        BaseMod.addPotion(arknsfw.potions.shared.CrestInkPotion.class, fiveLiquid, fiveHybrid, fiveSpots, arknsfw.potions.shared.CrestInkPotion.ID);
        BaseMod.addPotion(arknsfw.potions.shared.WombElixirPotion.class, fiveLiquid, fiveHybrid, fiveSpots, arknsfw.potions.shared.WombElixirPotion.ID);
    }

    private static void registerEvents() {
        // Eyja — normal
        BaseMod.addEvent(EyjaNormalVolcanoRestEvent.ID, EyjaNormalVolcanoRestEvent.class, Exordium.ID);
        BaseMod.addEvent(EyjaNormalFieldCampEvent.ID, EyjaNormalFieldCampEvent.class, TheCity.ID);
        BaseMod.addEvent(EyjaNormalQuietEmbraceEvent.ID, EyjaNormalQuietEmbraceEvent.class, TheBeyond.ID);
        // Eyja — shame
        BaseMod.addEvent(EyjaShamePublicSampleEvent.ID, EyjaShamePublicSampleEvent.class, Exordium.ID);
        BaseMod.addEvent(EyjaShameOpenSeminarEvent.ID, EyjaShameOpenSeminarEvent.class, TheCity.ID);
        BaseMod.addEvent(EyjaShameAuditHallEvent.ID, EyjaShameAuditHallEvent.class, TheBeyond.ID);
        // Eyja — fall
        BaseMod.addEvent(EyjaFallHeatNeedEvent.ID, EyjaFallHeatNeedEvent.class, Exordium.ID);
        BaseMod.addEvent(EyjaFallBreedingRiteEvent.ID, EyjaFallBreedingRiteEvent.class, TheCity.ID);
        BaseMod.addEvent(EyjaFallAshWombEvent.ID, EyjaFallAshWombEvent.class, TheBeyond.ID);
        // Muel — normal
        BaseMod.addEvent(MuelNormalBubbleBreakEvent.ID, MuelNormalBubbleBreakEvent.class, Exordium.ID);
        BaseMod.addEvent(MuelNormalGardenTeaEvent.ID, MuelNormalGardenTeaEvent.class, TheCity.ID);
        BaseMod.addEvent(MuelNormalStarPoolEvent.ID, MuelNormalStarPoolEvent.class, TheBeyond.ID);
        // Muel — shame
        BaseMod.addEvent(MuelShameLabAuditEvent.ID, MuelShameLabAuditEvent.class, Exordium.ID);
        BaseMod.addEvent(MuelShameLiveDemoEvent.ID, MuelShameLiveDemoEvent.class, TheCity.ID);
        BaseMod.addEvent(MuelShameBroadcastEvent.ID, MuelShameBroadcastEvent.class, TheBeyond.ID);
        // Muel — fall
        BaseMod.addEvent(MuelFallCloneNeedEvent.ID, MuelFallCloneNeedEvent.class, Exordium.ID);
        BaseMod.addEvent(MuelFallSeedGreenhouseEvent.ID, MuelFallSeedGreenhouseEvent.class, TheCity.ID);
        BaseMod.addEvent(MuelFallFluidOverflowEvent.ID, MuelFallFluidOverflowEvent.class, TheBeyond.ID);
        BaseMod.addEvent(EyjaDebuffSealEvent.ID, EyjaDebuffSealEvent.class, TheCity.ID);
        BaseMod.addEvent(MuelDebuffSealEvent.ID, MuelDebuffSealEvent.class, TheCity.ID);
        // NEW5E
        BaseMod.addEvent(SceneNormalAct1Event.ID, SceneNormalAct1Event.class, Exordium.ID);
        BaseMod.addEvent(SceneNormalAct2Event.ID, SceneNormalAct2Event.class, TheCity.ID);
        BaseMod.addEvent(SceneNormalAct3Event.ID, SceneNormalAct3Event.class, TheBeyond.ID);
        BaseMod.addEvent(SceneShameAct1Event.ID, SceneShameAct1Event.class, Exordium.ID);
        BaseMod.addEvent(SceneShameAct2Event.ID, SceneShameAct2Event.class, TheCity.ID);
        BaseMod.addEvent(SceneShameAct3Event.ID, SceneShameAct3Event.class, TheBeyond.ID);
        BaseMod.addEvent(SceneFallAct1Event.ID, SceneFallAct1Event.class, Exordium.ID);
        BaseMod.addEvent(SceneFallAct2Event.ID, SceneFallAct2Event.class, TheCity.ID);
        BaseMod.addEvent(SceneFallAct3Event.ID, SceneFallAct3Event.class, TheBeyond.ID);
        BaseMod.addEvent(SceneDebuffSealEvent.ID, SceneDebuffSealEvent.class, TheCity.ID);
        BaseMod.addEvent(HighmoreNormalAct1Event.ID, HighmoreNormalAct1Event.class, Exordium.ID);
        BaseMod.addEvent(HighmoreNormalAct2Event.ID, HighmoreNormalAct2Event.class, TheCity.ID);
        BaseMod.addEvent(HighmoreNormalAct3Event.ID, HighmoreNormalAct3Event.class, TheBeyond.ID);
        BaseMod.addEvent(HighmoreShameAct1Event.ID, HighmoreShameAct1Event.class, Exordium.ID);
        BaseMod.addEvent(HighmoreShameAct2Event.ID, HighmoreShameAct2Event.class, TheCity.ID);
        BaseMod.addEvent(HighmoreShameAct3Event.ID, HighmoreShameAct3Event.class, TheBeyond.ID);
        BaseMod.addEvent(HighmoreFallAct1Event.ID, HighmoreFallAct1Event.class, Exordium.ID);
        BaseMod.addEvent(HighmoreFallAct2Event.ID, HighmoreFallAct2Event.class, TheCity.ID);
        BaseMod.addEvent(HighmoreFallAct3Event.ID, HighmoreFallAct3Event.class, TheBeyond.ID);
        BaseMod.addEvent(HighmoreDebuffSealEvent.ID, HighmoreDebuffSealEvent.class, TheCity.ID);
        BaseMod.addEvent(ArchettoNormalAct1Event.ID, ArchettoNormalAct1Event.class, Exordium.ID);
        BaseMod.addEvent(ArchettoNormalAct2Event.ID, ArchettoNormalAct2Event.class, TheCity.ID);
        BaseMod.addEvent(ArchettoNormalAct3Event.ID, ArchettoNormalAct3Event.class, TheBeyond.ID);
        BaseMod.addEvent(ArchettoShameAct1Event.ID, ArchettoShameAct1Event.class, Exordium.ID);
        BaseMod.addEvent(ArchettoShameAct2Event.ID, ArchettoShameAct2Event.class, TheCity.ID);
        BaseMod.addEvent(ArchettoShameAct3Event.ID, ArchettoShameAct3Event.class, TheBeyond.ID);
        BaseMod.addEvent(ArchettoFallAct1Event.ID, ArchettoFallAct1Event.class, Exordium.ID);
        BaseMod.addEvent(ArchettoFallAct2Event.ID, ArchettoFallAct2Event.class, TheCity.ID);
        BaseMod.addEvent(ArchettoFallAct3Event.ID, ArchettoFallAct3Event.class, TheBeyond.ID);
        BaseMod.addEvent(ArchettoDebuffSealEvent.ID, ArchettoDebuffSealEvent.class, TheCity.ID);
        BaseMod.addEvent(HarukaNormalAct1Event.ID, HarukaNormalAct1Event.class, Exordium.ID);
        BaseMod.addEvent(HarukaNormalAct2Event.ID, HarukaNormalAct2Event.class, TheCity.ID);
        BaseMod.addEvent(HarukaNormalAct3Event.ID, HarukaNormalAct3Event.class, TheBeyond.ID);
        BaseMod.addEvent(HarukaShameAct1Event.ID, HarukaShameAct1Event.class, Exordium.ID);
        BaseMod.addEvent(HarukaShameAct2Event.ID, HarukaShameAct2Event.class, TheCity.ID);
        BaseMod.addEvent(HarukaShameAct3Event.ID, HarukaShameAct3Event.class, TheBeyond.ID);
        BaseMod.addEvent(HarukaFallAct1Event.ID, HarukaFallAct1Event.class, Exordium.ID);
        BaseMod.addEvent(HarukaFallAct2Event.ID, HarukaFallAct2Event.class, TheCity.ID);
        BaseMod.addEvent(HarukaFallAct3Event.ID, HarukaFallAct3Event.class, TheBeyond.ID);
        BaseMod.addEvent(HarukaDebuffSealEvent.ID, HarukaDebuffSealEvent.class, TheCity.ID);
        BaseMod.addEvent(NymphNormalAct1Event.ID, NymphNormalAct1Event.class, Exordium.ID);
        BaseMod.addEvent(NymphNormalAct2Event.ID, NymphNormalAct2Event.class, TheCity.ID);
        BaseMod.addEvent(NymphNormalAct3Event.ID, NymphNormalAct3Event.class, TheBeyond.ID);
        BaseMod.addEvent(NymphShameAct1Event.ID, NymphShameAct1Event.class, Exordium.ID);
        BaseMod.addEvent(NymphShameAct2Event.ID, NymphShameAct2Event.class, TheCity.ID);
        BaseMod.addEvent(NymphShameAct3Event.ID, NymphShameAct3Event.class, TheBeyond.ID);
        BaseMod.addEvent(NymphFallAct1Event.ID, NymphFallAct1Event.class, Exordium.ID);
        BaseMod.addEvent(NymphFallAct2Event.ID, NymphFallAct2Event.class, TheCity.ID);
        BaseMod.addEvent(NymphFallAct3Event.ID, NymphFallAct3Event.class, TheBeyond.ID);
        BaseMod.addEvent(NymphDebuffSealEvent.ID, NymphDebuffSealEvent.class, TheCity.ID);
    }

    @Override
    public void receiveEditCards() {
        // Eyja
        BaseMod.addCard(new FeverCaress());
        BaseMod.addCard(new VolcanicEmbrace());
        BaseMod.addCard(new HeatResonance());
        BaseMod.addCard(new AshKiss());
        BaseMod.addCard(new MagmaThrust());
        BaseMod.addCard(new LavaShield());
        BaseMod.addCard(new EruptionPeak());
        BaseMod.addCard(new BurnMark());
        BaseMod.addCard(new CoreFever());
        // Muel
        BaseMod.addCard(new BubbleTease());
        BaseMod.addCard(new CloneService());
        BaseMod.addCard(new GreenhouseMist());
        BaseMod.addCard(new WetSlide());
        BaseMod.addCard(new FluidSplash());
        BaseMod.addCard(new MoistBarrier());
        BaseMod.addCard(new TwinPleasure());
        BaseMod.addCard(new SeedSpray());
        BaseMod.addCard(new CloneHaze());
        // Curses — Eyja
        BaseMod.addCard(new WombEmberCard());
        BaseMod.addCard(new FeverContractCard());
        BaseMod.addCard(new AshCollarCard());
        BaseMod.addCard(new MagmaEchoCard());
        // Curses — Muel
        BaseMod.addCard(new CloneResidueCard());
        BaseMod.addCard(new FloodMarkCard());
        BaseMod.addCard(new BubbleMuteCard());
        BaseMod.addCard(new SeedWombCard());
        // NEW5
        BaseMod.addCard(new SceneSilentCaress());
        BaseMod.addCard(new SceneWarmEmbrace());
        BaseMod.addCard(new SceneDeepResonance());
        BaseMod.addCard(new SceneTenderKiss());
        BaseMod.addCard(new ScenePassionThrust());
        BaseMod.addCard(new SceneMoistBarrier());
        BaseMod.addCard(new SceneTwinPeak());
        BaseMod.addCard(new SceneBlushMark());
        BaseMod.addCard(new SceneCoreNeed());
        BaseMod.addCard(new SceneCalmBreath());
        BaseMod.addCard(new SceneIndulgentStrike());
        BaseMod.addCard(new SceneOfferBody());
        BaseMod.addCard(new SceneLewdTrance());
        BaseMod.addCard(new SceneShameSweat());
        BaseMod.addCard(new ScenePleasureBurst());
        BaseMod.addCard(new SceneOverflowPulse());
        BaseMod.addCard(new SceneWombMarkCard());
        BaseMod.addCard(new SceneShameContractCard());
        BaseMod.addCard(new SceneBindCollarCard());
        BaseMod.addCard(new SceneEchoSeedCard());
        BaseMod.addCard(new HighmoreSilentCaress());
        BaseMod.addCard(new HighmoreWarmEmbrace());
        BaseMod.addCard(new HighmoreDeepResonance());
        BaseMod.addCard(new HighmoreTenderKiss());
        BaseMod.addCard(new HighmorePassionThrust());
        BaseMod.addCard(new HighmoreMoistBarrier());
        BaseMod.addCard(new HighmoreTwinPeak());
        BaseMod.addCard(new HighmoreBlushMark());
        BaseMod.addCard(new HighmoreCoreNeed());
        BaseMod.addCard(new HighmoreCalmBreath());
        BaseMod.addCard(new HighmoreIndulgentStrike());
        BaseMod.addCard(new HighmoreOfferBody());
        BaseMod.addCard(new HighmoreLewdTrance());
        BaseMod.addCard(new HighmoreShameSweat());
        BaseMod.addCard(new HighmorePleasureBurst());
        BaseMod.addCard(new HighmoreOverflowPulse());
        BaseMod.addCard(new HighmoreWombMarkCard());
        BaseMod.addCard(new HighmoreShameContractCard());
        BaseMod.addCard(new HighmoreBindCollarCard());
        BaseMod.addCard(new HighmoreEchoSeedCard());
        BaseMod.addCard(new ArchettoSilentCaress());
        BaseMod.addCard(new ArchettoWarmEmbrace());
        BaseMod.addCard(new ArchettoDeepResonance());
        BaseMod.addCard(new ArchettoTenderKiss());
        BaseMod.addCard(new ArchettoPassionThrust());
        BaseMod.addCard(new ArchettoMoistBarrier());
        BaseMod.addCard(new ArchettoTwinPeak());
        BaseMod.addCard(new ArchettoBlushMark());
        BaseMod.addCard(new ArchettoCoreNeed());
        BaseMod.addCard(new ArchettoCalmBreath());
        BaseMod.addCard(new ArchettoIndulgentStrike());
        BaseMod.addCard(new ArchettoOfferBody());
        BaseMod.addCard(new ArchettoLewdTrance());
        BaseMod.addCard(new ArchettoShameSweat());
        BaseMod.addCard(new ArchettoPleasureBurst());
        BaseMod.addCard(new ArchettoOverflowPulse());
        BaseMod.addCard(new ArchettoWombMarkCard());
        BaseMod.addCard(new ArchettoShameContractCard());
        BaseMod.addCard(new ArchettoBindCollarCard());
        BaseMod.addCard(new ArchettoEchoSeedCard());
        BaseMod.addCard(new HarukaSilentCaress());
        BaseMod.addCard(new HarukaWarmEmbrace());
        BaseMod.addCard(new HarukaDeepResonance());
        BaseMod.addCard(new HarukaTenderKiss());
        BaseMod.addCard(new HarukaPassionThrust());
        BaseMod.addCard(new HarukaMoistBarrier());
        BaseMod.addCard(new HarukaTwinPeak());
        BaseMod.addCard(new HarukaBlushMark());
        BaseMod.addCard(new HarukaCoreNeed());
        BaseMod.addCard(new HarukaCalmBreath());
        BaseMod.addCard(new HarukaIndulgentStrike());
        BaseMod.addCard(new HarukaOfferBody());
        BaseMod.addCard(new HarukaLewdTrance());
        BaseMod.addCard(new HarukaShameSweat());
        BaseMod.addCard(new HarukaPleasureBurst());
        BaseMod.addCard(new HarukaOverflowPulse());
        BaseMod.addCard(new HarukaWombMarkCard());
        BaseMod.addCard(new HarukaShameContractCard());
        BaseMod.addCard(new HarukaBindCollarCard());
        BaseMod.addCard(new HarukaEchoSeedCard());
        BaseMod.addCard(new NymphSilentCaress());
        BaseMod.addCard(new NymphWarmEmbrace());
        BaseMod.addCard(new NymphDeepResonance());
        BaseMod.addCard(new NymphTenderKiss());
        BaseMod.addCard(new NymphPassionThrust());
        BaseMod.addCard(new NymphMoistBarrier());
        BaseMod.addCard(new NymphTwinPeak());
        BaseMod.addCard(new NymphBlushMark());
        BaseMod.addCard(new NymphCoreNeed());
        BaseMod.addCard(new NymphCalmBreath());
        BaseMod.addCard(new NymphIndulgentStrike());
        BaseMod.addCard(new NymphOfferBody());
        BaseMod.addCard(new NymphLewdTrance());
        BaseMod.addCard(new NymphShameSweat());
        BaseMod.addCard(new NymphPleasureBurst());
        BaseMod.addCard(new NymphOverflowPulse());
        BaseMod.addCard(new NymphWombMarkCard());
        BaseMod.addCard(new NymphShameContractCard());
        BaseMod.addCard(new NymphBindCollarCard());
        BaseMod.addCard(new NymphEchoSeedCard());
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new ThermometerCharmRelic(), ColorEnum.Eyjafjalla_COLOR);
        BaseMod.addRelicToCustomPool(new WoolHeatRelic(), ColorEnum.Eyjafjalla_COLOR);
        BaseMod.addRelicToCustomPool(new HeatStickerRelic(), ColorEnum.Eyjafjalla_COLOR);
        BaseMod.addRelicToCustomPool(new LavaPlugRelic(), ColorEnum.Eyjafjalla_COLOR);
        BaseMod.addRelicToCustomPool(new AshCollarRelic(), ColorEnum.Eyjafjalla_COLOR);
        BaseMod.addRelicToCustomPool(new EmberSeedRelic(), ColorEnum.Eyjafjalla_COLOR);
        BaseMod.addRelicToCustomPool(new RhineGelRelic(), Muelsyse.patches.ColorEnum.Muelsyse_COLOR);
        BaseMod.addRelicToCustomPool(new DuplicateMirrorRelic(), Muelsyse.patches.ColorEnum.Muelsyse_COLOR);
        BaseMod.addRelicToCustomPool(new BubbleWandRelic(), Muelsyse.patches.ColorEnum.Muelsyse_COLOR);
        BaseMod.addRelicToCustomPool(new CloneTagRelic(), Muelsyse.patches.ColorEnum.Muelsyse_COLOR);
        BaseMod.addRelicToCustomPool(new RootVineRelic(), Muelsyse.patches.ColorEnum.Muelsyse_COLOR);
        BaseMod.addRelicToCustomPool(new OverflowFlaskRelic(), Muelsyse.patches.ColorEnum.Muelsyse_COLOR);
        // Curse relics (event-only, SPECIAL)
        BaseMod.addRelic(new VolcanoBrandCurseRelic(), RelicType.SHARED);
        BaseMod.addRelic(new AshFurnaceCurseRelic(), RelicType.SHARED);
        BaseMod.addRelic(new BreedingAltarCurseRelic(), RelicType.SHARED);
        BaseMod.addRelic(new CloneLoopCurseRelic(), RelicType.SHARED);
        BaseMod.addRelic(new RhineFilthCurseRelic(), RelicType.SHARED);
        BaseMod.addRelic(new OverflowCoreCurseRelic(), RelicType.SHARED);
        BaseMod.addRelic(new EyjaPregnancyMarkRelic(), RelicType.SHARED);
        BaseMod.addRelic(new MuelPregnancyMarkRelic(), RelicType.SHARED);
        BaseMod.addRelic(new EyjaPregnancyDeliveryRelic(), RelicType.SHARED);
        BaseMod.addRelic(new arknsfw.relics.highmore.HighmorePregnancyMarkRelic(), RelicType.SHARED);
        BaseMod.addRelic(new arknsfw.relics.highmore.HighmorePregnancyDeliveryRelic(), RelicType.SHARED);
        BaseMod.addRelic(new arknsfw.relics.scene.ScenePregnancyMarkRelic(), RelicType.SHARED);
        BaseMod.addRelic(new arknsfw.relics.scene.ScenePregnancyDeliveryRelic(), RelicType.SHARED);
        BaseMod.addRelic(new arknsfw.relics.archetto.ArchettoPregnancyMarkRelic(), RelicType.SHARED);
        BaseMod.addRelic(new arknsfw.relics.archetto.ArchettoPregnancyDeliveryRelic(), RelicType.SHARED);
        BaseMod.addRelic(new arknsfw.relics.haruka.HarukaPregnancyMarkRelic(), RelicType.SHARED);
        BaseMod.addRelic(new arknsfw.relics.haruka.HarukaPregnancyDeliveryRelic(), RelicType.SHARED);
        BaseMod.addRelic(new arknsfw.relics.nymph.NymphPregnancyMarkRelic(), RelicType.SHARED);
        BaseMod.addRelic(new arknsfw.relics.nymph.NymphPregnancyDeliveryRelic(), RelicType.SHARED);
        BaseMod.addRelic(new MuelPregnancyDeliveryRelic(), RelicType.SHARED);
        // NEW5R
        BaseMod.addRelicToCustomPool(new SceneWarmCharmRelic(), scene.core.ColorEnum.SCENE_COLOR);
        BaseMod.addRelicToCustomPool(new SceneBlushStickerRelic(), scene.core.ColorEnum.SCENE_COLOR);
        BaseMod.addRelicToCustomPool(new SceneSoftCollarRelic(), scene.core.ColorEnum.SCENE_COLOR);
        BaseMod.addRelicToCustomPool(new ScenePulsePlugRelic(), scene.core.ColorEnum.SCENE_COLOR);
        BaseMod.addRelicToCustomPool(new SceneTwinMirrorRelic(), scene.core.ColorEnum.SCENE_COLOR);
        BaseMod.addRelicToCustomPool(new SceneMoistFlaskRelic(), scene.core.ColorEnum.SCENE_COLOR);
        BaseMod.addRelicToCustomPool(new SceneOverflowCoreRelic(), scene.core.ColorEnum.SCENE_COLOR);
        BaseMod.addRelic(new SceneBrandCurseRelic(), RelicType.SHARED);
        BaseMod.addRelic(new SceneAltarCurseRelic(), RelicType.SHARED);
        BaseMod.addRelic(new SceneLoopCurseRelic(), RelicType.SHARED);
        BaseMod.addRelicToCustomPool(new HighmoreWarmCharmRelic(), highmore.core.ColorEnum.HIGHMORE_COLOR);
        BaseMod.addRelicToCustomPool(new HighmoreBlushStickerRelic(), highmore.core.ColorEnum.HIGHMORE_COLOR);
        BaseMod.addRelicToCustomPool(new HighmoreSoftCollarRelic(), highmore.core.ColorEnum.HIGHMORE_COLOR);
        BaseMod.addRelicToCustomPool(new HighmorePulsePlugRelic(), highmore.core.ColorEnum.HIGHMORE_COLOR);
        BaseMod.addRelicToCustomPool(new HighmoreTwinMirrorRelic(), highmore.core.ColorEnum.HIGHMORE_COLOR);
        BaseMod.addRelicToCustomPool(new HighmoreMoistFlaskRelic(), highmore.core.ColorEnum.HIGHMORE_COLOR);
        BaseMod.addRelicToCustomPool(new HighmoreOverflowCoreRelic(), highmore.core.ColorEnum.HIGHMORE_COLOR);
        BaseMod.addRelic(new HighmoreBrandCurseRelic(), RelicType.SHARED);
        BaseMod.addRelic(new HighmoreAltarCurseRelic(), RelicType.SHARED);
        BaseMod.addRelic(new HighmoreLoopCurseRelic(), RelicType.SHARED);
        BaseMod.addRelicToCustomPool(new ArchettoWarmCharmRelic(), archetto.core.ColorEnum.ARCHETTO_COLOR);
        BaseMod.addRelicToCustomPool(new ArchettoBlushStickerRelic(), archetto.core.ColorEnum.ARCHETTO_COLOR);
        BaseMod.addRelicToCustomPool(new ArchettoSoftCollarRelic(), archetto.core.ColorEnum.ARCHETTO_COLOR);
        BaseMod.addRelicToCustomPool(new ArchettoPulsePlugRelic(), archetto.core.ColorEnum.ARCHETTO_COLOR);
        BaseMod.addRelicToCustomPool(new ArchettoTwinMirrorRelic(), archetto.core.ColorEnum.ARCHETTO_COLOR);
        BaseMod.addRelicToCustomPool(new ArchettoMoistFlaskRelic(), archetto.core.ColorEnum.ARCHETTO_COLOR);
        BaseMod.addRelicToCustomPool(new ArchettoOverflowCoreRelic(), archetto.core.ColorEnum.ARCHETTO_COLOR);
        BaseMod.addRelic(new ArchettoBrandCurseRelic(), RelicType.SHARED);
        BaseMod.addRelic(new ArchettoAltarCurseRelic(), RelicType.SHARED);
        BaseMod.addRelic(new ArchettoLoopCurseRelic(), RelicType.SHARED);
        BaseMod.addRelicToCustomPool(new HarukaWarmCharmRelic(), haruka.core.ColorEnum.HARUKA_COLOR);
        BaseMod.addRelicToCustomPool(new HarukaBlushStickerRelic(), haruka.core.ColorEnum.HARUKA_COLOR);
        BaseMod.addRelicToCustomPool(new HarukaSoftCollarRelic(), haruka.core.ColorEnum.HARUKA_COLOR);
        BaseMod.addRelicToCustomPool(new HarukaPulsePlugRelic(), haruka.core.ColorEnum.HARUKA_COLOR);
        BaseMod.addRelicToCustomPool(new HarukaTwinMirrorRelic(), haruka.core.ColorEnum.HARUKA_COLOR);
        BaseMod.addRelicToCustomPool(new HarukaMoistFlaskRelic(), haruka.core.ColorEnum.HARUKA_COLOR);
        BaseMod.addRelicToCustomPool(new HarukaOverflowCoreRelic(), haruka.core.ColorEnum.HARUKA_COLOR);
        BaseMod.addRelic(new HarukaBrandCurseRelic(), RelicType.SHARED);
        BaseMod.addRelic(new HarukaAltarCurseRelic(), RelicType.SHARED);
        BaseMod.addRelic(new HarukaLoopCurseRelic(), RelicType.SHARED);
        BaseMod.addRelicToCustomPool(new NymphWarmCharmRelic(), nymph.core.ColorEnum.NYMPH_COLOR);
        BaseMod.addRelicToCustomPool(new NymphBlushStickerRelic(), nymph.core.ColorEnum.NYMPH_COLOR);
        BaseMod.addRelicToCustomPool(new NymphSoftCollarRelic(), nymph.core.ColorEnum.NYMPH_COLOR);
        BaseMod.addRelicToCustomPool(new NymphPulsePlugRelic(), nymph.core.ColorEnum.NYMPH_COLOR);
        BaseMod.addRelicToCustomPool(new NymphTwinMirrorRelic(), nymph.core.ColorEnum.NYMPH_COLOR);
        BaseMod.addRelicToCustomPool(new NymphMoistFlaskRelic(), nymph.core.ColorEnum.NYMPH_COLOR);
        BaseMod.addRelicToCustomPool(new NymphOverflowCoreRelic(), nymph.core.ColorEnum.NYMPH_COLOR);
        BaseMod.addRelic(new NymphBrandCurseRelic(), RelicType.SHARED);
        BaseMod.addRelic(new NymphAltarCurseRelic(), RelicType.SHARED);
        BaseMod.addRelic(new NymphLoopCurseRelic(), RelicType.SHARED);
    }

    @Override
    public void receiveEditKeywords() {
        BaseMod.addKeyword(modID, "地热", new String[]{"地热"}, "艾雅法拉 NSFW 线：与兴奋联动的火山余热。");
        BaseMod.addKeyword(modID, "水化", new String[]{"水化"}, "缪尔赛思 NSFW 线：湿润/分身相关增益。");
        BaseMod.addKeyword(modID, "核心高热", new String[]{"核心高热"}, "受击后兴奋上升。");
        BaseMod.addKeyword(modID, "分身雾", new String[]{"分身雾"}, "回合开始兴奋与受孕度上升。");
    }

    @Override
    public void receiveEditStrings() {
        String base = RESOURCES + "/localization/zhs/";
        BaseMod.loadCustomStringsFile(CardStrings.class, base + "CardStrings.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, base + "RelicStrings.json");
        BaseMod.loadCustomStringsFile(EventStrings.class, base + "EventStrings.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, base + "PowerStrings.json");
        BaseMod.loadCustomStringsFile(PotionStrings.class, base + "PotionStrings.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, base + "UIStrings.json");
    }

    @Override
    public void receivePostBattle(com.megacrit.cardcrawl.rooms.AbstractRoom battleRoom) {
        arknsfw.helpers.ArkPostBattleChoice.onBattleEnd(battleRoom);
    }

    @Override
    public void receivePostUpdate() {
        arknsfw.helpers.ArkPostBattleChoice.update();
    }

    @Override
    public void receiveRender(com.badlogic.gdx.graphics.g2d.SpriteBatch sb) {
        arknsfw.helpers.ArkPortraitPanel.render(sb);
        arknsfw.helpers.ArkPostBattleChoice.render(sb);
    }

    @Override
    public void receiveOnPlayerTurnStart() {
        arknsfw.helpers.ArkClimaxHelper.onPlayerTurnStart();
    }

    @Override
    public int receiveOnPlayerLoseHp(int damageAmount) {
        arknsfw.helpers.ArkExposureHelper.onPlayerLoseHp(damageAmount);
        return damageAmount;
    }
}
