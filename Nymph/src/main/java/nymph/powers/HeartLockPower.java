package nymph.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import nymph.NymphMod;

public class HeartLockPower extends AbstractPower {
    public static final String POWER_ID = NymphMod.makeID("HeartLockPower");
    private static final PowerStrings ps = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public HeartLockPower(AbstractCreature owner, int amount) {
        name = ps.NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        type = PowerType.DEBUFF;
        isTurnBased = true;
        updateDescription();
    }
    public void atStartOfTurn() {
        if (!owner.isPlayer && amount > 0) {
            addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, -amount), -amount));
            addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
        }
    }
    public void updateDescription() { description = ps.DESCRIPTIONS[0] + amount + ps.DESCRIPTIONS[1]; }
}
