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
public class HarukaDrumBeat extends CustomRelic {
    public static final String ID = HarukaMod.makeID("HarukaDrumBeat");

    public HarukaDrumBeat() {
        super(ID, new Texture(HarukaMod.relicPath("HarukaDrumBeat.png")),
                new Texture(HarukaMod.relicOutlinePath("HarukaDrumBeat.png")), RelicTier.RARE, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStart() { com.megacrit.cardcrawl.powers.AbstractPower p = AbstractDungeon.player.getPower(PyroPower.POWER_ID); if (p != null && p.amount >= 8) { addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, 6, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HEAVY)); flash(); } }

    @Override
    public AbstractRelic makeCopy() { return new HarukaDrumBeat(); }
}
