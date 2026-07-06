package scene.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import scene.powers.FocusPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import scene.SceneMod;
import scene.helpers.AssetLoader;

public class SceneStarterRelic extends CustomRelic {
    public static final String ID = SceneMod.makeID("SceneStarterRelic");

    public SceneStarterRelic() {
        super(ID, new Texture(AssetLoader.relicArt("relic_starter.png")),
                new Texture(AssetLoader.relicOutlineArt("relic_starter.png")),
                RelicTier.STARTER, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new FocusPower(AbstractDungeon.player, 1), 1));
    }

    @Override
    public String getUpdatedDescription() { return DESCRIPTIONS[0]; }

    @Override
    public AbstractRelic makeCopy() { return new SceneStarterRelic(); }
}
