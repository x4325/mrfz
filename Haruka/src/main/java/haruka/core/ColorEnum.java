package haruka.core;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;

/** Card color + library type for 遥. Mirrors Eyjafjalla/Muelsyse ColorEnum pattern. */
public class ColorEnum {
    @SpireEnum
    public static AbstractCard.CardColor HARUKA_COLOR;

    @SpireEnum(name = "HARUKA_COLOR")
    public static CardLibrary.LibraryType LIBRARY_HARUKA;
}
