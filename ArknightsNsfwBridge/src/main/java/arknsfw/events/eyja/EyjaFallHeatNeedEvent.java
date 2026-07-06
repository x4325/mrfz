package arknsfw.events.eyja;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.events.AbstractArkRouteEvent;
import arknsfw.helpers.ArkDebuffHelper;
import arknsfw.helpers.ArkEventImages;
import arknsfw.helpers.ArkRouteDirtyOptionHelper;
import arknsfw.helpers.ArkRunProgress;
import arknsfw.powers.eyja.CoreStrainPower;
import arknsfw.powers.eyja.VolcanicFlushPower;


public class EyjaFallHeatNeedEvent extends AbstractArkRouteEvent {
    public static final String ID = ArkNsfwMod.makeID("EyjaFallHeatNeed");

    public EyjaFallHeatNeedEvent() {
        super(ID, ArkEventImages.path(ArkEventImages.EYJA_FALL_ACT1), ArkRunProgress.Route.FALL, 4);
    }

    @Override
    protected void onFirstChoice(int buttonUsed) {
        lockThisRoute();
        if (buttonUsed == 0) {
            ArkDebuffHelper.apply(AbstractDungeon.player, new VolcanicFlushPower(AbstractDungeon.player, 2));
            NsfwRunStats.addExcitement(40);
            NsfwRunStats.addFertility(10, 8, true);
            showResult(1);
            return;
        }
        if (buttonUsed == 1) {
            ArkDebuffHelper.apply(AbstractDungeon.player, new CoreStrainPower(AbstractDungeon.player, 1));
            NsfwRunStats.addExcitement(55);
            NsfwRunStats.addFertility(0, 15, true);
            showResult(2);
            return;
        }
        if (buttonUsed == 2) {
            AbstractDungeon.player.damage(new com.megacrit.cardcrawl.cards.DamageInfo(null, 5));
            NsfwRunStats.addExcitement(65);
            NsfwRunStats.addFertility(15, 20, true);
            showResult(3);
            return;
        }
        if (buttonUsed == 3) {
            ArkRouteDirtyOptionHelper.applyEyjaFallDirty(1);
            showResult(4);
        }
    }
}
