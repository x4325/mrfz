package arknsfw.relics.fall;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

/** 遥控震动棒：每回合开始掷硬币——正面抽 1 张牌，反面兴奋 +4。 */
public class RemoteVibeRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("RemoteVibeRelic");

    public RemoteVibeRelic() {
        super(ID, "fall_remote_vibe.png", RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public void atTurnStart() {
        flash();
        if (AbstractDungeon.cardRandomRng.randomBoolean()) {
            addToBot(new DrawCardAction(AbstractDungeon.player, 1));
        } else {
            arknsfw.helpers.ArkSafeStats.addExcitementDeferred(4);
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
