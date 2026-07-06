package scene.relics;

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
import scene.SceneMod;
import scene.powers.FocusPower;
public class BulkRelic05 extends CustomRelic {
    public static final String ID = SceneMod.makeID("BulkRelic05");

    public BulkRelic05() {
        super(ID, new Texture(SceneMod.relicPath("BulkRelic05.png")),
                new Texture(SceneMod.relicOutlinePath("BulkRelic05.png")), RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) { if (card.type == AbstractCard.CardType.SKILL) { addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FocusPower(AbstractDungeon.player, 1), 1)); flash(); } }

    @Override
    public AbstractRelic makeCopy() { return new BulkRelic05(); }
}
