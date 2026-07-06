package arknsfw.relics.curses;

import basemod.abstracts.CustomRelic;
import liesecore.helpers.TextureHelper;
import arknsfw.ArkNsfwMod;

public abstract class AbstractArkCurseRelic extends CustomRelic {

    public AbstractArkCurseRelic(String id, String img, LandingSound sound) {
        super(
                id,
                TextureHelper.getTexture(ArkNsfwMod.makeRelicPath(img)),
                TextureHelper.getTexture(ArkNsfwMod.makeRelicOutlinePath(img)),
                RelicTier.SPECIAL,
                sound
        );
    }
}

