package arknsfw.relics.curses.haruka;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

/** 回环诅咒：抽到诅咒牌时兴奋 +5。 */
public class HarukaLoopCurseRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("HarukaLoopCurseRelic");

    public HarukaLoopCurseRelic() {
        super(ID, "relic_curse_haruka_harukaloopcurserelic.png", RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (card != null && card.type == AbstractCard.CardType.CURSE) {
            flash();
            NsfwRunStats.addExcitement(5);
        }
    }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new HarukaLoopCurseRelic(); }
}
