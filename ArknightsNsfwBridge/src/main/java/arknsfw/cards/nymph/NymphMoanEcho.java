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

/** 迷雾·娇声回响 */
public class NymphMoanEcho extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("NymphMoanEcho");

    public NymphMoanEcho() {
        super(ID, 1, CardType.ATTACK, ColorEnum.NYMPH_COLOR, CardRarity.COMMON, CardTarget.ENEMY, "card_nymph_nymphdeepresonance.png");
        baseDamage = 6;
        baseMagicNumber = magicNumber = 6;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int dmg = damage + (NsfwRunStats.pregnant ? magicNumber : 0);
        addToBot(new DamageAction(m, new DamageInfo(p, dmg, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
            applyUpgradeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() { return new NymphMoanEcho(); }
}
