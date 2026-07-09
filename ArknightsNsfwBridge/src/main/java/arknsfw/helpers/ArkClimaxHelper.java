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
        int excitementAtClimax = NsfwRunStats.excitement;
        NsfwRunStats.addExcitement(-NsfwRunStats.excitement);
        // 快感转换器：不失去格挡，改为快感冲击（兴奋值一半的全体伤害）
        boolean converter = p.hasRelic(arknsfw.relics.fall.PleasureConverterRelic.ID);
        if (converter) {
            int dmg = Math.max(5, excitementAtClimax / 2);
            AbstractDungeon.actionManager.addToBottom(
                    new com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction(
                            p, com.megacrit.cardcrawl.cards.DamageInfo.createDamageMatrix(dmg, true),
                            com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS,
                            com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));
        } else if (!p.hasRelic(arknsfw.relics.equipment.RingGagRelic.ID)) {
            p.loseBlock();
        }
        PowerHelper.apply(p, new WeakPower(p, 1, false));
        PowerHelper.apply(p, new FrailPower(p, 1, false));
        PowerHelper.apply(p, new DrawReductionPower(p, 1));
        // 绝顶余韵：高潮后 2 回合格挡获取 -25%
        PowerHelper.apply(p, new arknsfw.powers.fall.AfterglowPower(p, 2));
        AbstractPower brand = ArkCharDebuffs.fresh(p, 1);
        if (brand != null) {
            ArkDebuffHelper.apply(p, brand);
        }
        // 调教项圈·进阶：高潮计数 → 永久力量
        com.megacrit.cardcrawl.relics.AbstractRelic collar =
                p.getRelic(arknsfw.relics.fall.TrainingCollarPlusRelic.ID);
        if (collar instanceof arknsfw.relics.fall.TrainingCollarPlusRelic) {
            ((arknsfw.relics.fall.TrainingCollarPlusRelic) collar).onClimax();
        }
        // 贤者怀表：下回合回神（+1费+2抽）
        if (p.hasRelic(arknsfw.relics.fall.SageTimeWatchRelic.ID)) {
            PowerHelper.apply(p, new arknsfw.powers.fall.SageTimePower(p));
        }
        UiHelper.showCenterText(p, "高潮——失控！");
        ArkPortraitPanel.notifyClimax();
    }
}
