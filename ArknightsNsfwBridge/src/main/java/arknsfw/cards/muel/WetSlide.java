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
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import arknsfw.cards.AbstractArkNsfwCard;
import arknsfw.helpers.ArkCharMechanicsHelper;
import Muelsyse.patches.ColorEnum;

public class WetSlide extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("WetSlide");

    public WetSlide() {
        super(ID, 0, CardType.SKILL, Muelsyse.patches.ColorEnum.Muelsyse_COLOR, CardRarity.UNCOMMON, CardTarget.SELF, "card_wet_slide.png");
        baseBlock = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int blk = block + (ArkCharMechanicsHelper.rootageAmount() >= 1 ? 3 : 0);
        addToBot(new GainBlockAction(p, blk));
        NsfwRunStats.addExcitement(10);
        NsfwRunStats.addFertility(3, 0, false);
        if (ArkCharMechanicsHelper.hasManifold()) {
            addToBot(new DrawCardAction(p, 1));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new WetSlide();
    }
}
