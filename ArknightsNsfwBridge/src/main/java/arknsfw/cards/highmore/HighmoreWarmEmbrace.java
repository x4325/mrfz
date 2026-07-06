package arknsfw.cards.highmore;

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
import highmore.core.ColorEnum;
import highmore.powers.ReapPower;

public class HighmoreWarmEmbrace extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("HighmoreWarmEmbrace");

    public HighmoreWarmEmbrace() {
        super(ID, 1, CardType.SKILL, ColorEnum.HIGHMORE_COLOR, CardRarity.UNCOMMON, CardTarget.SELF, "card_highmore_highmorewarmembrace.png");
        
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        NsfwRunStats.addExcitement(10);
        ArkCharMechanicsHelper.applyReapPower(p, 2);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            // no upgrade
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HighmoreWarmEmbrace(); }
}
