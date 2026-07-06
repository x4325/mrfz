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
public class HighmoreAnchor extends CustomRelic {
    public static final String ID = HighmoreMod.makeID("HighmoreAnchor");

    public HighmoreAnchor() {
        super(ID, new Texture(HighmoreMod.relicPath("HighmoreAnchor.png")),
                new Texture(HighmoreMod.relicOutlinePath("HighmoreAnchor.png")), RelicTier.RARE, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) { if (card.type == AbstractCard.CardType.SKILL) { addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 3)); flash(); } }

    @Override
    public AbstractRelic makeCopy() { return new HighmoreAnchor(); }
}
