package arknsfw.potions.eyja;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.potions.AbstractArkPotion;

public class PyroAphroPotion extends AbstractArkPotion {
    public static final String ID = ArkNsfwMod.makeID("PyroAphroPotion");

    public PyroAphroPotion() {
        super(ID, PotionRarity.UNCOMMON, PotionSize.M, PotionColor.ENERGY);
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return;
        }
        int amt = getPotency();
        ArkCharMechanicsHelper.applyFireMarkPower(p, 1);
        NsfwRunStats.addExcitement(amt);
        NsfwRunStats.addConception(amt / 3, false);
        ArkCharMechanicsHelper.gainCloudEnergy(1);
    }

    @Override
    public void initializeData() {
        super.initializeData();
        potency = getPotency();
        int conceive = potency / 3;
        description = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1] + conceive
                + potionStrings.DESCRIPTIONS[2];
        tips.clear();
        tips.add(new com.megacrit.cardcrawl.helpers.PowerTip(name, description));
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return ascensionLevel >= 2 ? 18 : 15;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new PyroAphroPotion();
    }
}
