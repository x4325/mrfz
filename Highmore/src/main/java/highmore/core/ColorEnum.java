package highmore.core;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;

/** Card color + library type for 海沫. Mirrors Eyjafjalla/Muelsyse ColorEnum pattern. */
public class ColorEnum {
    @SpireEnum
    public static AbstractCard.CardColor HIGHMORE_COLOR;

    @SpireEnum(name = "HIGHMORE_COLOR")
    public static CardLibrary.LibraryType LIBRARY_HIGHMORE;
}
