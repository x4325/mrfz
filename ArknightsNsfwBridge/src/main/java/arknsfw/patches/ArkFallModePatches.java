package arknsfw.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.shop.ShopScreen;
import liesecore.helpers.EnemyIntentType;
import liesecore.helpers.UiHelper;
import arknsfw.events.ArkFallOfferingEvent;
import arknsfw.helpers.ArkCharacterSetup;
import arknsfw.helpers.ArkFallMode;
import arknsfw.helpers.ArkFiveIntentHandler;
import arknsfw.helpers.ArkSpecialIntentHandler;

import java.util.ArrayList;

/** 堕落模式全套改造：卡池 / 遗物池 / 药水池 / 事件 / 商店 / 敌人调教追击。 */
public class ArkFallModePatches {

    // ================= 卡牌获取：大概率替换为色情卡 =================

    @SpirePatch(clz = AbstractDungeon.class, method = "getCard",
            paramtypez = {AbstractCard.CardRarity.class})
    public static class FallCardBias1 {
        @SpirePrefixPatch
        public static SpireReturn<AbstractCard> prefix(AbstractCard.CardRarity rarity) {
            return roll(rarity, AbstractDungeon.cardRng);
        }
    }

    @SpirePatch(clz = AbstractDungeon.class, method = "getCard",
            paramtypez = {AbstractCard.CardRarity.class, Random.class})
    public static class FallCardBias2 {
        @SpirePrefixPatch
        public static SpireReturn<AbstractCard> prefix(AbstractCard.CardRarity rarity, Random rng) {
            return roll(rarity, rng);
        }
    }

    private static SpireReturn<AbstractCard> roll(AbstractCard.CardRarity rarity, Random rng) {
        if (!ArkFallMode.active() || rng == null) {
            return SpireReturn.Continue();
        }
        if (rarity == AbstractCard.CardRarity.CURSE || rarity == AbstractCard.CardRarity.SPECIAL) {
            return SpireReturn.Continue();
        }
        if (!rng.randomBoolean(ArkFallMode.CARD_BIAS)) {
            return SpireReturn.Continue();
        }
        AbstractCard pick = ArkFallMode.randomNsfwCard(rarity, rng);
        if (pick == null) {
            return SpireReturn.Continue();
        }
        return SpireReturn.Return(pick);
    }

    // ================= 遗物：全替换为色情遗物 =================

    @SpirePatch(clz = AbstractDungeon.class, method = "returnRandomRelic",
            paramtypez = {AbstractRelic.RelicTier.class})
    public static class FallRelicPool {
        @SpirePrefixPatch
        public static SpireReturn<AbstractRelic> prefix(AbstractRelic.RelicTier tier) {
            if (!ArkFallMode.active()) {
                return SpireReturn.Continue();
            }
            AbstractRelic pick = ArkFallMode.randomNsfwRelic(AbstractDungeon.relicRng, false);
            if (pick == null) {
                return SpireReturn.Continue();
            }
            return SpireReturn.Return(pick);
        }
    }

    @SpirePatch(clz = AbstractDungeon.class, method = "returnRandomScreenlessRelic",
            paramtypez = {AbstractRelic.RelicTier.class})
    public static class FallRelicPoolScreenless {
        @SpirePrefixPatch
        public static SpireReturn<AbstractRelic> prefix(AbstractRelic.RelicTier tier) {
            if (!ArkFallMode.active()) {
                return SpireReturn.Continue();
            }
            AbstractRelic pick = ArkFallMode.randomNsfwRelic(AbstractDungeon.relicRng, false);
            if (pick == null) {
                return SpireReturn.Continue();
            }
            return SpireReturn.Return(pick);
        }
    }

    // ================= 药水：全替换为色情药水 =================

    @SpirePatch(clz = AbstractDungeon.class, method = "returnRandomPotion",
            paramtypez = {AbstractPotion.PotionRarity.class, boolean.class})
    public static class FallPotionPool {
        @SpirePrefixPatch
        public static SpireReturn<AbstractPotion> prefix(AbstractPotion.PotionRarity rarity, boolean limited) {
            if (!ArkFallMode.active()) {
                return SpireReturn.Continue();
            }
            AbstractPotion pick = ArkFallMode.randomNsfwPotion(AbstractDungeon.potionRng);
            if (pick == null) {
                return SpireReturn.Continue();
            }
            return SpireReturn.Return(pick);
        }
    }

