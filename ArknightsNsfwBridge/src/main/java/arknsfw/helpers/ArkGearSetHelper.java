package arknsfw.helpers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import liesecore.helpers.UiHelper;
import arknsfw.powers.fall.HeatAdaptPower;
import arknsfw.powers.fall.LustSurgePower;

/**
 * 拘束套装共鸣：按持有的拘束装备件数触发战斗开始加成。
 * 3件：淫气高涨1层；5件：另获发情适应1层+8格挡；7件：再+2力量、兴奋+12。
 */
public final class ArkGearSetHelper {

    private ArkGearSetHelper() {
    }

    public static int equipmentCount() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return 0;
        }
        int n = 0;
        for (String id : ArkFallMode.EQUIPMENT_IDS) {
            if (p.hasRelic(id)) {
                n++;
            }
        }
        return n;
    }

    public static void atBattleStart() {
        if (!ArkCharacterSetup.isArkNsfwRun() || AbstractDungeon.player == null) {
            return;
        }
        AbstractPlayer p = AbstractDungeon.player;
        int n = equipmentCount();
        if (n < 3) {
            return;
        }
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new LustSurgePower(p, 1), 1));
        if (n >= 5) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(p, p, new HeatAdaptPower(p, 1), 1));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, 8));
        }
        if (n >= 7) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(p, p, new StrengthPower(p, 2), 2));
            ArkSafeStats.addExcitementDeferred(12);
        }
        UiHelper.showCenterText(p, "拘束套装共鸣·" + n + "件");
    }
}
