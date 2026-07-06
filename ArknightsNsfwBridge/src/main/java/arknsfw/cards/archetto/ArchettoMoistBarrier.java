package arknsfw.cards.archetto;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkNsfwCard;
import arknsfw.helpers.ArkCharMechanicsHelper;
import archetto.core.ColorEnum;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;

public class ArchettoMoistBarrier extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("ArchettoMoistBarrier");

    public ArchettoMoistBarrier() {
        super(ID, 1, CardType.SKILL, ColorEnum.ARCHETTO_COLOR, CardRarity.COMMON, CardTarget.SELF, "card_archetto_archettomoistbarrier.png");
        baseBlock = 10;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        NsfwRunStats.addExcitement(6);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(4); baseBlock=10;
        }
    }

    @Override
    public AbstractCard makeCopy() { return new ArchettoMoistBarrier(); }
}
