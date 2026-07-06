package arknsfw.potions.eyja;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.potions.AbstractArkPotion;
import arknsfw.powers.eyja.AshShamePower;

/** 艾雅专用：兴奋 + 灰烬羞怯（非龙娘丧威）。 */
public class AshShameMistPotion extends AbstractArkPotion {
    public static final String ID = ArkNsfwMod.makeID("AshShameMistPotion");

    public AshShameMistPotion() {
        super(ID, PotionRarity.UNCOMMON, PotionSize.S, PotionColor.POISON);
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return;
        }
        NsfwRunStats.addExcitement(getPotency());
        addToBot(new ApplyPowerAction(p, p, new AshShamePower(p, 2), 2));
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return ascensionLevel >= 2 ? 22 : 20;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new AshShameMistPotion();
    }
}
