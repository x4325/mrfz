package arknsfw.helpers;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 堕落模式：开局在 Mods 面板勾选。
 * 该模式下——卡牌获取大概率替换为色情卡；遗物/药水池整体替换为色情池；
 * 事件全部替换为「堕落供物」固定三选一；敌人回合后追加调教攻击。
 */
public final class ArkFallMode {

    /** 卡牌奖励被替换为色情卡的概率 */
    public static final float CARD_BIAS = 0.65f;
    /** 敌人每次行动后追加调教攻击的概率 */
    public static final float H_ATTACK_CHANCE = 0.40f;

    private static final String CONFIG_KEY = "fallMode";
    private static boolean enabled = false;
    private static SpireConfig config;

    private ArkFallMode() {
    }

    public static void loadConfig() {
        try {
            config = new SpireConfig("arknsfw", "arknsfwConfig");
            enabled = config.has(CONFIG_KEY) && config.getBool(CONFIG_KEY);
        } catch (Exception e) {
            enabled = false;
        }
    }

    public static boolean enabled() {
        return enabled;
    }

    public static void setEnabled(boolean value) {
        enabled = value;
        try {
            if (config != null) {
                config.setBool(CONFIG_KEY, value);
                config.save();
            }
        } catch (Exception ignored) {
        }
    }

    /** 堕落模式生效：勾选 + 当前是七名支持角色之一的 run。 */
    public static boolean active() {
        return enabled && ArkCharacterSetup.isArkNsfwRun();
    }

    public static String charKey() {
        if (ArkCharacterSetup.isEyjaRun()) return "eyja";
        if (ArkCharacterSetup.isMuelsyseRun()) return "muel";
        return ArkCharDebuffs.currentCharKey();
    }

    // ================= 色情卡池 =================

    private static final Map<AbstractCard.CardColor, ArrayList<AbstractCard>> CARD_CACHE = new HashMap<>();

    private static ArrayList<AbstractCard> nsfwCardsForPlayer() {
        if (AbstractDungeon.player == null) {
            return new ArrayList<>();
        }
        AbstractCard.CardColor color = AbstractDungeon.player.getCardColor();
        ArrayList<AbstractCard> cached = CARD_CACHE.get(color);
        if (cached != null) {
            return cached;
        }
        ArrayList<AbstractCard> out = new ArrayList<>();
        for (Map.Entry<String, AbstractCard> e : CardLibrary.cards.entrySet()) {
            AbstractCard c = e.getValue();
            if (c.cardID.startsWith("arknsfw:") && c.color == color
                    && c.type != AbstractCard.CardType.CURSE
                    && c.rarity != AbstractCard.CardRarity.SPECIAL) {
                out.add(c);
            }
        }
        CARD_CACHE.put(color, out);
        return out;
    }

    public static AbstractCard randomNsfwCard(AbstractCard.CardRarity rarity, Random rng) {
        ArrayList<AbstractCard> all = nsfwCardsForPlayer();
        if (all.isEmpty()) {
            return null;
        }
        ArrayList<AbstractCard> match = new ArrayList<>();
        for (AbstractCard c : all) {
            if (c.rarity == rarity) {
                match.add(c);
            }
        }
        ArrayList<AbstractCard> pool = match.isEmpty() ? all : match;
        if (rng != null) {
            return pool.get(rng.random(pool.size() - 1));
        }
        return pool.get(AbstractDungeon.cardRandomRng.random(pool.size() - 1));
    }

    // ================= 色情遗物池 =================

    /** 拘束装备（七角色通用） */
    public static final String[] EQUIPMENT_IDS = {
            "arknsfw:RestraintCuffsRelic", "arknsfw:LeashRelic", "arknsfw:ChastityBeltRelic",
            "arknsfw:LaceGarterRelic", "arknsfw:BellTagRelic", "arknsfw:ClothGagRelic",
            "arknsfw:RingGagRelic", "arknsfw:LaceBlindfoldRelic", "arknsfw:VibeEggRelic",
            "arknsfw:BodyCrestRelic", "arknsfw:RopeBindRelic",
    };

    /** 欲望装备（新增，七角色通用） */
    public static final String[] GEAR_IDS = {
            "arknsfw:ExposureCloakRelic", "arknsfw:PleasureConverterRelic",
            "arknsfw:TrainingCollarPlusRelic", "arknsfw:RemoteVibeRelic",
            "arknsfw:CorruptHourglassRelic", "arknsfw:CrestAmpRingRelic",
    };

    /** 祝福（色情状态，事件三选一的第三项） */
    public static final String[] BLESSING_IDS = {
            "arknsfw:LustSurgeCharmRelic", "arknsfw:HeatAdaptBadgeRelic",
            "arknsfw:SageTimeWatchRelic", "arknsfw:CrestResonancePendantRelic",
            "arknsfw:MotherGlowBroochRelic",
    };

    private static final Map<String, String[]> CHAR_RELICS = new HashMap<>();
    static {
        CHAR_RELICS.put("eyja", new String[]{
                "arknsfw:ThermometerCharmRelic", "arknsfw:WoolHeatRelic", "arknsfw:HeatStickerRelic",
                "arknsfw:LavaPlugRelic", "arknsfw:AshCollarRelic", "arknsfw:EmberSeedRelic"});
        CHAR_RELICS.put("muel", new String[]{
                "arknsfw:RhineGelRelic", "arknsfw:DuplicateMirrorRelic", "arknsfw:BubbleWandRelic",
                "arknsfw:CloneTagRelic", "arknsfw:RootVineRelic", "arknsfw:OverflowFlaskRelic"});
        for (String k : new String[]{"highmore", "scene", "archetto", "haruka", "nymph"}) {
            String p = Character.toUpperCase(k.charAt(0)) + k.substring(1);
            CHAR_RELICS.put(k, new String[]{
                    "arknsfw:" + p + "WarmCharmRelic", "arknsfw:" + p + "BlushStickerRelic",
                    "arknsfw:" + p + "SoftCollarRelic", "arknsfw:" + p + "PulsePlugRelic",
                    "arknsfw:" + p + "TwinMirrorRelic", "arknsfw:" + p + "MoistFlaskRelic",
                    "arknsfw:" + p + "OverflowCoreRelic"});
        }
    }

