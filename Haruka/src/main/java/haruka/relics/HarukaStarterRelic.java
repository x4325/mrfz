package haruka.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import haruka.powers.PyroPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import haruka.HarukaMod;
import haruka.helpers.AssetLoader;

public class HarukaStarterRelic extends CustomRelic {
    public static final String ID = HarukaMod.makeID("HarukaStarterRelic");

    public HarukaStarterRelic() {
        super(ID, new Texture(AssetLoader.relicArt("relic_starter.png")),
                new Texture(AssetLoader.relicOutlineArt("relic_starter.png")),
                RelicTier.STARTER, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new PyroPower(AbstractDungeon.player, 1), 1));
    }

    @Override
    public String getUpdatedDescription() { return DESCRIPTIONS[0]; }

    @Override
    public AbstractRelic makeCopy() { return new HarukaStarterRelic(); }
}
