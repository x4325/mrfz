package arknsfw.cards.muel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkNsfwCard;
import arknsfw.helpers.ArkCharMechanicsHelper;
import Muelsyse.patches.ColorEnum;

public class SeedSpray extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("SeedSpray");

    public SeedSpray() {
        super(ID, 1, CardType.SKILL, Muelsyse.patches.ColorEnum.Muelsyse_COLOR, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY, "card_seed_spray.png");
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int weak = magicNumber + (ArkCharMechanicsHelper.hasManifold() ? 1 : 0);
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped()) {
                addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, weak, false), weak));
            }
        }
        NsfwRunStats.addExcitement(8);
        NsfwRunStats.addFertility(5 + ArkCharMechanicsHelper.rootageAmount(), 3, false);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new SeedSpray();
    }
}
