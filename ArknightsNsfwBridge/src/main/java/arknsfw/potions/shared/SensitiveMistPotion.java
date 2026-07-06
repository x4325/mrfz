package arknsfw.potions.shared;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.helpers.ArkCharDebuffs;
import arknsfw.helpers.ArkCharMechanicsHelper;
import arknsfw.helpers.ArkDebuffHelper;
import arknsfw.potions.AbstractArkPotion;

/** 敏感药雾：角色资源+3，兴奋+12 */
public class SensitiveMistPotion extends AbstractArkPotion {
    public static final String ID = ArkNsfwMod.makeID("SensitiveMistPotion");

    public SensitiveMistPotion() {
        super(ID, PotionRarity.UNCOMMON, PotionSize.S, PotionColor.SMOKE);
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) {
            return;
        }
        NsfwRunStats.addExcitement(12);
        ArkCharMechanicsHelper.applyReapPower(p, 3);
        ArkCharMechanicsHelper.applyFocusPower(p, 3);
        ArkCharMechanicsHelper.applyAimPower(p, 3);
        ArkCharMechanicsHelper.applyPyroPower(p, 3);
        ArkCharMechanicsHelper.applyHexPower(p, 3);
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return 12;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new SensitiveMistPotion();
    }
}
