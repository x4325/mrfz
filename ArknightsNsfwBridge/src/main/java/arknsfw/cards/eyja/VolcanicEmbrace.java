package arknsfw.cards.eyja;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkNsfwCard;
import arknsfw.helpers.ArkCharMechanicsHelper;
import Eyjafjalla.modcore.ColorEnum;

public class VolcanicEmbrace extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("VolcanicEmbrace");

    public VolcanicEmbrace() {
        super(ID, 2, CardType.ATTACK, ColorEnum.Eyjafjalla_COLOR, CardRarity.RARE, CardTarget.ENEMY, "card_volcanic_embrace.png");
        baseDamage = 14;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int dmg = damage;
        if (ArkCharMechanicsHelper.pyrobreathActive(this)) {
            dmg += 6;
        }
        if (ArkCharMechanicsHelper.cloudEnergy() >= 2) {
            ArkCharMechanicsHelper.spendCloudEnergy(1);
            dmg += 8;
        }
        addToBot(new DamageAction(m, new DamageInfo(p, dmg, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
        if (ArkCharMechanicsHelper.pyrobreathActive(this)) {
            NsfwRunStats.addFertility(12, 10, true);
        } else {
            NsfwRunStats.addFertility(8, 6, true);
        }
        NsfwRunStats.addExcitement(10);
        ArkCharMechanicsHelper.markPyrobreath(this);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(5);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new VolcanicEmbrace();
    }
}
