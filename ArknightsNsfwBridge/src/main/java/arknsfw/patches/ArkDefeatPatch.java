package arknsfw.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import arknsfw.helpers.ArkDefeatHelper;

/** 战败拦截：致死伤害后触发凌辱线复活（每局一次）。 */
public class ArkDefeatPatch {

    @SpirePatch(clz = AbstractPlayer.class, method = "damage", paramtypez = {DamageInfo.class})
    public static class RescueOnLethal {
        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer __instance, DamageInfo info) {
            if (__instance.isDead || __instance.currentHealth <= 0) {
                ArkDefeatHelper.tryRescue(__instance);
            }
        }
    }

    /** 掉血累积（衣装破损进度）：替代 BaseMod 不存在的 OnPlayerLoseHpSubscriber。 */
    @SpirePatch(clz = AbstractPlayer.class, method = "damage", paramtypez = {DamageInfo.class})
    public static class TrackHpLoss {
        @com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch
        public static void Prefix(AbstractPlayer __instance, DamageInfo info) {
            lastHp = __instance.currentHealth;
        }

        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer __instance, DamageInfo info) {
            int lost = lastHp - __instance.currentHealth;
            if (lost > 0) {
                arknsfw.helpers.ArkExposureHelper.onPlayerLoseHp(lost);
            }
        }

        private static int lastHp = 0;
    }
}
