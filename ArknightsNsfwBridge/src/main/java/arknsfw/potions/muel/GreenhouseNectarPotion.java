package arknsfw.potions.muel;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.potions.AbstractArkPotion;
import arknsfw.powers.muel.HydrationPower;

public class GreenhouseNectarPotion extends AbstractArkPotion {
    public static final String ID = ArkNsfwMod.makeID("GreenhouseNectarPotion");

    public GreenhouseNectarPotion() {
        super(ID, PotionRarity.RARE, PotionSize.M, PotionColor.FAIRY);
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return;
        }
        int heal = getPotency();
        addToBot(new HealAction(p, p, heal));
        addToBot(new ApplyPowerAction(p, p, new HydrationPower(p, 2), 2));
        NsfwRunStats.addConception(6, false);
        if (ArkCharMechanicsHelper.isCultivating()) {
            ArkCharMechanicsHelper.applyRootage(p, 1);
        } else {
            NsfwRunStats.addConception(4, false);
        }
    }

    @Override
    public void initializeData() {
        super.initializeData();
        potency = getPotency();
        description = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1];
        tips.clear();
        tips.add(new com.megacrit.cardcrawl.helpers.PowerTip(name, description));
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return ascensionLevel >= 2 ? 4 : 5;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new GreenhouseNectarPotion();
    }
}
