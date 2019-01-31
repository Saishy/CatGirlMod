package catgirlmod.actions;

import catgirlmod.cards.clumsy.Stumble;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TryingMyBestAction extends AbstractGameAction {

    private AbstractCreature target;
    private int dmgAmount = 0;

    public TryingMyBestAction(AbstractCreature target, int damageAmount) {
        this.target = target;
        this.dmgAmount = damageAmount;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            int theSize = AbstractDungeon.player.hand.size();
            int attackNumber = 0;

            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card.type == AbstractCard.CardType.ATTACK || card.cardID.equals(Stumble.ID)) {
                    attackNumber++;
                }
            }

            AbstractDungeon.actionManager.addToBottom(
                    new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, theSize, false)
            );

            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(target, new DamageInfo(AbstractDungeon.player, dmgAmount * attackNumber, DamageInfo.DamageType.NORMAL), AttackEffect.BLUNT_HEAVY)
            );
        }

        tickDuration();
    }
}
