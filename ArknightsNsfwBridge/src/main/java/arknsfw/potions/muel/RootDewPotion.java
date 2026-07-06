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

public class RootDewPotion extends AbstractArkPotion {
    public static final String ID = ArkNsfwMod.makeID("RootDewPotion");

    public RootDewPotion() {
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
        ArkCharMechanicsHelper.applyRootage(p, 1);
        addToBot(new ApplyPowerAction(p, p, new HydrationPower(p, 1), 1));
        NsfwRunStats.addConception(6, false);
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return ascensionLevel >= 2 ? 4 : 5;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new RootDewPotion();
    }
}
