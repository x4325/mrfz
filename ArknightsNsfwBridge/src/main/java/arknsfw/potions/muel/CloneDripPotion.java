package arknsfw.potions.muel;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.potions.AbstractArkPotion;

public class CloneDripPotion extends AbstractArkPotion {
    public static final String ID = ArkNsfwMod.makeID("CloneDripPotion");

    public CloneDripPotion() {
        super(ID, PotionRarity.UNCOMMON, PotionSize.M, PotionColor.ANCIENT);
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return;
        }
        int amt = getPotency();
        NsfwRunStats.addExcitement(amt);
        NsfwRunStats.addConception(5, false);
        if (ArkCharMechanicsHelper.hasManifold()) {
            ArkCharMechanicsHelper.boostManifold(1);
        } else {
            ArkCharMechanicsHelper.applyRootage(p, 1);
        }
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return ascensionLevel >= 2 ? 16 : 12;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new CloneDripPotion();
    }
}
