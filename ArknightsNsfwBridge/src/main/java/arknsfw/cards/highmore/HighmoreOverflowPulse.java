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
import highmore.powers.ReapPower;

public class HighmoreOverflowPulse extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("HighmoreOverflowPulse");

    public HighmoreOverflowPulse() {
        super(ID, 1, CardType.ATTACK, ColorEnum.HIGHMORE_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY, "card_highmore_highmoreoverflowpulse.png");
        baseDamage = 11;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        NsfwRunStats.addExcitement(10);
        NsfwRunStats.addFertility(4, 0, false);
        ArkCharMechanicsHelper.applyReapPower(p, 1);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(4);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HighmoreOverflowPulse(); }
}
