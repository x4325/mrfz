package arknsfw.powers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharMechanicsHelper;

/** 淫乱状态：每回合开始获得 amount 层角色资源，同时兴奋 +8。 */
public class LewdTrancePower extends AbstractPower {
    public static final String POWER_ID = ArkNsfwMod.makeID("LewdTrancePower");
    private static final PowerStrings STR = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public LewdTrancePower(AbstractCreature owner, int amount) {
        name = STR.NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        type = PowerType.BUFF;
        isTurnBased = false;
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        flash();
        arknsfw.helpers.ArkSafeStats.addExcitementDeferred(8);
        if (owner instanceof AbstractPlayer) {
            AbstractPlayer p = (AbstractPlayer) owner;
            ArkCharMechanicsHelper.applyReapPower(p, amount);
            ArkCharMechanicsHelper.applyFocusPower(p, amount);
            ArkCharMechanicsHelper.applyAimPower(p, amount);
            ArkCharMechanicsHelper.applyPyroPower(p, amount);
            ArkCharMechanicsHelper.applyHexPower(p, amount);
        }
    }

    public void stackPower(int n) { super.stackPower(n); updateDescription(); }
    public void updateDescription() { description = STR.DESCRIPTIONS[0] + amount + STR.DESCRIPTIONS[1]; }
}
