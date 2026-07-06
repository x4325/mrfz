package arknsfw.relics;

import basemod.abstracts.CustomRelic;
import liesecore.helpers.TextureHelper;
import arknsfw.ArkNsfwMod;

public abstract class AbstractArkNsfwRelic extends CustomRelic {

    public AbstractArkNsfwRelic(String id, String img, RelicTier tier, LandingSound sound) {
        super(
                id,
                TextureHelper.getTexture(ArkNsfwMod.makeRelicPath(img)),
                TextureHelper.getTexture(ArkNsfwMod.makeRelicOutlinePath(img)),
                tier,
                sound
        );
    }
}
