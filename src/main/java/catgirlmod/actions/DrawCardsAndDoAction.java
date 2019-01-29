package catgirlmod.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DrawCardsAndDoAction extends AbstractGameAction {

    private IDrawCardsAndDoCallback callback;

    public interface IDrawCardsAndDoCallback {
        void Execute(AbstractCard cardDraw);
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

        int allCardsAmount = AbstractDungeon.player.drawPile.size();

        if (allCardsAmount < amount) {
            amount = allCardsAmount;
        }

        if (SoulGroup.isActive()) {
            return;
        }

        if (AbstractDungeon.player.hand.size() == BaseMod.MAX_HAND_SIZE) {
            AbstractDungeon.player.createHandIsFullDialog();
            isDone = true;
            return;
        }

        if ((this.amount != 0) && (this.duration < 0.0F)) {
            if (Settings.FAST_MODE) {
                this.duration = Settings.ACTION_DUR_XFAST;
            } else {
                this.duration = Settings.ACTION_DUR_FASTER;
            }
            this.amount -= 1;

            AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
            AbstractDungeon.player.draw();
            AbstractDungeon.player.hand.refreshHandLayout();

            callback.Execute(card);
        }

        this.duration -= com.badlogic.gdx.Gdx.graphics.getDeltaTime();

        if (this.amount == 0) {
            isDone = true;
        }
    }
}
