package arknsfw.cards.nymph;

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
import nymph.core.ColorEnum;

/** 迷雾·敏感引爆 */
public class NymphSenseDetonate extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("NymphSenseDetonate");

    public NymphSenseDetonate() {
        super(ID, 1, CardType.ATTACK, ColorEnum.NYMPH_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY, "card_nymph_nymphmoistbarrier.png");
        baseDamage = 8;
        baseMagicNumber = magicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int dmg = damage + magicNumber * ArkSensitivity.points();
        addToBot(new DamageAction(m, new DamageInfo(p, dmg, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        NsfwRunStats.addExcitement(4);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeMagicNumber(1);
            applyUpgradeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() { return new NymphSenseDetonate(); }
}
