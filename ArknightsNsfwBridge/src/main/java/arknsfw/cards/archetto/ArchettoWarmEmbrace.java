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
import archetto.powers.AimPower;

public class ArchettoWarmEmbrace extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("ArchettoWarmEmbrace");

    public ArchettoWarmEmbrace() {
        super(ID, 1, CardType.SKILL, ColorEnum.ARCHETTO_COLOR, CardRarity.UNCOMMON, CardTarget.SELF, "card_archetto_archettowarmembrace.png");
        
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        NsfwRunStats.addExcitement(10);
        ArkCharMechanicsHelper.applyAimPower(p, 2);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            // no upgrade
        }
    }

    @Override
    public AbstractCard makeCopy() { return new ArchettoWarmEmbrace(); }
}
