package scene.core;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;

/** Card color + library type for 稀音. Mirrors Eyjafjalla/Muelsyse ColorEnum pattern. */
public class ColorEnum {
    @SpireEnum
    public static AbstractCard.CardColor SCENE_COLOR;

    @SpireEnum(name = "SCENE_COLOR")
    public static CardLibrary.LibraryType LIBRARY_SCENE;
}
