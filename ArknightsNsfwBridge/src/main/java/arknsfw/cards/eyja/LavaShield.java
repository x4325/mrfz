package arknsfw.cards.eyja;

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
import Eyjafjalla.modcore.ColorEnum;

public class LavaShield extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("LavaShield");

    public LavaShield() {
        super(ID, 1, CardType.SKILL, ColorEnum.Eyjafjalla_COLOR, CardRarity.COMMON, CardTarget.SELF, "card_lava_shield.png");
        baseBlock = 10;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int blk = block + Math.min(8, ArkCharMechanicsHelper.cloudCardCount() * 2);
        addToBot(new GainBlockAction(p, blk));
        NsfwRunStats.addExcitement(6);
        if (ArkCharMechanicsHelper.cloudEnergy() >= 2) {
            ArkCharMechanicsHelper.applyFireMarkPower(p, 1);
        }
        if (ArkCharMechanicsHelper.cloudCardCount() == 0) {
            ArkCharMechanicsHelper.gainCloudEnergy(1);
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(4);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new LavaShield();
    }
}
