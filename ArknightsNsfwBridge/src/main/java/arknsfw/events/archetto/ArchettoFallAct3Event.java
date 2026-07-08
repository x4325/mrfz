package arknsfw.events.archetto;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import arknsfw.ArkNsfwMod;
import arknsfw.events.AbstractArkFiveRouteEvent;
import arknsfw.helpers.ArkEventImages;
import arknsfw.helpers.ArkRunProgress;
import arknsfw.powers.archetto.TremblingGripPower;

public class ArchettoFallAct3Event extends AbstractArkFiveRouteEvent {
    public static final String ID = ArkNsfwMod.makeID("ArchettoFallAct3");

    public ArchettoFallAct3Event() {
        super(ID, ArkEventImages.path(ArkEventImages.ARCHETTO_FALL_ACT3), ArkRunProgress.Route.FALL, 3);
    }

    @Override
    protected AbstractPower freshDebuff(int layers) {
        return new TremblingGripPower(AbstractDungeon.player, layers);
    }
}
