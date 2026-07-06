package arknsfw.cards.archetto;

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
import archetto.core.ColorEnum;
import archetto.powers.AimPower;

public class ArchettoCoreNeed extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("ArchettoCoreNeed");

    public ArchettoCoreNeed() {
        super(ID, 2, CardType.POWER, ColorEnum.ARCHETTO_COLOR, CardRarity.RARE, CardTarget.SELF, "card_archetto_archettocoreneed.png");
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        NsfwRunStats.addExcitement(5);
        ArkCharMechanicsHelper.applyAimPower(p, 1);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1); baseMagicNumber=1;
        }
    }

    @Override
    public AbstractCard makeCopy() { return new ArchettoCoreNeed(); }
}
