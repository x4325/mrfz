package arknsfw.potions.eyja;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.curses.eyja.AshCollarCard;
import arknsfw.potions.AbstractArkPotion;
import arknsfw.powers.eyja.AshShamePower;

public class CollarSootPotion extends AbstractArkPotion {
    public static final String ID = ArkNsfwMod.makeID("CollarSootPotion");

    public CollarSootPotion() {
        super(ID, PotionRarity.UNCOMMON, PotionSize.S, PotionColor.SMOKE);
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return;
        }
        NsfwRunStats.addExcitement(getPotency());
        addToBot(new ApplyPowerAction(p, p, new AshShamePower(p, 2), 2));
        addToBot(new MakeTempCardInDiscardAction(new AshCollarCard(), 1));
        p.damage(new DamageInfo(null, getPotency() / 5, DamageInfo.DamageType.HP_LOSS));
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return ascensionLevel >= 2 ? 20 : 15;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new CollarSootPotion();
    }
}
