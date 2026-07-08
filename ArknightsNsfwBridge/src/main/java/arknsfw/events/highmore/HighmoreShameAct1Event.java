package arknsfw.events.highmore;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import arknsfw.ArkNsfwMod;
import arknsfw.events.AbstractArkFiveRouteEvent;
import arknsfw.helpers.ArkEventImages;
import arknsfw.helpers.ArkRunProgress;
import arknsfw.powers.highmore.TideBrandPower;

public class HighmoreShameAct1Event extends AbstractArkFiveRouteEvent {
    public static final String ID = ArkNsfwMod.makeID("HighmoreShameAct1");

    public HighmoreShameAct1Event() {
        super(ID, ArkEventImages.path(ArkEventImages.HIGHMORE_SHAME_ACT1), ArkRunProgress.Route.SHAME, 1);
    }

    @Override
    protected AbstractPower freshDebuff(int layers) {
        return new TideBrandPower(AbstractDungeon.player, layers);
    }
}
