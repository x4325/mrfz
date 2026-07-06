package archetto.relics;

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
import archetto.ArchettoMod;
import archetto.powers.AimPower;
public class ArchettoSalePoster extends CustomRelic {
    public static final String ID = ArchettoMod.makeID("ArchettoSalePoster");

    public ArchettoSalePoster() {
        super(ID, new Texture(ArchettoMod.relicPath("ArchettoSalePoster.png")),
                new Texture(ArchettoMod.relicOutlinePath("ArchettoSalePoster.png")), RelicTier.RARE, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() { addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new AimPower(AbstractDungeon.player, 2), 2)); flash(); }

    @Override
    public AbstractRelic makeCopy() { return new ArchettoSalePoster(); }
}
