package arknsfw.potions.shared;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.LieseCompat;
import arknsfw.potions.AbstractArkPotion;

/** 绝顶引爆剂：兴奋直接拉满至高潮阈值（下回合必然失控），获得 2 费与 2 抽。 */
public class ClimaxTriggerPotion extends AbstractArkPotion {
    public static final String ID = ArkNsfwMod.makeID("ClimaxTriggerPotion");

    public ClimaxTriggerPotion() {
        super(ID, PotionRarity.RARE, PotionSize.M, PotionColor.FIRE);
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return;
        }
        int threshold = LieseCompat.climaxThreshold();
        if (threshold > 0 && NsfwRunStats.excitement < threshold) {
            NsfwRunStats.addExcitement(threshold - NsfwRunStats.excitement);
        }
        addToBot(new GainEnergyAction(2));
        addToBot(new com.megacrit.cardcrawl.actions.common.DrawCardAction(p, 2));
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return 2;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new ClimaxTriggerPotion();
    }
}
