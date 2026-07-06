package arknsfw.potions.muel;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.curses.muel.FloodMarkCard;
import arknsfw.potions.AbstractArkPotion;
import arknsfw.powers.muel.LeakPower;

public class FloodMarkDraughtPotion extends AbstractArkPotion {
    public static final String ID = ArkNsfwMod.makeID("FloodMarkDraughtPotion");

    public FloodMarkDraughtPotion() {
        super(ID, PotionRarity.UNCOMMON, PotionSize.S, PotionColor.POISON);
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return;
        }
        if (p.currentBlock > 0) {
            p.loseBlock(p.currentBlock);
        }
        NsfwRunStats.addExcitement(getPotency());
        NsfwRunStats.addConception(getPotency() / 2 + 4, false);
        addToBot(new ApplyPowerAction(p, p, new LeakPower(p, 2), 2));
        addToBot(new MakeTempCardInDiscardAction(new FloodMarkCard(), 1));
    }

    @Override
    public void initializeData() {
        super.initializeData();
        potency = getPotency();
        description = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1]
                + (potency / 2 + 4) + potionStrings.DESCRIPTIONS[2];
        tips.clear();
        tips.add(new com.megacrit.cardcrawl.helpers.PowerTip(name, description));
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return ascensionLevel >= 2 ? 12 : 10;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new FloodMarkDraughtPotion();
    }
}
