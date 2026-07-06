package arknsfw.relics.eyja;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.relics.AbstractArkNsfwRelic;

public class AshCollarRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("AshCollarRelic");
    private boolean markedThisTurn = false;

    public AshCollarRelic() {
        super(ID, "ash_collar.png", RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        flash();
        NsfwRunStats.addExcitement(10);
        ArkCharMechanicsHelper.gainCloudEnergy(1);
    }

    @Override
    public void atTurnStart() {
        markedThisTurn = false;
    }

    @Override
    public void onLoseHp(int damageAmount) {
        if (damageAmount > 0) {
            NsfwRunStats.addExcitement(4);
            if (!markedThisTurn && AbstractDungeon.player != null) {
                markedThisTurn = true;
                ArkCharMechanicsHelper.applyFireMarkPower(AbstractDungeon.player, 1);
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new AshCollarRelic();
    }
}
