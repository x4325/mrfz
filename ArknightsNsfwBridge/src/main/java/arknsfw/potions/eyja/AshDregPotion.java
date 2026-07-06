package arknsfw.potions.eyja;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.potions.AbstractArkPotion;
import arknsfw.powers.eyja.AshShamePower;

public class AshDregPotion extends AbstractArkPotion {
    public static final String ID = ArkNsfwMod.makeID("AshDregPotion");

    public AshDregPotion() {
        super(ID, PotionRarity.UNCOMMON, PotionSize.S, PotionColor.SMOKE);
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return;
        }
        int shame = 2;
        int excite = getPotency();
        int hpLoss = getPotency() / 4;
        NsfwRunStats.addExcitement(excite);
        addToBot(new ApplyPowerAction(p, p, new AshShamePower(p, shame), shame));
        p.damage(new DamageInfo(null, hpLoss, DamageInfo.DamageType.HP_LOSS));
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return ascensionLevel >= 2 ? 24 : 20;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new AshDregPotion();
    }
}
