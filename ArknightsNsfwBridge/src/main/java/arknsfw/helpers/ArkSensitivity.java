package arknsfw.helpers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import arknsfw.relics.fall.SensitivityBrandRelic;

/**
 * 敏感值：堕落模式败北累积（存在「敏感烙印」遗物 counter 里，随存档保存）。
 * 每点：兴奋获取 +10%，受孕/妊娠进度 +5%。
 */
public final class ArkSensitivity {

    private ArkSensitivity() {
    }

    public static int points() {
        if (AbstractDungeon.player == null) {
            return 0;
        }
        AbstractRelic r = AbstractDungeon.player.getRelic(SensitivityBrandRelic.ID);
        return r == null ? 0 : Math.max(0, r.counter);
    }

    /** 败北+1：没有烙印先给烙印。 */
    public static void addPoint(AbstractPlayer p) {
        if (p == null) {
            return;
        }
        AbstractRelic r = p.getRelic(SensitivityBrandRelic.ID);
        if (r == null) {
            r = new SensitivityBrandRelic();
            r.instantObtain(p, p.relics.size(), true);
        }
        if (r instanceof SensitivityBrandRelic) {
            ((SensitivityBrandRelic) r).addPoint();
        } else {
            r.counter = Math.max(0, r.counter) + 1;
        }
    }

    /** 兴奋获取百分比加成（+10%/点）。 */
    public static int excitementBonusPercent() {
        return points() * 10;
    }

    /** 受孕/妊娠进度百分比加成（+5%/点）。 */
    public static int conceptionBonusPercent() {
        return points() * 5;
    }
}
