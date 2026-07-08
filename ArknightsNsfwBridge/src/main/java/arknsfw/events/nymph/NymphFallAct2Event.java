package arknsfw.events.nymph;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import arknsfw.ArkNsfwMod;
import arknsfw.events.AbstractArkFiveRouteEvent;
import arknsfw.helpers.ArkEventImages;
import arknsfw.helpers.ArkRunProgress;
import arknsfw.powers.nymph.HeartGnawPower;

public class NymphFallAct2Event extends AbstractArkFiveRouteEvent {
    public static final String ID = ArkNsfwMod.makeID("NymphFallAct2");

    public NymphFallAct2Event() {
        super(ID, ArkEventImages.path(ArkEventImages.NYMPH_FALL_ACT2), ArkRunProgress.Route.FALL, 2);
    }

    @Override
    protected AbstractPower freshDebuff(int layers) {
        return new HeartGnawPower(AbstractDungeon.player, layers);
    }
}
