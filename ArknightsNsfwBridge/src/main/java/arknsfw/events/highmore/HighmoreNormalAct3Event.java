package arknsfw.events.highmore;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import arknsfw.ArkNsfwMod;
import arknsfw.events.AbstractArkFiveRouteEvent;
import arknsfw.helpers.ArkEventImages;
import arknsfw.helpers.ArkRunProgress;
import arknsfw.powers.highmore.TideBrandPower;

public class HighmoreNormalAct3Event extends AbstractArkFiveRouteEvent {
    public static final String ID = ArkNsfwMod.makeID("HighmoreNormalAct3");

    public HighmoreNormalAct3Event() {
        super(ID, ArkEventImages.path(ArkEventImages.HIGHMORE_NORMAL_ACT3), ArkRunProgress.Route.NORMAL, 3);
    }

    @Override
    protected AbstractPower freshDebuff(int layers) {
        return new TideBrandPower(AbstractDungeon.player, layers);
    }
}