    // ================= 事件：神龛也替换为堕落供物 =================

    @SpirePatch(clz = AbstractDungeon.class, method = "getShrine", paramtypez = {Random.class})
    public static class FallShrine {
        @SpirePrefixPatch
        public static SpireReturn<AbstractEvent> prefix(Random rng) {
            if (!ArkFallMode.active()) {
                return SpireReturn.Continue();
            }
            return SpireReturn.Return(new ArkFallOfferingEvent());
        }
    }

    // ================= 商店：只卖色情卡 =================

    @SpirePatch(clz = ShopScreen.class, method = "init")
    public static class FallShopCards {
        @SpirePrefixPatch
        public static void prefix(ShopScreen screen,
                                  ArrayList<AbstractCard> coloredCards,
                                  ArrayList<AbstractCard> colorlessCards) {
            if (!ArkFallMode.active()) {
                return;
            }
            replaceWithNsfw(coloredCards);
            replaceWithNsfw(colorlessCards);
        }

        private static void replaceWithNsfw(ArrayList<AbstractCard> cards) {
            if (cards == null) {
                return;
            }
            ArrayList<String> used = new ArrayList<>();
            for (int i = 0; i < cards.size(); i++) {
                AbstractCard old = cards.get(i);
                AbstractCard pick = null;
                for (int attempt = 0; attempt < 12; attempt++) {
                    AbstractCard c = ArkFallMode.randomNsfwCard(
                            old != null ? old.rarity : AbstractCard.CardRarity.COMMON,
                            AbstractDungeon.merchantRng);
                    if (c != null && !used.contains(c.cardID)) {
                        pick = c;
                        break;
                    }
                }
                if (pick != null) {
                    used.add(pick.cardID);
                    cards.set(i, pick.makeCopy());
                }
            }
        }
    }

    // ================= 敌人：每回合概率追加调教攻击 =================

    private static final EnemyIntentType[] H_TYPES = {
            EnemyIntentType.HARASS, EnemyIntentType.BIND, EnemyIntentType.TEASE,
            EnemyIntentType.HUMILIATE, EnemyIntentType.INJECT,
    };

    /** 回合开始时结算（由 ArkNsfwMod.receiveOnPlayerTurnStart 调用）：
     *  每个存活敌人有 H_ATTACK_CHANCE 概率对玩家追加一次调教攻击。 */
    public static void rollHAttacks() {
        if (!ArkFallMode.active() || AbstractDungeon.player == null
                || AbstractDungeon.player.isDead || AbstractDungeon.aiRng == null) {
            return;
        }
        AbstractRoom room = AbstractDungeon.getCurrRoom();
        if (room == null || room.phase != AbstractRoom.RoomPhase.COMBAT
                || AbstractDungeon.getMonsters() == null) {
            return;
        }
        // 第一回合不追击，给玩家喘息
        if (AbstractDungeon.actionManager == null || AbstractDungeon.actionManager.turn <= 1) {
            return;
        }
        boolean any = false;
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (monster == null || monster.isDying || monster.isEscaping || monster.isDeadOrEscaped()) {
                continue;
            }
            if (!AbstractDungeon.aiRng.randomBoolean(ArkFallMode.H_ATTACK_CHANCE)) {
                continue;
            }
            EnemyIntentType type = H_TYPES[AbstractDungeon.aiRng.random(H_TYPES.length - 1)];
            try {
                if (ArkCharacterSetup.isEyjaRun()) {
                    ArkSpecialIntentHandler.EYJA.execute(monster, type);
                } else if (ArkCharacterSetup.isMuelsyseRun()) {
                    ArkSpecialIntentHandler.MUEL.execute(monster, type);
                } else {
                    ArkFiveIntentHandler.INSTANCE.execute(monster, type);
                }
                any = true;
            } catch (Exception ignored) {
            }
        }
        if (any) {
            UiHelper.showCenterText(AbstractDungeon.player, "调教追击！");
        }
    }
}
