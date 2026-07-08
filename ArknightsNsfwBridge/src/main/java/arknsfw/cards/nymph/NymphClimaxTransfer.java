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

/** 迷雾·高潮转移 */
public class NymphClimaxTransfer extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("NymphClimaxTransfer");

    public NymphClimaxTransfer() {
        super(ID, 1, CardType.SKILL, ColorEnum.NYMPH_COLOR, CardRarity.RARE, CardTarget.ALL_ENEMY, "card_nymph_nymphtenderkiss.png");
        baseMagicNumber = magicNumber = 20;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int x = NsfwRunStats.excitement;
        if (x > 0) {
            NsfwRunStats.addExcitement(-x);
            int dmg = Math.min(magicNumber, x / 4);
            if (dmg > 0) {
                addToBot(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(dmg, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(10);
            applyUpgradeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() { return new NymphClimaxTransfer(); }
}
