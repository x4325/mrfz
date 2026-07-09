package arknsfw.cards.highmore;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkNsfwCard;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.helpers.ArkExposureHelper;
import arknsfw.helpers.ArkGearSetHelper;
import arknsfw.helpers.ArkSafeStats;
import arknsfw.helpers.ArkSensitivity;
import arknsfw.helpers.LieseCompat;
import highmore.core.ColorEnum;

/** 幽潮·纹章引爆 */
public class HighmoreCrestDetonate extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("HighmoreCrestDetonate");

    public HighmoreCrestDetonate() {
        super(ID, 2, CardType.ATTACK, ColorEnum.HIGHMORE_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY, "card_highmore_highmoresilentcaress.png");
        baseDamage = 8;
        baseMagicNumber = magicNumber = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int curses = 0;
        for (AbstractCard c : p.masterDeck.group) { if (c.type == CardType.CURSE) curses++; }
        addToBot(new DamageAction(m, new DamageInfo(p, damage + magicNumber * curses, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        NsfwRunStats.addExcitement(4);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
            applyUpgradeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HighmoreCrestDetonate(); }
}
