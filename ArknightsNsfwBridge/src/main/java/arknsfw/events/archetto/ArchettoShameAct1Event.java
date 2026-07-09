package arknsfw.events.archetto;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import arknsfw.ArkNsfwMod;
import arknsfw.events.AbstractArkFiveRouteEvent;
import arknsfw.helpers.ArkEventImages;
import arknsfw.helpers.ArkRunProgress;
import arknsfw.powers.archetto.TremblingGripPower;

public class ArchettoShameAct1Event extends AbstractArkFiveRouteEvent {
    public static final String ID = ArkNsfwMod.makeID("ArchettoShameAct1");

    public ArchettoShameAct1Event() {
        super(ID, ArkEventImages.path(ArkEventImages.ARCHETTO_SHAME_ACT1), ArkRunProgress.Route.SHAME, 1);
    }

    @Override
    protected AbstractPower freshDebuff(int layers) {
        return new TremblingGripPower(AbstractDungeon.player, layers);
    }
}
