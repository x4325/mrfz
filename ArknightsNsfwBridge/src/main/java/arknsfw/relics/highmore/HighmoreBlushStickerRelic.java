package arknsfw.relics.highmore;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import com.megacrit.cardcrawl.cards.AbstractCard;

import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

public class HighmoreBlushStickerRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("HighmoreBlushStickerRelic");

    public HighmoreBlushStickerRelic() {
        super(ID, "relic_highmore_highmoreblushstickerrelic.png", RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override public void onUseCard(AbstractCard card, com.megacrit.cardcrawl.actions.utility.UseCardAction action) { if (card.type == AbstractCard.CardType.SKILL) { NsfwRunStats.addExcitement(4); flash(); } }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new HighmoreBlushStickerRelic(); }
}
