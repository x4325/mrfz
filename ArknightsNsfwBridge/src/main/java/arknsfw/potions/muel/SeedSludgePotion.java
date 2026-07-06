package arknsfw.potions.muel;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.curses.muel.SeedWombCard;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.potions.AbstractArkPotion;

public class SeedSludgePotion extends AbstractArkPotion {
    public static final String ID = ArkNsfwMod.makeID("SeedSludgePotion");

    public SeedSludgePotion() {
        super(ID, PotionRarity.RARE, PotionSize.S, PotionColor.SMOKE);
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return;
        }
        NsfwRunStats.addExcitement(getPotency());
        if (NsfwRunStats.pregnant) {
            NsfwRunStats.addPregnancyProgress(15);
        } else {
            NsfwRunStats.addFertility(12, 10, true);
        }
        addToBot(new MakeTempCardInDiscardAction(new SeedWombCard(), 1));
        if (ArkCharMechanicsHelper.hasManifold()) {
            ArkCharMechanicsHelper.boostManifold(1);
        }
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return ascensionLevel >= 2 ? 16 : 12;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new SeedSludgePotion();
    }
}
