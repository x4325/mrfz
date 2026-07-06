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
}
