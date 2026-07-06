package arknsfw.relics.muel;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.relics.AbstractArkNsfwRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class OverflowFlaskRelic extends AbstractArkNsfwRelic {
    public static final String ID = ArkNsfwMod.makeID("OverflowFlaskRelic");

    public OverflowFlaskRelic() {
        super(ID, "overflow_flask.png", RelicTier.BOSS, LandingSound.MAGICAL);
    }

    private int skillCount = 0;

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c.type == AbstractCard.CardType.SKILL) {
            skillCount++;
            int need = ArkCharMechanicsHelper.hasManifold() ? 2 : 3;
            if (skillCount >= need) {
                skillCount = 0;
                NsfwRunStats.addFertility(10, 8, true);
                flash();
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new OverflowFlaskRelic();
    }
}
