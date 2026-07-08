package arknsfw.relics.curses.haruka;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

/** 烙印诅咒：兴奋上限 +20（见 ArkThresholdPatch），每回合失去 2 生命。 */
public class HarukaBrandCurseRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("HarukaBrandCurseRelic");

    public HarukaBrandCurseRelic() {
        super(ID, "relic_curse_haruka_harukabrandcurserelic.png", RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public void atTurnStart() {
        flash();
        addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 2));
    }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new HarukaBrandCurseRelic(); }
}
