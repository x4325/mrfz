package arknsfw.potions.shared;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.potions.AbstractArkPotion;

/** 拘束润滑剂：获得 10 格挡、抽 2 张牌，兴奋 +8——束缚也能滑顺应对。 */
public class LubricantPotion extends AbstractArkPotion {
    public static final String ID = ArkNsfwMod.makeID("LubricantPotion");

    public LubricantPotion() {
        super(ID, PotionRarity.COMMON, PotionSize.S, PotionColor.FAIRY);
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return;
        }
        addToBot(new GainBlockAction(p, getPotency()));
        addToBot(new DrawCardAction(p, 2));
        NsfwRunStats.addExcitement(8);
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return 10;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new LubricantPotion();
    }
}
