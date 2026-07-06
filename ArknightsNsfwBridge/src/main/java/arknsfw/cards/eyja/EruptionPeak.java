package arknsfw.cards.eyja;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkNsfwCard;
import arknsfw.helpers.ArkCharMechanicsHelper;
import Eyjafjalla.modcore.ColorEnum;

public class EruptionPeak extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("EruptionPeak");

    public EruptionPeak() {
        super(ID, 2, CardType.ATTACK, ColorEnum.Eyjafjalla_COLOR, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY, "card_eruption_peak.png");
        baseDamage = 9;
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int bonus = ArkCharMechanicsHelper.fireMarkPowerAmount() * 2
                + Math.min(9, ArkCharMechanicsHelper.cloudCardCount() * 3);
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped()) {
                addToBot(new DamageAction(mo, new DamageInfo(p, damage + bonus, DamageInfo.DamageType.NORMAL),
                        AbstractGameAction.AttackEffect.FIRE));
            }
        }
        NsfwRunStats.addExcitement(15);
        NsfwRunStats.addFertility(6, 4, true);
        ArkCharMechanicsHelper.markPyrobreath(this);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EruptionPeak();
    }
}
