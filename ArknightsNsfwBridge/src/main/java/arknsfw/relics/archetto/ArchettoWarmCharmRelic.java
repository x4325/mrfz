package arknsfw.relics.archetto;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.relics.AbstractArkNsfwRelic;

/** 温热护符：兴奋 ≥30 时攻击牌伤害 +2。 */
public class ArchettoWarmCharmRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("ArchettoWarmCharmRelic");

    public ArchettoWarmCharmRelic() {
        super(ID, "relic_archetto_archettowarmcharmrelic.png", RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public float atDamageModify(float damage, AbstractCard card) {
        if (card != null && card.type == AbstractCard.CardType.ATTACK && NsfwRunStats.excitement >= 30) {
            return damage + 2.0F;
        }
        return damage;
    }

    @Override public void atTurnStart() { if (NsfwRunStats.excitement >= 30) flash(); }

    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new ArchettoWarmCharmRelic(); }
}
