package arknsfw.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import arknsfw.ArkNsfwMod;

public abstract class AbstractArkNsfwCard extends CustomCard {

    protected final CardStrings cardStrings;

    protected AbstractArkNsfwCard(
            String id,
            int cost,
            CardType type,
            CardColor color,
            CardRarity rarity,
            CardTarget target,
            String artFile
    ) {
        super(
                id,
                "",
                ArkNsfwMod.makeCardPath(artFile),
                cost,
                "",
                type,
                color,
                rarity,
                target
        );
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        name = cardStrings.NAME;
        rawDescription = cardStrings.DESCRIPTION;
        initializeTitle();
        initializeDescription();
    }
}
