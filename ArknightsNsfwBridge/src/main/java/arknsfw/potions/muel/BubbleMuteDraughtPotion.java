package arknsfw.potions.muel;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.curses.muel.BubbleMuteCard;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.potions.AbstractArkPotion;
import arknsfw.powers.muel.BubbleGagPower;

public class BubbleMuteDraughtPotion extends AbstractArkPotion {
    public static final String ID = ArkNsfwMod.makeID("BubbleMuteDraughtPotion");

    public BubbleMuteDraughtPotion() {
        super(ID, PotionRarity.UNCOMMON, PotionSize.S, PotionColor.SMOKE);
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return;
        }
        int gag = ArkCharMechanicsHelper.isCultivating() ? 3 : 2;
        NsfwRunStats.addExcitement(getPotency());
        addToBot(new ApplyPowerAction(p, p, new BubbleGagPower(p, gag), gag));
        addToBot(new MakeTempCardInDiscardAction(new BubbleMuteCard(), 1));
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return ascensionLevel >= 2 ? 14 : 12;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new BubbleMuteDraughtPotion();
    }
}
