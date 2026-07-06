package archetto.core;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;

/** Card color + library type for 空弦. Mirrors Eyjafjalla/Muelsyse ColorEnum pattern. */
public class ColorEnum {
    @SpireEnum
    public static AbstractCard.CardColor ARCHETTO_COLOR;

    @SpireEnum(name = "ARCHETTO_COLOR")
    public static CardLibrary.LibraryType LIBRARY_ARCHETTO;
}
