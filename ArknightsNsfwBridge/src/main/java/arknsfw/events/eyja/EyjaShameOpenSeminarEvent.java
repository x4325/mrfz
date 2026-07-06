package arknsfw.events.eyja;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import liesecore.helpers.CurseHelper;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.events.AbstractArkRouteEvent;
import arknsfw.helpers.ArkDebuffHelper;
import arknsfw.helpers.ArkEventImages;
import arknsfw.helpers.ArkRouteDirtyOptionHelper;
import arknsfw.helpers.ArkRunProgress;
import arknsfw.powers.eyja.AshShamePower;
import arknsfw.powers.eyja.CoreStrainPower;


public class EyjaShameOpenSeminarEvent extends AbstractArkRouteEvent {
    public static final String ID = ArkNsfwMod.makeID("EyjaShameOpenSeminar");

    public EyjaShameOpenSeminarEvent() {
        super(ID, ArkEventImages.path(ArkEventImages.EYJA_SHAME_ACT2), ArkRunProgress.Route.SHAME, 4);
    }

    @Override
    protected void onFirstChoice(int buttonUsed) {
        lockThisRoute();
        if (buttonUsed == 0) {
            ArkDebuffHelper.apply(AbstractDungeon.player, new CoreStrainPower(AbstractDungeon.player, 1));
            NsfwRunStats.addExcitement(25);
            NsfwRunStats.addFertility(8, 0, false);
            CurseHelper.addRandomCurseToDeck();
            showResult(1);
            return;
        }
        if (buttonUsed == 1) {
            ArkDebuffHelper.apply(AbstractDungeon.player, new AshShamePower(AbstractDungeon.player, 2));
            AbstractDungeon.player.damage(new com.megacrit.cardcrawl.cards.DamageInfo(null, 8));
            NsfwRunStats.addExcitement(35);
            showResult(2);
            return;
        }
        if (buttonUsed == 2) {
            NsfwRunStats.addExcitement(20);
            NsfwRunStats.addFertility(12, 0, true);
            showResult(3);
            return;
        }
        if (buttonUsed == 3) {
            ArkRouteDirtyOptionHelper.applyShameSelfHumiliate(
                    AbstractDungeon.player, new CoreStrainPower(AbstractDungeon.player, 2), 2);
            showResult(4);
        }
    }
}
