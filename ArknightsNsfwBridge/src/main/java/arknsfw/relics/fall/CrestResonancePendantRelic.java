package arknsfw.relics.fall;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

/** 淫纹共鸣坠饰：每回合开始，按牌组诅咒数获得格挡（每张2点，至多12）。 */
public class CrestResonancePendantRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("CrestResonancePendantRelic");

    public CrestResonancePendantRelic() {
        super(ID, "fall_crest_pendant.png", RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStart() {
        int curses = 0;
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.type == AbstractCard.CardType.CURSE) {
                curses++;
            }
        }
        int block = Math.min(12, curses * 2);
        if (block > 0) {
            flash();
            addToBot(new GainBlockAction(AbstractDungeon.player, block));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
