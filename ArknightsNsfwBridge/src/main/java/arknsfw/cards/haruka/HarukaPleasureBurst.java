package arknsfw.cards.haruka;

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
import haruka.core.ColorEnum;

/** 快感转化：兴奋转伤害 */
public class HarukaPleasureBurst extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("HarukaPleasureBurst");

    public HarukaPleasureBurst() {
        super(ID, 2, CardType.ATTACK, ColorEnum.HARUKA_COLOR, CardRarity.RARE, CardTarget.ENEMY, "card_haruka_harukamoistbarrier.png");
        baseMagicNumber = magicNumber = 25;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int dmg = Math.min(NsfwRunStats.excitement / 5, magicNumber);
        addToBot(new DamageAction(m, new DamageInfo(p, Math.max(3, dmg), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
        NsfwRunStats.addExcitement(-20);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(10);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new HarukaPleasureBurst(); }
}
