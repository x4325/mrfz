package arknsfw.potions.muel;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.potions.AbstractArkPotion;
import arknsfw.powers.muel.LeakPower;

public class FloodWastePotion extends AbstractArkPotion {
    public static final String ID = ArkNsfwMod.makeID("FloodWastePotion");

    public FloodWastePotion() {
        super(ID, PotionRarity.UNCOMMON, PotionSize.S, PotionColor.POISON);
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return;
        }
        int leak = 2;
        if (p.currentBlock > 0) {
            p.loseBlock(p.currentBlock);
        }
        NsfwRunStats.addExcitement(getPotency());
        NsfwRunStats.addConception(getPotency() / 2, false);
        addToBot(new ApplyPowerAction(p, p, new LeakPower(p, leak), leak));
    }

    @Override
    public void initializeData() {
        super.initializeData();
        potency = getPotency();
        description = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1] + (potency / 2)
                + potionStrings.DESCRIPTIONS[2];
        tips.clear();
        tips.add(new com.megacrit.cardcrawl.helpers.PowerTip(name, description));
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return ascensionLevel >= 2 ? 14 : 10;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new FloodWastePotion();
    }
}
