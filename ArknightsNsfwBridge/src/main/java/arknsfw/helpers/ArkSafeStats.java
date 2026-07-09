package arknsfw.helpers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import liesecore.helpers.NsfwRunStats;

/**
 * power 钩子内的数值结算必须延迟到动作队列执行：
 * 受孕/兴奋跨过阈值时 liesecore 可能立即添加新 power，
 * 若发生在 powers 列表迭代中（如 applyEndOfTurnTriggers）会触发
 * ConcurrentModificationException 闪退。
 */
public final class ArkSafeStats {

    private ArkSafeStats() {
    }

    public static void addConceptionDeferred(final int amount, final boolean inside) {
        if (AbstractDungeon.actionManager == null) {
            NsfwRunStats.addConception(amount, inside);
            return;
        }
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                NsfwRunStats.addConception(amount, inside);
                this.isDone = true;
            }
        });
    }

    public static void addExcitementDeferred(final int amount) {
        if (AbstractDungeon.actionManager == null) {
            NsfwRunStats.addExcitement(amount);
            return;
        }
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                NsfwRunStats.addExcitement(amount);
                this.isDone = true;
            }
        });
    }

    public static void addFertilityDeferred(final int a, final int b, final boolean c) {
        if (AbstractDungeon.actionManager == null) {
            NsfwRunStats.addFertility(a, b, c);
            return;
        }
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                NsfwRunStats.addFertility(a, b, c);
                this.isDone = true;
            }
        });
    }
}
