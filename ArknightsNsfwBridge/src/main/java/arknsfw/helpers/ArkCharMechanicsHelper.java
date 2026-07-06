package arknsfw.helpers;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import Eyjafjalla.modifier.FireMarkMod;
import Eyjafjalla.panel.CloudEnergyPanel;
import Eyjafjalla.patch.CloudPatch;
import Eyjafjalla.power.FireMarkPower;
import Muelsyse.patches.CultivatePatch;
import Muelsyse.powers.ManifoldPower;
import Muelsyse.powers.RootagePower;
import nymph.powers.HexPower;
import haruka.powers.PyroPower;
import archetto.powers.AimPower;
import highmore.powers.ReapPower;
import scene.powers.FocusPower;

/**
 * Bridges arknsfw content with FimmlpS Eyjafjalla (云层/云量/炎息) and Muelsyse (流形/栽培/溯源) mods.
 */
public final class ArkCharMechanicsHelper {

    private ArkCharMechanicsHelper() {
    }

    // --- Eyjafjalla: cloud hand + cloud energy + pyrobreath (FireMark) ---

    public static int cloudEnergy() {
        if (!ArkCharacterSetup.isEyjaRun()) {
            return 0;
        }
        return CloudEnergyPanel.getCurrentEnergy();
    }

    public static int cloudCardCount() {
        if (!ArkCharacterSetup.isEyjaRun() || CloudPatch.cloudGroup == null) {
            return 0;
        }
        return CloudPatch.cloudGroup.size();
    }

    public static int fireMarkPowerAmount() {
        if (!ArkCharacterSetup.isEyjaRun() || AbstractDungeon.player == null) {
            return 0;
        }
        AbstractPower p = AbstractDungeon.player.getPower(FireMarkPower.POWER_ID);
        return p == null ? 0 : p.amount;
    }

    public static boolean cardHasFireMark(AbstractCard card) {
        if (card == null) {
            return false;
        }
        return CardModifierManager.modifiers(card).stream()
                .anyMatch(m -> m instanceof FireMarkMod);
    }

    public static void markPyrobreath(AbstractCard card) {
        if (!ArkCharacterSetup.isEyjaRun() || card == null || cardHasFireMark(card)) {
            return;
        }
        CardModifierManager.addModifier(card, new FireMarkMod());
    }

    public static void gainCloudEnergy(int amount) {
        if (!ArkCharacterSetup.isEyjaRun() || amount <= 0) {
            return;
        }
        CloudEnergyPanel.addEnergy(amount);
    }

    public static void spendCloudEnergy(int amount) {
        if (!ArkCharacterSetup.isEyjaRun() || amount <= 0) {
            return;
        }
        if (cloudEnergy() >= amount) {
            CloudEnergyPanel.useEnergy(amount);
        }
    }

