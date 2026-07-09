package arknsfw.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import liesecore.powers.ShamePower;
import arknsfw.powers.eyja.AshShamePower;
import arknsfw.powers.muel.BubbleGagPower;
import arknsfw.powers.archetto.TremblingGripPower;

/** 并入 liesecore 丧威附加费计算，避免重复 patch 卡牌费用。 */
public class ArkDebuffCostPatch {

    @SpirePatch(clz = ShamePower.class, method = "getExtraCost", paramtypez = {AbstractCard.class})
    public static class MergeArkDebuffCost {
        @SpirePostfixPatch
        public static int Postfix(int __result, AbstractCard card) {
            return __result + AshShamePower.getExtraCost(card) + BubbleGagPower.getExtraCost(card)
                    + TremblingGripPower.getExtraCost(card);
        }
    }
}
