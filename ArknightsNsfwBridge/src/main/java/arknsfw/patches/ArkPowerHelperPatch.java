package arknsfw.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import liesecore.helpers.PowerHelper;
import arknsfw.helpers.ArkDebuffHelper;

public class ArkPowerHelperPatch {

    @SpirePatch(clz = PowerHelper.class, method = "remove", paramtypez = {AbstractPlayer.class, String.class})
    public static class BlockLockedRemove {
        @SpirePrefixPatch
        public static SpireReturn Prefix(AbstractPlayer player, String powerId) {
            if (ArkDebuffHelper.isLocked(powerId)) {
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
