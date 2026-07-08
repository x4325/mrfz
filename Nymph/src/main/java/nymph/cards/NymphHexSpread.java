package nymph.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import nymph.NymphMod;
import nymph.powers.HexPower;
import nymph.powers.FearPower;
import nymph.powers.HeartLockPower;

/** 咒火蔓延 */
public class NymphHexSpread extends AbstractNymphCard {
    public static final String ID = NymphMod.makeID("HexSpread");

    public NymphHexSpread() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY, "card_nymph_hexspread.png");

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower hex = m == null ? null : m.getPower(HexPower.POWER_ID);
        if (hex != null && hex.amount > 0) {
            int half = (hex.amount + 1) / 2;
            for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                if (mo != m && !mo.isDead && !mo.isDying) {
                    addToBot(new ApplyPowerAction(mo, p, new HexPower(mo, half), half));
                }
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override
    public AbstractCard makeCopy() { return new NymphHexSpread(); }
}
