package arknsfw.relics.archetto;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

/** 双子镜：战斗胜利时受孕度 +3。 */
public class ArchettoTwinMirrorRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("ArchettoTwinMirrorRelic");

    public ArchettoTwinMirrorRelic() {
        super(ID, "relic_archetto_archettotwinmirrorrelic.png", RelicTier.RARE, LandingSound.CLINK);
    }

    @Override
    public void onVictory() {
        flash();
        NsfwRunStats.addConception(3, false);
    }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new ArchettoTwinMirrorRelic(); }
}
