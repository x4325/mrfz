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
import com.megacrit.cardcrawl.actions.common.DamageAction;

public class HarukaSilentCaress extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("HarukaSilentCaress");

    public HarukaSilentCaress() {
        super(ID, 1, CardType.SKILL, ColorEnum.HARUKA_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY, "card_haruka_harukasilentcaress.png");
        baseMagicNumber = magicNumber = 8;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE));
        NsfwRunStats.addExcitement(12);
        ArkCharMechanicsHelper.applyPyroPower(p, 1);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(4);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HarukaSilentCaress(); }
}
