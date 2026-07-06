package arknsfw.cards.highmore;

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
import highmore.core.ColorEnum;
import com.megacrit.cardcrawl.actions.common.DamageAction;

public class HighmoreTwinPeak extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("HighmoreTwinPeak");

    public HighmoreTwinPeak() {
        super(ID, 2, CardType.ATTACK, ColorEnum.HIGHMORE_COLOR, CardRarity.RARE, CardTarget.ALL_ENEMY, "card_highmore_highmoretwinpeak.png");
        baseDamage = 7;
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped()) {
                addToBot(new DamageAction(mo, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            }
        }
        NsfwRunStats.addExcitement(15);
        NsfwRunStats.addFertility(6, 4, true);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(2); isMultiDamage=true;
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HighmoreTwinPeak(); }
}
