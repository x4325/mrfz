package arknsfw.events.nymph;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import arknsfw.ArkNsfwMod;
import arknsfw.events.AbstractArkFiveRouteEvent;
import arknsfw.helpers.ArkEventImages;
import arknsfw.helpers.ArkRunProgress;
import arknsfw.powers.nymph.HeartGnawPower;

public class NymphShameAct3Event extends AbstractArkFiveRouteEvent {
    public static final String ID = ArkNsfwMod.makeID("NymphShameAct3");

    public NymphShameAct3Event() {
        super(ID, ArkEventImages.path(ArkEventImages.NYMPH_SHAME_ACT3), ArkRunProgress.Route.SHAME, 3);
    }

    @Override
    protected AbstractPower freshDebuff(int layers) {
        return new HeartGnawPower(AbstractDungeon.player, layers);
    }
}
