package catgirlmod.powers;

import catgirlmod.CatGirlMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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

public class InsanePower extends AbstractPower {

    public static final String POWER_ID = CatGirlMod.makeID("InsanePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = CatGirlMod.makePath("images/powers/catgirl_insane.png");

    public InsanePower(final AbstractCreature owner, final AbstractCreature source) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        updateDescription();
        type = PowerType.BUFF;
        isTurnBased = false;
        img = ImageMaster.loadImage(IMG);
    }

    @Override
    public void onInitialApplication() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == AbstractCard.CardType.ATTACK) {
                c.modifyCostForTurn(-9);
            }
        }

        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.type == AbstractCard.CardType.ATTACK) {
                c.modifyCostForTurn(-9);
            }
        }

        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c.type == AbstractCard.CardType.ATTACK) {
                c.modifyCostForTurn(-9);
            }
        }

        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c.type == AbstractCard.CardType.ATTACK) {
                c.modifyCostForTurn(-9);
            }
        }
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            flash();
            action.exhaustCard = true;
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            AbstractDungeon.actionManager.addToBottom(
                    new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(
                            owner, owner, POWER_ID
                    )
            );
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
