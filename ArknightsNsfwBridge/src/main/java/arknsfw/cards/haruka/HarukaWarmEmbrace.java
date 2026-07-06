package arknsfw.cards.haruka;

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
import haruka.core.ColorEnum;
import haruka.powers.PyroPower;

public class HarukaWarmEmbrace extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("HarukaWarmEmbrace");

    public HarukaWarmEmbrace() {
        super(ID, 1, CardType.SKILL, ColorEnum.HARUKA_COLOR, CardRarity.UNCOMMON, CardTarget.SELF, "card_haruka_harukawarmembrace.png");
        
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        NsfwRunStats.addExcitement(10);
        ArkCharMechanicsHelper.applyPyroPower(p, 2);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            // no upgrade
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HarukaWarmEmbrace(); }
}
