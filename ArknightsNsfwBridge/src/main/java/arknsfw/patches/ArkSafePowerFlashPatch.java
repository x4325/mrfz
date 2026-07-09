package arknsfw.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.powers.AbstractPower;

/** 无图标或 owner 无 hitbox 时跳过 flash，避免 FlashPowerEffect NPE。 */
@SpirePatch(clz = AbstractPower.class, method = "flash")
public class ArkSafePowerFlashPatch {

    @SpirePrefixPatch
    public static SpireReturn<Void> Prefix(AbstractPower __instance) {
        if (__instance.img == null && __instance.region128 == null) {
            return SpireReturn.Return(null);
        }
        if (__instance.owner != null
                && !__instance.owner.isDeadOrEscaped()
                && __instance.owner.hb == null) {
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}
