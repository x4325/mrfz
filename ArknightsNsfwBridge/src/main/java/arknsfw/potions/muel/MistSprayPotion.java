package arknsfw.potions.muel;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.potions.AbstractArkPotion;
import arknsfw.powers.muel.CloneHazePower;
import arknsfw.powers.muel.HydrationPower;

public class MistSprayPotion extends AbstractArkPotion {
    public static final String ID = ArkNsfwMod.makeID("MistSprayPotion");

    public MistSprayPotion() {
        super(ID, PotionRarity.COMMON, PotionSize.S, PotionColor.BLUE);
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return;
        }
        NsfwRunStats.addExcitement(getPotency());
        NsfwRunStats.addConception(3, false);
        addToBot(new ApplyPowerAction(p, p, new CloneHazePower(p, 1), 1));
        addToBot(new ApplyPowerAction(p, p, new HydrationPower(p, 1), 1));
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return ascensionLevel >= 2 ? 8 : 10;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new MistSprayPotion();
    }
}
