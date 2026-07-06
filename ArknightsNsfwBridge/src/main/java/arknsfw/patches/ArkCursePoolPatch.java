package arknsfw.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import liesecore.helpers.CurseHelper;
import arknsfw.helpers.ArkCharacterSetup;
import arknsfw.helpers.ArkCurseHelper;

public class ArkCursePoolPatch {

    @SpirePatch(clz = CurseHelper.class, method = "addRandomCurseToDeck")
    public static class RouteRandomCurse {
        @SpirePrefixPatch
        public static SpireReturn Prefix() {
            if (ArkCharacterSetup.isArkNsfwRun()) {
                ArkCurseHelper.addRandomCurseForCurrentCharacter();
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = CurseHelper.class, method = "randomUnownedCurseRelic")
    public static class RouteRandomCurseRelic {
        @SpirePrefixPatch
        public static SpireReturn<com.megacrit.cardcrawl.relics.AbstractRelic> Prefix() {
            if (ArkCharacterSetup.isArkNsfwRun()) {
                return SpireReturn.Return(ArkCurseHelper.randomUnownedCurseRelicForCurrentCharacter());
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = CurseHelper.class, method = "randomCurse")
    public static class RouteRandomCurseCard {
        @SpirePrefixPatch
        public static SpireReturn<com.megacrit.cardcrawl.cards.AbstractCard> Prefix() {
            if (ArkCharacterSetup.isArkNsfwRun()) {
                com.megacrit.cardcrawl.cards.AbstractCard card = ArkCurseHelper.randomCurseForCurrentCharacter();
                if (card != null) {
                    return SpireReturn.Return(card);
                }
            }
            return SpireReturn.Continue();
        }
    }
}
