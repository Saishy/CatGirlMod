package catgirlmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.UUID;

public class IncreaseMagicNumberAction extends AbstractGameAction {

    private int increase;
    private UUID uuid;

    public IncreaseMagicNumberAction(UUID targetUUID, int increaseBy) {
        this.increase = increaseBy;
        this.uuid = targetUUID;
    }

    public void update() {
        for (AbstractCard c : com.megacrit.cardcrawl.dungeons.AbstractDungeon.player.masterDeck.group)
            if (c.uuid.equals(this.uuid)) {
                c.misc += this.increase;
                c.applyPowers();
                c.baseMagicNumber = c.misc;
                c.magicNumber = c.baseMagicNumber;
            }
        for (AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
            c.misc += this.increase;
            c.applyPowers();
            c.baseMagicNumber = c.misc;
            c.magicNumber = c.baseMagicNumber;
        }

        isDone = true;
    }
}
