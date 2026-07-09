package arknsfw.events.haruka;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import arknsfw.ArkNsfwMod;
import arknsfw.events.AbstractArkFiveRouteEvent;
import arknsfw.helpers.ArkEventImages;
import arknsfw.helpers.ArkRunProgress;
import arknsfw.powers.haruka.LingeringHeatPower;

public class HarukaShameAct3Event extends AbstractArkFiveRouteEvent {
    public static final String ID = ArkNsfwMod.makeID("HarukaShameAct3");

    public HarukaShameAct3Event() {
        super(ID, ArkEventImages.path(ArkEventImages.HARUKA_SHAME_ACT3), ArkRunProgress.Route.SHAME, 3);
    }

    @Override
    protected AbstractPower freshDebuff(int layers) {
        return new LingeringHeatPower(AbstractDungeon.player, layers);
    }
}
