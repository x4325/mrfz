package arknsfw.potions.muel;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.potions.AbstractArkPotion;
import arknsfw.powers.muel.BubbleGagPower;

/** 缪尔专用：兴奋 + 泡沫封口（非龙娘丧威）。 */
public class BubbleShameMistPotion extends AbstractArkPotion {
    public static final String ID = ArkNsfwMod.makeID("BubbleShameMistPotion");

    public BubbleShameMistPotion() {
        super(ID, PotionRarity.UNCOMMON, PotionSize.S, PotionColor.POISON);
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return;
        }
        NsfwRunStats.addExcitement(getPotency());
        addToBot(new ApplyPowerAction(p, p, new BubbleGagPower(p, 2), 2));
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return ascensionLevel >= 2 ? 22 : 20;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new BubbleShameMistPotion();
    }
}
