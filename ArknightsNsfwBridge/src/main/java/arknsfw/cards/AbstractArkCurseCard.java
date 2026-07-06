package arknsfw.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import arknsfw.ArkNsfwMod;

public abstract class AbstractArkCurseCard extends CustomCard {

    protected final CardStrings cardStrings;

    protected AbstractArkCurseCard(String id, String artFile) {
        super(
                id,
                "",
                ArkNsfwMod.makeCardPath(artFile),
                -2,
                "",
                CardType.CURSE,
                CardColor.CURSE,
                CardRarity.CURSE,
                CardTarget.NONE
        );
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        name = cardStrings.NAME;
        rawDescription = cardStrings.DESCRIPTION;
        initializeTitle();
        initializeDescription();
        isEthereal = false;
    }
}
