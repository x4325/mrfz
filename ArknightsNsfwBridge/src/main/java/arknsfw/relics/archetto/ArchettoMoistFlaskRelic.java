package arknsfw.relics.archetto;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

/** 濡湿药瓶：回复生命时兴奋 +8。 */
public class ArchettoMoistFlaskRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("ArchettoMoistFlaskRelic");

    public ArchettoMoistFlaskRelic() {
        super(ID, "relic_archetto_archettomoistflaskrelic.png", RelicTier.SHOP, LandingSound.CLINK);
    }

    @Override
    public int onPlayerHeal(int healAmount) {
        if (healAmount > 0) {
            flash();
            NsfwRunStats.addExcitement(8);
        }
        return healAmount;
    }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new ArchettoMoistFlaskRelic(); }
}