    private static ArrayList<String> relicPoolIds(boolean includeBlessings) {
        ArrayList<String> ids = new ArrayList<>();
        for (String id : EQUIPMENT_IDS) ids.add(id);
        for (String id : GEAR_IDS) ids.add(id);
        String key = charKey();
        if (key != null && CHAR_RELICS.containsKey(key)) {
            for (String id : CHAR_RELICS.get(key)) ids.add(id);
        }
        if (includeBlessings) {
            for (String id : BLESSING_IDS) ids.add(id);
        }
        return ids;
    }

    /** 随机取一件未持有的色情遗物；全部拥有时返回 null。 */
    public static AbstractRelic randomNsfwRelic(Random rng, boolean includeBlessings) {
        ArrayList<String> ids = relicPoolIds(includeBlessings);
        ArrayList<String> missing = new ArrayList<>();
        for (String id : ids) {
            if (AbstractDungeon.player != null && !AbstractDungeon.player.hasRelic(id)
                    && RelicLibrary.getRelic(id) != null) {
                missing.add(id);
            }
        }
        if (missing.isEmpty()) {
            return null;
        }
        String pick = rng != null
                ? missing.get(rng.random(missing.size() - 1))
                : missing.get(AbstractDungeon.relicRng.random(missing.size() - 1));
        return RelicLibrary.getRelic(pick).makeCopy();
    }

    /** 随机取一件未持有的祝福遗物。 */
    public static AbstractRelic randomBlessing(Random rng) {
        ArrayList<String> missing = new ArrayList<>();
        for (String id : BLESSING_IDS) {
            if (AbstractDungeon.player != null && !AbstractDungeon.player.hasRelic(id)
                    && RelicLibrary.getRelic(id) != null) {
                missing.add(id);
            }
        }
        if (missing.isEmpty()) {
            return null;
        }
        String pick = rng != null
                ? missing.get(rng.random(missing.size() - 1))
                : missing.get(AbstractDungeon.relicRng.random(missing.size() - 1));
        return RelicLibrary.getRelic(pick).makeCopy();
    }

    // ================= 色情药水池 =================

    private static final Map<String, Class<?>[]> CHAR_POTIONS = new HashMap<>();
    private static final Class<?>[] SHARED_POTIONS = {
            arknsfw.potions.shared.AphroDraughtPotion.class,
            arknsfw.potions.shared.HoneyDewPotion.class,
            arknsfw.potions.shared.PleasureBombPotion.class,
            arknsfw.potions.shared.SensitiveMistPotion.class,
            arknsfw.potions.shared.SuppressantPotion.class,
            arknsfw.potions.shared.HeatPerfumePotion.class,
            arknsfw.potions.shared.CorruptionEssencePotion.class,
            arknsfw.potions.shared.CrestInkPotion.class,
            arknsfw.potions.shared.WombElixirPotion.class,
            arknsfw.potions.shared.ClimaxTriggerPotion.class,
            arknsfw.potions.shared.LubricantPotion.class,
            arknsfw.potions.shared.MotherNectarPotion.class,
            liesecore.potions.ClimaxDraughtPotion.class,
    };
    static {
        CHAR_POTIONS.put("eyja", new Class<?>[]{
                arknsfw.potions.eyja.CloudWarmTonicPotion.class,
                arknsfw.potions.eyja.PyroAphroPotion.class,
                arknsfw.potions.eyja.VolcanicNectarPotion.class,
                arknsfw.potions.eyja.AshDregPotion.class,
                arknsfw.potions.eyja.FeverSedimentPotion.class,
                arknsfw.potions.eyja.EmberLockPotion.class,
                arknsfw.potions.eyja.HeatLingerPotion.class,
                arknsfw.potions.eyja.EmberDraughtPotion.class,
                arknsfw.potions.eyja.LavaBloomPotion.class,
        });
        CHAR_POTIONS.put("muel", new Class<?>[]{
                arknsfw.potions.muel.BubbleSerumPotion.class,
                arknsfw.potions.muel.CloneDripPotion.class,
                arknsfw.potions.muel.RootDewPotion.class,
                arknsfw.potions.muel.FloodWastePotion.class,
                arknsfw.potions.muel.SeedSludgePotion.class,
                arknsfw.potions.muel.MuteFoamPotion.class,
                arknsfw.potions.muel.MistSprayPotion.class,
                arknsfw.potions.muel.TwinSapPotion.class,
                arknsfw.potions.muel.GreenhouseNectarPotion.class,
        });
    }

    public static AbstractPotion randomNsfwPotion(Random rng) {
        ArrayList<Class<?>> pool = new ArrayList<>();
        for (Class<?> c : SHARED_POTIONS) pool.add(c);
        String key = charKey();
        if (key != null && CHAR_POTIONS.containsKey(key)) {
            for (Class<?> c : CHAR_POTIONS.get(key)) pool.add(c);
        }
        if (pool.isEmpty()) {
            return null;
        }
        int idx = rng != null
                ? rng.random(pool.size() - 1)
                : AbstractDungeon.potionRng.random(pool.size() - 1);
        try {
            return (AbstractPotion) pool.get(idx).newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
