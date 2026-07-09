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

    /** 升级描述安全应用：UPGRADE_DESCRIPTION 缺失时退回 DESCRIPTION，避免 NPE 闪退。 */
    protected void applyUpgradeDescription() {
        String upgradeText = cardStrings.UPGRADE_DESCRIPTION;
        if (upgradeText == null || upgradeText.isEmpty()) {
            upgradeText = cardStrings.DESCRIPTION;
        }
        rawDescription = upgradeText;
        initializeDescription();
    }
}
