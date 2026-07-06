package arknsfw.events.nymph;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import liesecore.helpers.CurseHelper;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.events.AbstractArkRouteEvent;
import arknsfw.helpers.ArkEventImages;
import arknsfw.helpers.ArkRunProgress;

public class NymphFallAct3Event extends AbstractArkRouteEvent {
    public static final String ID = ArkNsfwMod.makeID("NymphFallAct3");

    public NymphFallAct3Event() {
        super(ID, ArkEventImages.path(ArkEventImages.NYMPH_FALL_ACT3), ArkRunProgress.Route.FALL, 3);
    }

    @Override
    protected void onFirstChoice(int buttonUsed) {
        lockThisRoute();
        if (buttonUsed == 0) {
            AbstractDungeon.player.heal(Math.max(1, AbstractDungeon.player.maxHealth * 15 / 100));
            NsfwRunStats.addExcitement(15);
            showResult(1);
            return;
        }
        if (buttonUsed == 1) {
            NsfwRunStats.addExcitement(25);
            NsfwRunStats.addConception(8, false);
            CurseHelper.addRandomCurseToDeck();
            showResult(2);
            return;
        }
        NsfwRunStats.addExcitement(40);
        NsfwRunStats.addFertility(10, 8, true);
        showResult(3);
    }
}
