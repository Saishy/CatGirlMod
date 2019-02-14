package catgirlmod.actions;

import basemod.BaseMod;
import catgirlmod.CatGirlMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DrawCardsAndDoAction extends AbstractGameAction {

    private boolean shuffleCheck = false;
    private IDrawCardsAndDoCallback callback;

    public interface IDrawCardsAndDoCallback {
        void ExecuteDrawCardCallback(AbstractCard cardDraw);
    }

    public DrawCardsAndDoAction(IDrawCardsAndDoCallback command, int amountToDraw) {
        this.callback = command;

        setValues(AbstractDungeon.player, AbstractDungeon.player, amountToDraw);

        this.actionType = AbstractGameAction.ActionType.DRAW;
        if (Settings.FAST_MODE) {
            this.duration = Settings.ACTION_DUR_XFAST;
        } else {
            this.duration = Settings.ACTION_DUR_FASTER;
        }
    }

    /*public DrawCardsAndDoAction(IDrawCardsAndDoCallback command, int amountToDraw) {
        this(command, amountToDraw, true);
    }*/

    public void update() {
        if (AbstractDungeon.player.hasPower("No Draw")) {
            AbstractDungeon.player.getPower("No Draw").flash();
            isDone = true;
            return;
        }

        if (this.amount <= 0) {
            isDone = true;
            return;
        }

        if (SoulGroup.isActive()) {
            return;
        }

        if (AbstractDungeon.player.hand.size() == BaseMod.MAX_HAND_SIZE) {
            AbstractDungeon.player.createHandIsFullDialog();
            isDone = true;
            return;
        }

        int deckSize = AbstractDungeon.player.drawPile.size();
        int discardSize = AbstractDungeon.player.discardPile.size();

        if (deckSize + discardSize == 0) {
            isDone = true;
            return;
        }

        if (deckSize + discardSize < amount) {
            amount = deckSize + discardSize;
        }

        if (!shuffleCheck) {
            if (amount + AbstractDungeon.player.hand.size() > BaseMod.MAX_HAND_SIZE) {
                int handSizeAndDraw = BaseMod.MAX_HAND_SIZE - (amount + AbstractDungeon.player.hand.size());
                amount += handSizeAndDraw;
                AbstractDungeon.player.createHandIsFullDialog();
            }
            if (amount > deckSize) {
                int tmp = amount - deckSize;
                AbstractDungeon.actionManager.addToTop(new DrawCardsAndDoAction(callback, tmp));
                AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
                if (deckSize != 0) {
                    AbstractDungeon.actionManager.addToTop(new DrawCardsAndDoAction(callback, deckSize));
                }
                amount = 0;
                isDone = true;
            }
            shuffleCheck = true;
        }

        duration -= com.badlogic.gdx.Gdx.graphics.getDeltaTime();

        if ((amount != 0) && (duration < 0.0F)) {
            if (Settings.FAST_MODE) {
                duration = Settings.ACTION_DUR_XFAST;
            } else {
                duration = Settings.ACTION_DUR_FASTER;
            }
            amount -= 1;

            if (!AbstractDungeon.player.drawPile.isEmpty()) {
                AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
                AbstractDungeon.player.draw();
                AbstractDungeon.player.hand.refreshHandLayout();

                callback.ExecuteDrawCardCallback(card);
            } else {
                CatGirlMod.logger.debug("DrawCardsAndDoAction::Update() Tried drawing a card but draw pile is empty.");
                isDone = true;
            }
        }

        if (amount == 0) {
            isDone = true;
        }
    }
}
