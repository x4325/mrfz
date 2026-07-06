package arknsfw.helpers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DrawReductionPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import liesecore.helpers.NsfwRunStats;
import liesecore.helpers.PowerHelper;
import liesecore.helpers.UiHelper;

/**
 * 高潮失控：回合开始时兴奋达到阈值 → 当回合失控
 * （虚弱+脆弱各1、抽牌-1、失去全部格挡、专属印记+1），兴奋清零。
 */
public final class ArkClimaxHelper {

    private ArkClimaxHelper() {
    }

    public static void onPlayerTurnStart() {
        if (ArkCharDebuffs.currentCharKey() == null || AbstractDungeon.player == null
                || AbstractDungeon.currMapNode == null) {
            return;
        }
        AbstractRoom room = AbstractDungeon.getCurrRoom();
        if (room == null || room.phase != AbstractRoom.RoomPhase.COMBAT) {
            return;
        }
        int threshold = LieseCompat.climaxThreshold();
        if (threshold <= 0 || NsfwRunStats.excitement < threshold) {
            return;
        }
        AbstractPlayer p = AbstractDungeon.player;
        NsfwRunStats.addExcitement(-NsfwRunStats.excitement);
        p.loseBlock();
        PowerHelper.apply(p, new WeakPower(p, 1, false));
        PowerHelper.apply(p, new FrailPower(p, 1, false));
        PowerHelper.apply(p, new DrawReductionPower(p, 1));
        AbstractPower brand = ArkCharDebuffs.fresh(p, 1);
        if (brand != null) {
            ArkDebuffHelper.apply(p, brand);
        }
        UiHelper.showCenterText(p, "高潮——失控！");
    }
}
