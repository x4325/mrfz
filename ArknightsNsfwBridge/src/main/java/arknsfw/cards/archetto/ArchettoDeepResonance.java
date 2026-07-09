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
import com.megacrit.cardcrawl.actions.common.DamageAction;
import archetto.powers.AimPower;

public class ArchettoDeepResonance extends AbstractArkNsfwCard {
    public static final String ID = ArkNsfwMod.makeID("ArchettoDeepResonance");

    public ArchettoDeepResonance() {
        super(ID, 2, CardType.ATTACK, ColorEnum.ARCHETTO_COLOR, CardRarity.RARE, CardTarget.ENEMY, "card_archetto_archettodeepresonance.png");
        baseDamage = 10;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        NsfwRunStats.addExcitement(18);
        NsfwRunStats.addFertility(6, 0, false);
        ArkCharMechanicsHelper.applyAimPower(p, 1);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(4);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new ArchettoDeepResonance(); }
}
