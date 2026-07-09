package arknsfw.relics.fall;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import arknsfw.ArkNsfwMod;
import arknsfw.powers.fall.HeatAdaptPower;
import arknsfw.relics.AbstractArkNsfwRelic;

/** 发情适应徽章：战斗开始时获得「发情适应」。 */
public class HeatAdaptBadgeRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("HeatAdaptBadgeRelic");

    public HeatAdaptBadgeRelic() {
        super(ID, "fall_heatadapt_badge.png", RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        flash();
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new HeatAdaptPower(AbstractDungeon.player, 1), 1));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
