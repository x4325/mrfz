package arknsfw.relics.fall;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import arknsfw.ArkNsfwMod;
import arknsfw.powers.fall.LustSurgePower;
import arknsfw.relics.AbstractArkNsfwRelic;

/** 淫气缠身护符：战斗开始时获得「淫气高涨」。 */
public class LustSurgeCharmRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("LustSurgeCharmRelic");

    public LustSurgeCharmRelic() {
        super(ID, "fall_lustsurge_charm.png", RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        flash();
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new LustSurgePower(AbstractDungeon.player, 1), 1));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
