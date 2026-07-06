package arknsfw.cards.curses.archetto;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkCurseCard;
import arknsfw.helpers.ArkCharMechanicsHelper;

public class ArchettoShameContractCard extends AbstractArkCurseCard {
    public static final String ID = ArkNsfwMod.makeID("ArchettoShameContractCard");

    public ArchettoShameContractCard() {
        super(ID, "curse_archetto_archettoshamecontractcard.png");
    }

    @Override
    public void triggerWhenDrawn() {
        NsfwRunStats.addExcitement(10); com.megacrit.cardcrawl.dungeons.AbstractDungeon.player.damage(new com.megacrit.cardcrawl.cards.DamageInfo(null, 3));
    }

    @Override public void use(AbstractPlayer p, AbstractMonster m) {}
    @Override public void upgrade() {}
    @Override public AbstractCard makeCopy() { return new ArchettoShameContractCard(); }
}
