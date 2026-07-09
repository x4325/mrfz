package arknsfw.events.highmore;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import arknsfw.ArkNsfwMod;
import arknsfw.events.AbstractArkFiveRouteEvent;
import arknsfw.helpers.ArkEventImages;
import arknsfw.helpers.ArkRunProgress;
import arknsfw.powers.highmore.TideBrandPower;

public class HighmoreFallAct1Event extends AbstractArkFiveRouteEvent {
    public static final String ID = ArkNsfwMod.makeID("HighmoreFallAct1");

    public HighmoreFallAct1Event() {
        super(ID, ArkEventImages.path(ArkEventImages.HIGHMORE_FALL_ACT1), ArkRunProgress.Route.FALL, 1);
    }

    @Override
    protected AbstractPower freshDebuff(int layers) {
        return new TideBrandPower(AbstractDungeon.player, layers);
    }
}
