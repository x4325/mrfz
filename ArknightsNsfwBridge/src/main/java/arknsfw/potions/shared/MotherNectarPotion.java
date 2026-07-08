package arknsfw.potions.shared;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.potions.AbstractArkPotion;

/** 母乳蜜露：回复 12 生命（海沫改为格挡），受孕 +10；怀孕时改为孕程 +10。 */
public class MotherNectarPotion extends AbstractArkPotion {
    public static final String ID = ArkNsfwMod.makeID("MotherNectarPotion");

    public MotherNectarPotion() {
        super(ID, PotionRarity.UNCOMMON, PotionSize.M, PotionColor.ANCIENT);
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return;
        }
        ArkCharMechanicsHelper.healOrBlock(p, getPotency());
        if (NsfwRunStats.pregnant) {
            NsfwRunStats.addPregnancyProgress(10);
        } else {
            NsfwRunStats.addConception(10, false);
        }
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return 12;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new MotherNectarPotion();
    }
}
