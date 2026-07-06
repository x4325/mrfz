package arknsfw.potions.eyja;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.curses.eyja.MagmaEchoCard;
import arknsfw.potions.AbstractArkPotion;
import arknsfw.powers.eyja.VolcanicFlushPower;

public class MagmaEchoDraughtPotion extends AbstractArkPotion {
    public static final String ID = ArkNsfwMod.makeID("MagmaEchoDraughtPotion");

    public MagmaEchoDraughtPotion() {
        super(ID, PotionRarity.RARE, PotionSize.S, PotionColor.SMOKE);
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return;
        }
        NsfwRunStats.addExcitement(getPotency());
        NsfwRunStats.addConception(8, false);
        addToBot(new ApplyPowerAction(p, p, new VolcanicFlushPower(p, 1), 1));
        addToBot(new MakeTempCardInDiscardAction(new MagmaEchoCard(), 1));
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return ascensionLevel >= 2 ? 12 : 10;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new MagmaEchoDraughtPotion();
    }
}
