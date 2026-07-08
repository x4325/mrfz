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
import arknsfw.helpers.LieseCompat;
import nymph.core.ColorEnum;

/** 迷雾·淫纹过载 */
public class NymphCrestOverload extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("NymphCrestOverload");

    public NymphCrestOverload() {
        super(ID, -1, CardType.ATTACK, ColorEnum.NYMPH_COLOR, CardRarity.RARE, CardTarget.ENEMY, "card_nymph_nymphwarmembrace.png");
        baseMagicNumber = magicNumber = 9;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect = this.energyOnUse;
        if (p.hasRelic("Chemical X")) { effect += 2; }
        if (effect > 0) {
            addToBot(new DamageAction(m, new DamageInfo(p, magicNumber * effect, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
            NsfwRunStats.addExcitement(4 * effect);
        }
        if (!this.freeToPlayOnce) { p.energy.use(EnergyPanel.totalCount); }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(3);
            applyUpgradeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() { return new NymphCrestOverload(); }
}
