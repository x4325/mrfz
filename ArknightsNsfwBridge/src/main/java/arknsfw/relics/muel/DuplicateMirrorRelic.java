package arknsfw.relics.muel;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.relics.AbstractArkNsfwRelic;

public class DuplicateMirrorRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("DuplicateMirrorRelic");

    public DuplicateMirrorRelic() {
        super(ID, "duplicate_mirror.png", RelicTier.RARE, LandingSound.SOLID);
    }

    @Override
    public void atBattleStart() {
        flash();
        NsfwRunStats.addExcitement(12);
        if (ArkCharMechanicsHelper.hasManifold()) {
            ArkCharMechanicsHelper.boostManifold(1);
            NsfwRunStats.addFertility(0, 12, true);
        } else {
            NsfwRunStats.addFertility(0, 8, true);
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new DuplicateMirrorRelic();
    }
}
