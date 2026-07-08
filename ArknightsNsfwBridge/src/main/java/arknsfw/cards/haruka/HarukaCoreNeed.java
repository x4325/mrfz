package arknsfw.cards.haruka;

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
import haruka.core.ColorEnum;
import haruka.powers.PyroPower;

public class HarukaCoreNeed extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("HarukaCoreNeed");

    public HarukaCoreNeed() {
        super(ID, 2, CardType.POWER, ColorEnum.HARUKA_COLOR, CardRarity.RARE, CardTarget.SELF, "card_haruka_harukacoreneed.png");
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(p, p,
                new arknsfw.powers.CoreNeedPower(p, magicNumber), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1); baseMagicNumber=1;
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HarukaCoreNeed(); }
}
