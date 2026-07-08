package arknsfw.helpers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;

/** 按当前角色返回其专属羞耻 debuff。 */
public final class ArkCharDebuffs {

    private ArkCharDebuffs() {
    }

    public static AbstractPower fresh(AbstractPlayer p, int layers) {
        if (ArkCharacterSetup.isHighmoreRun()) {
            return new arknsfw.powers.highmore.TideBrandPower(p, layers);
        }
        if (ArkCharacterSetup.isSceneRun()) {
            return new arknsfw.powers.scene.ExposedLensPower(p, layers);
        }
        if (ArkCharacterSetup.isArchettoRun()) {
            return new arknsfw.powers.archetto.TremblingGripPower(p, layers);
        }
        if (ArkCharacterSetup.isHarukaRun()) {
            return new arknsfw.powers.haruka.LingeringHeatPower(p, layers);
        }
        if (ArkCharacterSetup.isNymphRun()) {
            return new arknsfw.powers.nymph.HeartGnawPower(p, layers);
        }
        return null;
    }

    public static String currentCharKey() {
        if (ArkCharacterSetup.isHighmoreRun()) return "highmore";
        if (ArkCharacterSetup.isSceneRun()) return "scene";
        if (ArkCharacterSetup.isArchettoRun()) return "archetto";
        if (ArkCharacterSetup.isHarukaRun()) return "haruka";
        if (ArkCharacterSetup.isNymphRun()) return "nymph";
        return null;
    }

    /** 当前皮肤序号（读各角色 mod 的皮肤选择器）。 */
    public static int currentSkinIndex() {
        try {
            if (ArkCharacterSetup.isHighmoreRun() && highmore.screens.SkinSelectScreen.Inst != null) {
                return highmore.screens.SkinSelectScreen.Inst.index;
            }
            if (ArkCharacterSetup.isSceneRun() && scene.screens.SkinSelectScreen.Inst != null) {
                return scene.screens.SkinSelectScreen.Inst.index;
            }
            if (ArkCharacterSetup.isArchettoRun() && archetto.screens.SkinSelectScreen.Inst != null) {
                return archetto.screens.SkinSelectScreen.Inst.index;
            }
            if (ArkCharacterSetup.isHarukaRun() && haruka.screens.SkinSelectScreen.Inst != null) {
                return haruka.screens.SkinSelectScreen.Inst.index;
            }
            if (ArkCharacterSetup.isNymphRun() && nymph.screens.SkinSelectScreen.Inst != null) {
                return nymph.screens.SkinSelectScreen.Inst.index;
            }
        } catch (Exception ignored) {
        }
        return 0;
    }
}
