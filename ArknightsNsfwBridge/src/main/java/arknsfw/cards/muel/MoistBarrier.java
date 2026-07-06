package arknsfw.cards.muel;

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
import Muelsyse.patches.ColorEnum;
import arknsfw.powers.muel.HydrationPower;

public class MoistBarrier extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("MoistBarrier");

    public MoistBarrier() {
        super(ID, 1, CardType.SKILL, Muelsyse.patches.ColorEnum.Muelsyse_COLOR, CardRarity.COMMON, CardTarget.SELF, "card_moist_barrier.png");
        baseBlock = 11;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int blk = block + (ArkCharMechanicsHelper.hasManifold() ? 3 : 0);
        addToBot(new GainBlockAction(p, blk));
        int hydration = (upgraded ? 2 : 1) + (ArkCharMechanicsHelper.rootageAmount() >= 1 ? 1 : 0);
        addToBot(new ApplyPowerAction(p, p, new HydrationPower(p, hydration), hydration));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(4);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new MoistBarrier();
    }
}
