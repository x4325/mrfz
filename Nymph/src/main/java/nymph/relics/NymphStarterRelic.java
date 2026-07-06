package nymph.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import nymph.powers.HexPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import nymph.NymphMod;
import nymph.helpers.AssetLoader;

public class NymphStarterRelic extends CustomRelic {
    public static final String ID = NymphMod.makeID("NymphStarterRelic");

    public NymphStarterRelic() {
        super(ID, new Texture(AssetLoader.relicArt("relic_starter.png")),
                new Texture(AssetLoader.relicOutlineArt("relic_starter.png")),
                RelicTier.STARTER, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new HexPower(AbstractDungeon.player, 1), 1));
    }

    @Override
    public String getUpdatedDescription() { return DESCRIPTIONS[0]; }

    @Override
    public AbstractRelic makeCopy() { return new NymphStarterRelic(); }
}
