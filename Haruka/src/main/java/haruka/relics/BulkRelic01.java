package haruka.relics;

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
import haruka.HarukaMod;
import haruka.powers.PyroPower;
public class BulkRelic01 extends CustomRelic {
    public static final String ID = HarukaMod.makeID("BulkRelic01");

    public BulkRelic01() {
        super(ID, new Texture(HarukaMod.relicPath("BulkRelic01.png")),
                new Texture(HarukaMod.relicOutlinePath("BulkRelic01.png")), RelicTier.COMMON, LandingSound.SOLID);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStart() { addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PyroPower(AbstractDungeon.player, 1), 1)); flash(); }

    @Override
    public AbstractRelic makeCopy() { return new BulkRelic01(); }
}
