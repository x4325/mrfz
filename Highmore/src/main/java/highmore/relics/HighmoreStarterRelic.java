package highmore.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import highmore.powers.ReapPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import highmore.HighmoreMod;
import highmore.helpers.AssetLoader;

public class HighmoreStarterRelic extends CustomRelic {
    public static final String ID = HighmoreMod.makeID("HighmoreStarterRelic");

    public HighmoreStarterRelic() {
        super(ID, new Texture(AssetLoader.relicArt("relic_starter.png")),
                new Texture(AssetLoader.relicOutlineArt("relic_starter.png")),
                RelicTier.STARTER, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new ReapPower(AbstractDungeon.player, 1), 1));
    }

    @Override
    public String getUpdatedDescription() { return DESCRIPTIONS[0]; }

    @Override
    public AbstractRelic makeCopy() { return new HighmoreStarterRelic(); }
}
