package haruka.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import haruka.core.ClassEnum;
import haruka.screens.SkinSelectScreen;

public class SelectScreenPatch {

    public static boolean isOurCharacterSelected() {
        if (CardCrawlGame.chosenCharacter != ClassEnum.HARUKA) {
            return false;
        }
        CharacterSelectScreen screen = CardCrawlGame.mainMenuScreen.charSelectScreen;
        Boolean anySelected = ReflectionHacks.getPrivate(screen, CharacterSelectScreen.class, "anySelected");
        return anySelected != null && anySelected;
    }

    @SpirePatch(clz = CharacterSelectScreen.class, method = "render")
    public static class RenderButtonPatch {
        @SpirePostfixPatch
        public static void Postfix(CharacterSelectScreen _inst, SpriteBatch sb) {
            if (isOurCharacterSelected() && SkinSelectScreen.Inst != null) {
                SkinSelectScreen.Inst.render(sb);
            }
        }
    }

    @SpirePatch(clz = CharacterSelectScreen.class, method = "update")
    public static class UpdateButtonPatch {
        @SpirePrefixPatch
        public static void Prefix(CharacterSelectScreen _inst) {
            if (isOurCharacterSelected() && SkinSelectScreen.Inst != null) {
                SkinSelectScreen.Inst.update();
            }
        }
    }
}
