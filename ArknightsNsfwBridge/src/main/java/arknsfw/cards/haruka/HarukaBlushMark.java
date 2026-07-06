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
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;

public class HarukaBlushMark extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("HarukaBlushMark");

    public HarukaBlushMark() {
        super(ID, 1, CardType.SKILL, ColorEnum.HARUKA_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY, "card_haruka_harukablushmark.png");
        
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, 1, false), 1));
        NsfwRunStats.addExcitement(8);
        NsfwRunStats.addConception(3, false);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            // no upgrade
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HarukaBlushMark(); }
}
