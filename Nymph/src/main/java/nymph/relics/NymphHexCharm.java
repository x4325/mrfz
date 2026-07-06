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
public class NymphHexCharm extends CustomRelic {
    public static final String ID = NymphMod.makeID("NymphHexCharm");

    public NymphHexCharm() {
        super(ID, new Texture(NymphMod.relicPath("NymphHexCharm.png")),
                new Texture(NymphMod.relicOutlinePath("NymphHexCharm.png")), RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() { flash(); for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) { if (!mo.isDead && !mo.isDying) { addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new HexPower(mo, 1), 1)); } } }

    @Override
    public AbstractRelic makeCopy() { return new NymphHexCharm(); }
}
