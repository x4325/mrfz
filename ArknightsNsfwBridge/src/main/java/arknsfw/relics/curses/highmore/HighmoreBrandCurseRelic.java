package arknsfw.relics.curses.highmore;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

/** 烙印诅咒：兴奋上限 +20（见 ArkThresholdPatch），每回合失去 2 生命。 */
public class HighmoreBrandCurseRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("HighmoreBrandCurseRelic");

    public HighmoreBrandCurseRelic() {
        super(ID, "relic_curse_highmore_highmorebrandcurserelic.png", RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public void atTurnStart() {
        flash();
        addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 2));
    }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new HighmoreBrandCurseRelic(); }
}
