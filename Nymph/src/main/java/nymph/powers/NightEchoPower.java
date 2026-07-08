package nymph.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import nymph.NymphMod;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/** 梦魇回响：每回合开始对随机敌人施加 amount 层咒灵。 */
public class NightEchoPower extends AbstractPower {
    public static final String POWER_ID = NymphMod.makeID("NightEchoPower");
    private static final PowerStrings ps = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public NightEchoPower(AbstractCreature owner, int amount) {
        name = ps.NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        type = PowerType.BUFF;
        isTurnBased = false;
        updateDescription();
        loadRegion("nightmare");
    }

    @Override
    public void atStartOfTurn() {
        AbstractMonster pick = AbstractDungeon.getMonsters() == null ? null
                : AbstractDungeon.getMonsters().getRandomMonster(true);
        if (pick != null) {
            flash();
            addToBot(new ApplyPowerAction(pick, owner, new HexPower(pick, amount), amount));
        }
    }

    public void stackPower(int n) { super.stackPower(n); updateDescription(); }
    public void updateDescription() { description = ps.DESCRIPTIONS[0] + amount + ps.DESCRIPTIONS[1]; }
}
