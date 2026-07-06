package arknsfw.cards.curses.haruka;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkCurseCard;
import arknsfw.helpers.ArkCharMechanicsHelper;

public class HarukaShameContractCard extends AbstractArkCurseCard {
    public static final String ID = ArkNsfwMod.makeID("HarukaShameContractCard");

    public HarukaShameContractCard() {
        super(ID, "curse_haruka_harukashamecontractcard.png");
    }

    @Override
    public void triggerWhenDrawn() {
        NsfwRunStats.addExcitement(10); com.megacrit.cardcrawl.dungeons.AbstractDungeon.player.damage(new com.megacrit.cardcrawl.cards.DamageInfo(null, 3));
    }

    @Override public void use(AbstractPlayer p, AbstractMonster m) {}
    @Override public void upgrade() {}
    @Override public AbstractCard makeCopy() { return new HarukaShameContractCard(); }
}
