package arknsfw.cards.archetto;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
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

/** 放纵一击：超模伤害换兴奋 */
public class ArchettoIndulgentStrike extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("ArchettoIndulgentStrike");

    public ArchettoIndulgentStrike() {
        super(ID, 1, CardType.ATTACK, ColorEnum.ARCHETTO_COLOR, CardRarity.UNCOMMON, CardTarget.ENEMY, "card_archetto_archettomoistbarrier.png");
        baseDamage = 14;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        NsfwRunStats.addExcitement(15);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(5);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new ArchettoIndulgentStrike(); }
}
