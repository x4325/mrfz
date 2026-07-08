package arknsfw.events.nymph;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import arknsfw.ArkNsfwMod;
import arknsfw.events.AbstractArkFiveRouteEvent;
import arknsfw.helpers.ArkEventImages;
import arknsfw.helpers.ArkRunProgress;
import arknsfw.powers.nymph.HeartGnawPower;

public class NymphNormalAct1Event extends AbstractArkFiveRouteEvent {
    public static final String ID = ArkNsfwMod.makeID("NymphNormalAct1");

    public NymphNormalAct1Event() {
        super(ID, ArkEventImages.path(ArkEventImages.NYMPH_NORMAL_ACT1), ArkRunProgress.Route.NORMAL, 1);
    }

    @Override
    protected AbstractPower freshDebuff(int layers) {
        return new HeartGnawPower(AbstractDungeon.player, layers);
    }
}
