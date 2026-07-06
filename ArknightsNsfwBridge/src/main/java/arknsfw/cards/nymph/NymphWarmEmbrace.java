package arknsfw.cards.nymph;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkNsfwCard;
import arknsfw.helpers.ArkCharMechanicsHelper;
import nymph.core.ColorEnum;
import nymph.powers.HexPower;

public class NymphWarmEmbrace extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("NymphWarmEmbrace");

    public NymphWarmEmbrace() {
        super(ID, 1, CardType.SKILL, ColorEnum.NYMPH_COLOR, CardRarity.UNCOMMON, CardTarget.SELF, "card_nymph_nymphwarmembrace.png");
        
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        NsfwRunStats.addExcitement(10);
        ArkCharMechanicsHelper.applyHexPower(p, 2);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            // no upgrade
        }
    }

    @Override
    public AbstractCard makeCopy() { return new NymphWarmEmbrace(); }
}
