package arknsfw.events.muel;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import liesecore.helpers.CurseHelper;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.events.AbstractArkRouteEvent;
import arknsfw.helpers.ArkEventImages;
import arknsfw.helpers.ArkRouteDirtyOptionHelper;
import arknsfw.helpers.ArkRunProgress;

public class MuelNormalGardenTeaEvent extends AbstractArkRouteEvent {
    public static final String ID = ArkNsfwMod.makeID("MuelNormalGardenTea");

    public MuelNormalGardenTeaEvent() {
        super(ID, ArkEventImages.path(ArkEventImages.MUEL_NORMAL_ACT2), ArkRunProgress.Route.NORMAL, 4);
    }

    @Override
    protected void onFirstChoice(int buttonUsed) {
        lockThisRoute();
        if (buttonUsed == 0) {
            AbstractDungeon.player.heal(Math.max(1, AbstractDungeon.player.maxHealth * 15 / 100));
            NsfwRunStats.addExcitement(15);
            NsfwRunStats.addFertility(6, 0, false);
            showResult(1);
            return;
        }
        if (buttonUsed == 1) {
            NsfwRunStats.addExcitement(-8);
            NsfwRunStats.addFertility(0, 0, false);
            showResult(2);
            return;
        }
        if (buttonUsed == 2) {
            AbstractDungeon.player.gainGold(30);
            NsfwRunStats.addExcitement(8);
            showResult(3);
            return;
        }
        if (buttonUsed == 3) {
            ArkRouteDirtyOptionHelper.applyNormalWarmth(2);
            showResult(4);
        }
    }
}
