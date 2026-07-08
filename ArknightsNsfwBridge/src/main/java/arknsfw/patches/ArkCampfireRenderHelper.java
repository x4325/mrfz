package arknsfw.patches;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import scene.characters.Scene;
import highmore.characters.Highmore;
import archetto.characters.Archetto;
import nymph.characters.Nymph;
import haruka.characters.Haruka;

final class ArkCampfireRenderHelper {

    private ArkCampfireRenderHelper() {
    }

    static boolean isSpineCampfireCharacter(AbstractPlayer player) {
        return player instanceof Scene
                || player instanceof Highmore
                || player instanceof Archetto
                || player instanceof Nymph
                || player instanceof Haruka;
    }
}
