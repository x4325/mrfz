package arknsfw.potions.muel;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.potions.AbstractArkPotion;
import arknsfw.powers.muel.HydrationPower;

public class BubbleSerumPotion extends AbstractArkPotion {
    public static final String ID = ArkNsfwMod.makeID("BubbleSerumPotion");

    public BubbleSerumPotion() {
        super(ID, PotionRarity.COMMON, PotionSize.M, PotionColor.BLUE);
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return;
        }
        int block = getPotency();
        addToBot(new GainBlockAction(p, block));
        NsfwRunStats.addExcitement(block - 2);
        NsfwRunStats.addConception(4, false);
        addToBot(new ApplyPowerAction(p, p, new HydrationPower(p, 1), 1));
    }

    @Override
    public void initializeData() {
        super.initializeData();
        potency = getPotency();
        int block = getPotency();
        description = potionStrings.DESCRIPTIONS[0] + block + potionStrings.DESCRIPTIONS[1] + (block - 2)
                + potionStrings.DESCRIPTIONS[2];
        tips.clear();
        tips.add(new com.megacrit.cardcrawl.helpers.PowerTip(name, description));
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return ascensionLevel >= 2 ? 8 : 10;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new BubbleSerumPotion();
    }
}
