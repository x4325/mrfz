package arknsfw.relics.fall;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

import java.util.ArrayList;

/** 淫纹阴环：战斗开始时抽牌堆中的诅咒全部消耗，每张兴奋 +6。 */
public class CrestAmpRingRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("CrestAmpRingRelic");

    public CrestAmpRingRelic() {
        super(ID, "fall_crest_ring.png", RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStartPreDraw() {
        ArrayList<AbstractCard> curses = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.type == AbstractCard.CardType.CURSE) {
                curses.add(c);
            }
        }
        if (curses.isEmpty()) {
            return;
        }
        flash();
        for (AbstractCard c : curses) {
            AbstractDungeon.player.drawPile.moveToExhaustPile(c);
            arknsfw.helpers.ArkSafeStats.addExcitementDeferred(6);
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
