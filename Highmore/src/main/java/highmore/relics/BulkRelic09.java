package highmore.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import highmore.HighmoreMod;
import highmore.powers.ReapPower;
public class BulkRelic09 extends CustomRelic {
    public static final String ID = HighmoreMod.makeID("BulkRelic09");

    public BulkRelic09() {
        super(ID, new Texture(HighmoreMod.relicPath("BulkRelic09.png")),
                new Texture(HighmoreMod.relicOutlinePath("BulkRelic09.png")), RelicTier.SHOP, LandingSound.SOLID);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() { addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ReapPower(AbstractDungeon.player, 2), 2)); flash(); }

    @Override
    public AbstractRelic makeCopy() { return new BulkRelic09(); }
}
