package arknsfw.relics.haruka;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class HarukaTwinMirrorRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("HarukaTwinMirrorRelic");

    public HarukaTwinMirrorRelic() {
        super(ID, "relic_haruka_harukatwinmirrorrelic.png", RelicTier.RARE, LandingSound.CLINK);
    }

    @Override public void onVictory() { flash(); }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new HarukaTwinMirrorRelic(); }
}
