package arknsfw.potions.eyja;

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
import arknsfw.powers.eyja.CoreFeverPower;

public class LavaBloomPotion extends AbstractArkPotion {
    public static final String ID = ArkNsfwMod.makeID("LavaBloomPotion");

    public LavaBloomPotion() {
        super(ID, PotionRarity.RARE, PotionSize.M, PotionColor.ANCIENT);
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return;
        }
        int heal = getPotency();
        addToBot(new HealAction(p, p, heal));
        NsfwRunStats.addExcitement(heal + 6);
        NsfwRunStats.addConception(5, false);
        ArkCharMechanicsHelper.gainCloudEnergy(2);
        addToBot(new ApplyPowerAction(p, p, new CoreFeverPower(p, 1), 1));
    }

    @Override
    public void initializeData() {
        super.initializeData();
        potency = getPotency();
        int heal = potency;
        description = potionStrings.DESCRIPTIONS[0] + heal + potionStrings.DESCRIPTIONS[1] + (heal + 6)
                + potionStrings.DESCRIPTIONS[2];
        tips.clear();
        tips.add(new com.megacrit.cardcrawl.helpers.PowerTip(name, description));
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return ascensionLevel >= 2 ? 6 : 8;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new LavaBloomPotion();
    }
}
