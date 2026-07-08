package arknsfw.relics.fall;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

/** 调教项圈·进阶：每次高潮失控计数 +1；每场战斗开始按计数获得力量。 */
public class TrainingCollarPlusRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("TrainingCollarPlusRelic");

    public TrainingCollarPlusRelic() {
        super(ID, "fall_training_collar.png", RelicTier.RARE, LandingSound.CLINK);
        this.counter = 0;
    }

    /** ArkClimaxHelper 在高潮失控时调用。 */
    public void onClimax() {
        this.counter++;
        flash();
    }

    @Override
    public void atBattleStart() {
        if (this.counter > 0) {
            flash();
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new StrengthPower(AbstractDungeon.player, this.counter), this.counter));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
