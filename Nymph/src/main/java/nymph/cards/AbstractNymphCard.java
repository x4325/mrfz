package nymph.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import nymph.NymphMod;
import nymph.core.ColorEnum;
import nymph.helpers.AssetLoader;

public abstract class AbstractNymphCard extends CustomCard {

    protected final CardStrings cardStrings;

    public AbstractNymphCard(String id, int cost, AbstractCard.CardType type,
                             AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) {
        this(id, cost, type, rarity, target, defaultArtFor(type));
    }

    public AbstractNymphCard(String id, int cost, AbstractCard.CardType type,
                             AbstractCard.CardRarity rarity, AbstractCard.CardTarget target,
                             String artFileName) {
        super(id, "", AssetLoader.cardArt(artFileName), cost, "", type,
                ColorEnum.NYMPH_COLOR, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        this.rawDescription = cardStrings.DESCRIPTION;
        this.name = cardStrings.NAME;
        this.initializeTitle();
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) upgradeName();
    }

    protected void upgradeDamage(int amount) { baseDamage += amount; upgradedDamage = true; }
    protected void upgradeBlock(int amount) { baseBlock += amount; upgradedBlock = true; }

    private static String defaultArtFor(AbstractCard.CardType type) {
        switch (type) {
            case ATTACK: return "card_attack.png";
            case POWER:  return "card_power.png";
            case SKILL:
            default:     return "card_skill.png";
        }
    }
}
