package catgirlmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DrawTopCardAndPlayIfTypeAction extends AbstractGameAction {

    private boolean exhaustCards;
    private AbstractCard.CardType cardType;

    public DrawTopCardAndPlayIfTypeAction(AbstractCreature target, AbstractCard.CardType cardType, boolean exhausts) {
       this.duration = Settings.ACTION_DUR_FAST;
       this.actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.WAIT;
       this.source = AbstractDungeon.player;
       this.target = target;
       this.cardType = cardType;
       this.exhaustCards = exhausts;
    }

    public DrawTopCardAndPlayIfTypeAction(AbstractCreature target, AbstractCard.CardType cardType) {
        this(target, cardType, false);
    }

    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.drawPile.size() + AbstractDungeon.player.discardPile.size() == 0) {
                isDone = true;
                return;
            }

            if (AbstractDungeon.player.drawPile.isEmpty()) {
                AbstractDungeon.actionManager.addToTop(new PlayTopCardAction(target, exhaustCards));
                AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());

                isDone = true;
                return;
            }

            if (!AbstractDungeon.player.drawPile.isEmpty()) {
                AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();

                if (card.type != cardType) {
                    AbstractDungeon.actionManager.addToBottom(
                            new DrawCardAction(AbstractDungeon.player, 1)
                    );
                    AbstractDungeon.actionManager.addToBottom(
                            new DiscardSpecificCardAction(card, AbstractDungeon.player.hand)
                    );

                    isDone = true;
                    return;
                }

                AbstractDungeon.player.drawPile.group.remove(card);
                AbstractDungeon.getCurrRoom().souls.remove(card);
                card.freeToPlayOnce = true;
                card.exhaustOnUseOnce = exhaustCards;
                AbstractDungeon.player.limbo.group.add(card);
                card.current_y = (-200.0F * Settings.scale);
                card.target_x = (Settings.WIDTH / 2.0F + 200.0F * Settings.scale);
                card.target_y = (Settings.HEIGHT / 2.0F);
                card.targetAngle = 0.0F;
                card.lighten(false);
                card.drawScale = 0.12F;
                card.targetDrawScale = 0.75F;

                if (!card.canUse(AbstractDungeon.player, (AbstractMonster) target)) {
                    if (exhaustCards) {
                        AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.limbo));
                    } else {
                        AbstractDungeon.actionManager.addToTop(new UnlimboAction(card));
                        AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(card, AbstractDungeon.player.limbo));

                        AbstractDungeon.actionManager.addToTop(new WaitAction(0.4F));
                    }
                } else {
                    card.applyPowers();
                    AbstractDungeon.actionManager.addToTop(new QueueCardAction(card, target));
                    AbstractDungeon.actionManager.addToTop(new UnlimboAction(card));

                    if (!Settings.FAST_MODE) {
                        AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                    } else {
                        AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                    }
                }
            }

            isDone = true;
        }
    }
}
