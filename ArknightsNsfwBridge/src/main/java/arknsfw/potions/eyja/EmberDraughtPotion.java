package arknsfw.potions.eyja;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.potions.AbstractArkPotion;

public class EmberDraughtPotion extends AbstractArkPotion {
    public static final String ID = ArkNsfwMod.makeID("EmberDraughtPotion");

    public EmberDraughtPotion() {
        super(ID, PotionRarity.UNCOMMON, PotionSize.M, PotionColor.ENERGY);
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return;
        }
        int amt = getPotency();
        NsfwRunStats.addExcitement(amt);
        NsfwRunStats.addConception(amt / 4, false);
        ArkCharMechanicsHelper.applyFireMarkPower(p, 1);
        ArkCharMechanicsHelper.gainCloudEnergy(2);
        for (AbstractCard c : p.hand.group) {
            if (!ArkCharMechanicsHelper.cardHasFireMark(c)) {
                ArkCharMechanicsHelper.markPyrobreath(c);
                break;
            }
        }
    }

    @Override
    public void initializeData() {
        super.initializeData();
        potency = getPotency();
        int conceive = potency / 4;
        description = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1] + conceive
                + potionStrings.DESCRIPTIONS[2];
        tips.clear();
        tips.add(new com.megacrit.cardcrawl.helpers.PowerTip(name, description));
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return ascensionLevel >= 2 ? 14 : 12;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new EmberDraughtPotion();
    }
}
