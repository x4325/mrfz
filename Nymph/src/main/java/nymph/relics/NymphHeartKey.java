package nymph.relics;

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
import nymph.NymphMod;
import nymph.powers.HexPower;
import nymph.powers.FearPower;
import nymph.powers.HeartLockPower;
public class NymphHeartKey extends CustomRelic {
    public static final String ID = NymphMod.makeID("NymphHeartKey");

    public NymphHeartKey() {
        super(ID, new Texture(NymphMod.relicPath("NymphHeartKey.png")),
                new Texture(NymphMod.relicOutlinePath("NymphHeartKey.png")), RelicTier.RARE, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) { if (card.type == AbstractCard.CardType.SKILL) { AbstractMonster pick = AbstractDungeon.getMonsters().getRandomMonster(true); if (pick != null) { addToBot(new ApplyPowerAction(pick, AbstractDungeon.player, new HexPower(pick, 1), 1)); flash(); } } }

    @Override
    public AbstractRelic makeCopy() { return new NymphHeartKey(); }
}
