package nymph.core;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;

/** Card color + library type for 妮芙. Mirrors Eyjafjalla/Muelsyse ColorEnum pattern. */
public class ColorEnum {
    @SpireEnum
    public static AbstractCard.CardColor NYMPH_COLOR;

    @SpireEnum(name = "NYMPH_COLOR")
    public static CardLibrary.LibraryType LIBRARY_NYMPH;
}
