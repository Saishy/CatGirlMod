package catgirlmod.powers;

import catgirlmod.CatGirlMod;
import catgirlmod.interfaces.IncrementDiscardSubscriber;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class NeverGivingUpPower extends AbstractPower implements IncrementDiscardSubscriber {

    public static final String POWER_ID = CatGirlMod.makeID("NeverGivingUp");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = "images/powers/catgirl_nevergivingup.png";

    public NeverGivingUpPower(final AbstractCreature owner, int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        updateDescription();
        this.amount = amount;
        type = PowerType.BUFF;
        isTurnBased = false;
        img = ImageMaster.loadImage(IMG);
    }

    @Override
    public void onInitialApplication() {
        CatGirlMod.subscribe(this);
    }

    @Override
    public void onRemove() {
        CatGirlMod.unsubscribe(this);
    }

    @Override
    public void onVictory() {
        CatGirlMod.unsubscribe(this);
    }

    @Override
    public void onDeath() {
        CatGirlMod.unsubscribe(this);
    }

    //No hook for discarding
    @Override
    public void receiveIncrementDiscard(boolean endOfTurn) {
        CatGirlMod.logger.debug("CatGirlMod: I'm never giving up! " + endOfTurn);

        if (AbstractDungeon.getCurrRoom() == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            return;
        }

        flash();

        AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (m != null) {
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(m, new DamageInfo(owner, amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT)
            );
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount;
    }
}
