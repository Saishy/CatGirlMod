package catgirlmod.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class CatGirlAction_UpgradeCardTypeInHand extends com.megacrit.cardcrawl.actions.AbstractGameAction {

    public enum UpgradeType {
        AMOUNT, ALL
    }

    private static final UIStrings uiStrings = com.megacrit.cardcrawl.core.CardCrawlGame.languagePack.getUIString("ArmamentsAction");
    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer p;

    private AbstractCard.CardType cardType;
    private UpgradeType upgradeType;
    private int amount;

    public CatGirlAction_UpgradeCardTypeInHand(AbstractCard.CardType cType, UpgradeType upType, int amount) {
        this.actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;

        this.cardType = cType;
        this.upgradeType = upType;
        this.amount = amount;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (p.hand.group.size() <= 0) {
                isDone = true;
                return;
            }

            if (upgradeType == UpgradeType.ALL) {
                for (AbstractCard c : p.hand.group) {
                    if (c.type == cardType && c.canUpgrade()) {
                        c.upgrade();
                        c.superFlash();
                    }
                }

                isDone = true;
                return;
            }

            if (amount <= 0) {
                isDone = true;
                return;
            }

            CardGroup upgradeable = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);

            for (AbstractCard c : this.p.hand.group) {
                if (c.type == cardType && c.canUpgrade()) {
                    upgradeable.addToTop(c);
                }
            }

            if (upgradeable.size() == amount) {
                for (int i = 0; i < amount; i++) {
                    ((AbstractCard) upgradeable.group.get(i)).upgrade();
                    ((AbstractCard) upgradeable.group.get(i)).superFlash();
                }
            } else if (upgradeable.size() > 1) {
                for (int i = 0; i < amount; i++) {
                    upgradeable.shuffle();
                    ((AbstractCard) upgradeable.group.get(0)).upgrade();
                    ((AbstractCard) upgradeable.group.get(0)).superFlash();
                    //((AbstractCard) upgradeable.group.get(0)).applyPowers();
                }
            }

            isDone = true;
            return;
        }

        tickDuration();
    }
}
