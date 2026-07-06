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

public class NymphCoreNeed extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("NymphCoreNeed");

    public NymphCoreNeed() {
        super(ID, 2, CardType.POWER, ColorEnum.NYMPH_COLOR, CardRarity.RARE, CardTarget.SELF, "card_nymph_nymphcoreneed.png");
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        NsfwRunStats.addExcitement(5);
        ArkCharMechanicsHelper.applyHexPower(p, 1);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1); baseMagicNumber=1;
        }
    }

    @Override
    public AbstractCard makeCopy() { return new NymphCoreNeed(); }
}
