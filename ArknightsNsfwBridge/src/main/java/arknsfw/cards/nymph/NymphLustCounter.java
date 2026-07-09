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

/** 迷雾·欲火反击 */
public class NymphLustCounter extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("NymphLustCounter");

    public NymphLustCounter() {
        super(ID, 1, CardType.ATTACK, ColorEnum.NYMPH_COLOR, CardRarity.COMMON, CardTarget.ENEMY, "card_nymph_nymphblushmark.png");
        baseDamage = 8;
        baseMagicNumber = magicNumber = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean debuffed = false;
        for (com.megacrit.cardcrawl.powers.AbstractPower pw : p.powers) {
            if (pw.type == com.megacrit.cardcrawl.powers.AbstractPower.PowerType.DEBUFF) { debuffed = true; break; }
        }
        addToBot(new DamageAction(m, new DamageInfo(p, damage + (debuffed ? magicNumber : 0), damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeMagicNumber(2);
            applyUpgradeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() { return new NymphLustCounter(); }
}
