package arknsfw.cards.archetto;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
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

/** 淫乱状态：资源引擎+持续兴奋 */
public class ArchettoLewdTrance extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("ArchettoLewdTrance");

    public ArchettoLewdTrance() {
        super(ID, 2, CardType.POWER, ColorEnum.ARCHETTO_COLOR, CardRarity.RARE, CardTarget.SELF, "card_archetto_archettomoistbarrier.png");
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(p, p,
                new arknsfw.powers.LewdTrancePower(p, magicNumber), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new ArchettoLewdTrance(); }
}
