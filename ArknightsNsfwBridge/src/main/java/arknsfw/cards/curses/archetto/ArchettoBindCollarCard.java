package arknsfw.cards.curses.archetto;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import liesecore.helpers.NsfwRunStats;
import arknsfw.ArkNsfwMod;
import arknsfw.cards.AbstractArkCurseCard;
import arknsfw.helpers.ArkCharMechanicsHelper;

public class ArchettoBindCollarCard extends AbstractArkCurseCard {
    public static final String ID = ArkNsfwMod.makeID("ArchettoBindCollarCard");

    public ArchettoBindCollarCard() {
        super(ID, "curse_archetto_archettobindcollarcard.png");
    }

    @Override
    public void triggerWhenDrawn() {
        NsfwRunStats.addExcitement(6);
        com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(com.megacrit.cardcrawl.dungeons.AbstractDungeon.player, null, new com.megacrit.cardcrawl.powers.WeakPower(com.megacrit.cardcrawl.dungeons.AbstractDungeon.player, 1, false), 1));
    }

    @Override public void use(AbstractPlayer p, AbstractMonster m) {}
    @Override public void upgrade() {}
    @Override public AbstractCard makeCopy() { return new ArchettoBindCollarCard(); }
}
