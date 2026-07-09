package arknsfw.events;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import liesecore.helpers.NsfwRunStats;
import arknsfw.helpers.ArkCurseHelper;
import arknsfw.helpers.ArkDebuffHelper;
import arknsfw.helpers.ArkRouteDirtyOptionHelper;
import arknsfw.helpers.ArkRunProgress;

/**
 * 五名自制干员共用的路线事件模板：三条路线机制差异化，数值随幕数递增，
 * 第四选项为该路线的「献身」结局。子类只需提供本角色的专属 debuff。
 */
public abstract class AbstractArkFiveRouteEvent extends AbstractArkRouteEvent {

    protected final int act;

    protected AbstractArkFiveRouteEvent(String id, String imagePath, ArkRunProgress.Route route, int act) {
        super(id, imagePath, route, 4);
        this.act = act;
    }

    /** 本角色的专属羞耻 debuff（layers 层）。 */
    protected abstract AbstractPower freshDebuff(int layers);

    @Override
    protected void onFirstChoice(int buttonUsed) {
        lockThisRoute();
        AbstractPlayer p = AbstractDungeon.player;
        switch (route) {
            case NORMAL:
                normalChoice(p, buttonUsed);
                break;
            case SHAME:
                shameChoice(p, buttonUsed);
                break;
            default:
                fallChoice(p, buttonUsed);
                break;
        }
        showResult(buttonUsed + 1);
    }

    private void normalChoice(AbstractPlayer p, int b) {
        if (b == 0) {
            // 休憩：温柔的照料
            p.heal(Math.max(1, p.maxHealth * (10 + 5 * act) / 100));
            NsfwRunStats.addExcitement(8 + 2 * act);
        } else if (b == 1) {
            // 抑制：压下火气，换取实际报酬
            NsfwRunStats.addExcitement(-10);
            p.gainGold(15 * act);
        } else if (b == 2) {
            // 依偎：亲近但不越界
            p.increaseMaxHp(2 + act, true);
            NsfwRunStats.addConception(4 + 2 * act, false);
            NsfwRunStats.addExcitement(10 + 2 * act);
        } else {
            // 温存献身
            ArkRouteDirtyOptionHelper.applyNormalWarmth(act);
        }
    }

    private void shameChoice(AbstractPlayer p, int b) {
        if (b == 0) {
            // 忍受注视
            ArkDebuffHelper.apply(p, freshDebuff(1));
            NsfwRunStats.addExcitement(15 + 5 * act);
            ArkCurseHelper.addRandomCurseForCurrentCharacter();
        } else if (b == 1) {
            // 咬牙承受
            p.damage(new DamageInfo(null, 6 + 2 * act));
            NsfwRunStats.addExcitement(25 + 5 * act);
        } else if (b == 2) {
            // 主动展示
            ArkDebuffHelper.apply(p, freshDebuff(2));
            NsfwRunStats.addFertility(8 + 4 * act, 0, act >= 2);
            NsfwRunStats.addExcitement(20 + 5 * act);
        } else {
            // 自述污痕（抑制）
            ArkRouteDirtyOptionHelper.applyShameSelfHumiliate(p, freshDebuff(2), act);
        }
    }

    private void fallChoice(AbstractPlayer p, int b) {
        if (b == 0) {
            // 顺从欲望
            NsfwRunStats.addExcitement(25 + 5 * act);
            NsfwRunStats.addFertility(8 + 2 * act, 6 + 2 * act, true);
        } else if (b == 1) {
            // 索求更多
            ArkDebuffHelper.apply(p, freshDebuff(1));
            NsfwRunStats.addExcitement(35 + 5 * act);
            NsfwRunStats.addConception(8 + 2 * act, true);
        } else if (b == 2) {
            // 彻底沉沦
            p.damage(new DamageInfo(null, 4));
            NsfwRunStats.addExcitement(45 + 5 * act);
            NsfwRunStats.addFertility(12 + 3 * act, 8 + 2 * act, true);
        } else {
            // 堕落献身：最深的一步
            NsfwRunStats.addExcitement(30 + 5 * act);
            if (NsfwRunStats.pregnant) {
                NsfwRunStats.addPregnancyProgress(10 + 5 * act);
            } else {
                NsfwRunStats.addConception(12 + 2 * act, true);
            }
            NsfwRunStats.addFertility(8 + 2 * act, 10 + 2 * act, true);
        }
    }
}
