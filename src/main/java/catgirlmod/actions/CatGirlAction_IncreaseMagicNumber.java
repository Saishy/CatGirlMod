package catgirlmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.UUID;

public class CatGirlAction_IncreaseMagicNumber extends AbstractGameAction {

    private int increase;
    private UUID uuid;

    public CatGirlAction_IncreaseMagicNumber(UUID targetUUID, int increaseBy) {
        this.increase = increaseBy;
        this.uuid = targetUUID;
    }

    public void update() {
        for (AbstractCard c : com.megacrit.cardcrawl.dungeons.AbstractDungeon.player.masterDeck.group)
            if (c.uuid.equals(this.uuid)) {
                c.baseMagicNumber += this.increase;
                c.magicNumber = c.baseMagicNumber;
                c.applyPowers();
            }
        for (AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
            c.baseMagicNumber += this.increase;
            c.magicNumber = c.baseMagicNumber;
            c.applyPowers();
        }

        this.isDone = true;
    }
}
