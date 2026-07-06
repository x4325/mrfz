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
import com.megacrit.cardcrawl.actions.common.DamageAction;

public class ArchettoSilentCaress extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("ArchettoSilentCaress");

    public ArchettoSilentCaress() {
        super(ID, 1, CardType.SKILL, ColorEnum.ARCHETTO_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY, "card_archetto_archettosilentcaress.png");
        baseMagicNumber = magicNumber = 8;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE));
        NsfwRunStats.addExcitement(12);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(4);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new ArchettoSilentCaress(); }
}