    public static void applyFireMarkPower(AbstractPlayer p, int amount) {
        if (!ArkCharacterSetup.isEyjaRun() || p == null || amount <= 0) {
            return;
        }
        AbstractPower existing = p.getPower(FireMarkPower.POWER_ID);
        if (existing == null) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(p, p, new FireMarkPower(p, amount), amount));
        } else {
            existing.stackPower(amount);
        }
    }

    public static boolean pyrobreathActive(AbstractCard card) {
        return cardHasFireMark(card) || fireMarkPowerAmount() > 0;
    }

    // --- Muelsyse: manifold summon + rootage + cultivate ---

    public static boolean hasManifold() {
        return manifoldTotalAmount() > 0;
    }

    public static int manifoldTotalAmount() {
        if (!ArkCharacterSetup.isMuelsyseRun() || AbstractDungeon.player == null) {
            return 0;
        }
        AbstractPower p = AbstractDungeon.player.getPower(ManifoldPower.POWER_ID);
        if (!(p instanceof ManifoldPower)) {
            return p == null ? 0 : p.amount;
        }
        ManifoldPower mp = (ManifoldPower) p;
        return mp.amount + mp.extraAmount;
    }

    public static boolean manifoldTookDamageThisCombat() {
        if (!ArkCharacterSetup.isMuelsyseRun() || AbstractDungeon.player == null) {
            return false;
        }
        AbstractPower p = AbstractDungeon.player.getPower(ManifoldPower.POWER_ID);
        return p instanceof ManifoldPower && ((ManifoldPower) p).hasReceivedDamage;
    }

    public static int rootageAmount() {
        if (!ArkCharacterSetup.isMuelsyseRun() || AbstractDungeon.player == null) {
            return 0;
        }
        AbstractPower p = AbstractDungeon.player.getPower(RootagePower.POWER_ID);
        return p == null ? 0 : p.amount;
    }

    public static boolean isCultivating() {
        return ArkCharacterSetup.isMuelsyseRun() && CultivatePatch.isCultivate;
    }

    public static void applyRootage(AbstractPlayer p, int amount) {
        if (!ArkCharacterSetup.isMuelsyseRun() || p == null || amount <= 0) {
            return;
        }
        AbstractPower existing = p.getPower(RootagePower.POWER_ID);
        if (existing == null) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(p, p, new RootagePower(p, amount), amount));
        } else {
            existing.stackPower(amount);
        }
    }


    public static void applyFocusPower(AbstractPlayer p, int amount) {
        if (!ArkCharacterSetup.isSceneRun() || p == null || amount <= 0) return;
        AbstractPower existing = p.getPower(FocusPower.POWER_ID);
        if (existing == null) {
            AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new FocusPower(p, amount), amount));
        } else { existing.stackPower(amount); }
    }

    public static int focusAmount() {
        if (!ArkCharacterSetup.isSceneRun() || AbstractDungeon.player == null) return 0;
        AbstractPower p = AbstractDungeon.player.getPower(FocusPower.POWER_ID);
        return p == null ? 0 : p.amount;
    }

    public static void applyReapPower(AbstractPlayer p, int amount) {
        if (!ArkCharacterSetup.isHighmoreRun() || p == null || amount <= 0) return;
        AbstractPower existing = p.getPower(ReapPower.POWER_ID);
        if (existing == null) {
            AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new ReapPower(p, amount), amount));
        } else { existing.stackPower(amount); }
    }

    public static int reapAmount() {
        if (!ArkCharacterSetup.isHighmoreRun() || AbstractDungeon.player == null) return 0;
        AbstractPower p = AbstractDungeon.player.getPower(ReapPower.POWER_ID);
        return p == null ? 0 : p.amount;
    }

    public static void applyAimPower(AbstractPlayer p, int amount) {
        if (!ArkCharacterSetup.isArchettoRun() || p == null || amount <= 0) return;
        AbstractPower existing = p.getPower(AimPower.POWER_ID);
        if (existing == null) {
            AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new AimPower(p, amount), amount));
        } else { existing.stackPower(amount); }
    }

    public static int aimAmount() {
        if (!ArkCharacterSetup.isArchettoRun() || AbstractDungeon.player == null) return 0;
        AbstractPower p = AbstractDungeon.player.getPower(AimPower.POWER_ID);
        return p == null ? 0 : p.amount;
    }

    public static void applyPyroPower(AbstractPlayer p, int amount) {
        if (!ArkCharacterSetup.isHarukaRun() || p == null || amount <= 0) return;
        AbstractPower existing = p.getPower(PyroPower.POWER_ID);
        if (existing == null) {
            AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new PyroPower(p, amount), amount));
        } else { existing.stackPower(amount); }
    }

    public static int pyroAmount() {
        if (!ArkCharacterSetup.isHarukaRun() || AbstractDungeon.player == null) return 0;
        AbstractPower p = AbstractDungeon.player.getPower(PyroPower.POWER_ID);
        return p == null ? 0 : p.amount;
    }

    public static void applyHexPower(AbstractPlayer p, int amount) {
        if (!ArkCharacterSetup.isNymphRun() || p == null || amount <= 0) return;
        AbstractPower existing = p.getPower(HexPower.POWER_ID);
        if (existing == null) {
            AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new HexPower(p, amount), amount));
        } else { existing.stackPower(amount); }
    }

    public static int hexAmount() {
        if (!ArkCharacterSetup.isNymphRun() || AbstractDungeon.player == null) return 0;
        AbstractPower p = AbstractDungeon.player.getPower(HexPower.POWER_ID);
        return p == null ? 0 : p.amount;
    }

    public static void boostManifold(int amount) {
        if (!ArkCharacterSetup.isMuelsyseRun() || amount <= 0 || AbstractDungeon.player == null) {
            return;
        }
        AbstractPower p = AbstractDungeon.player.getPower(ManifoldPower.POWER_ID);
        if (p instanceof ManifoldPower) {
            p.stackPower(amount);
        }
    }
}
