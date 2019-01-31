package catgirlmod.powers;

import catgirlmod.CatGirlMod;
import catgirlmod.cards.clumsy.Stumble;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PilingErrorsPower extends AbstractPower {

    public static final String POWER_ID = CatGirlMod.makeID("PilingErrorsPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = "images/powers/catgirl_pilingerrors.png";

    public boolean bUpgraded = false;

    public PilingErrorsPower(final AbstractCreature owner, boolean bUpgraded) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.bUpgraded = bUpgraded;
        updateDescription();
        type = PowerType.BUFF;
        isTurnBased = false;
        img = ImageMaster.loadImage(IMG);
    }

    public void Upgrade() {
        bUpgraded = true;
    }

    @Override
    public void onInitialApplication() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.cardID.equals(Stumble.ID)) {
                c.retain = true;
                if (bUpgraded) {
                    c.cost = 1;
                }
            }
        }

        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.cardID.equals(Stumble.ID)) {
                c.retain = true;
                if (bUpgraded) {
                    c.cost = 1;
                }
            }
        }

        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c.cardID.equals(Stumble.ID)) {
                c.retain = true;
                if (bUpgraded) {
                    c.cost = 1;
                }
            }
        }

        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c.cardID.equals(Stumble.ID)) {
                c.retain = true;
                if (bUpgraded) {
                    c.cost = 1;
                }
            }
        }
    }

    @Override
    public void onRemove() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.cardID.equals(Stumble.ID)) {
                c.retain = false;
                if (bUpgraded) {
                    c.cost = -2;
                }
            }
        }

        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.cardID.equals(Stumble.ID)) {
                c.retain = false;
                if (bUpgraded) {
                    c.cost = -2;
                }
            }
        }

        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c.cardID.equals(Stumble.ID)) {
                c.retain = false;
                if (bUpgraded) {
                    c.cost = -2;
                }
            }
        }

        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c.cardID.equals(Stumble.ID)) {
                c.retain = false;
                if (bUpgraded) {
                    c.cost = -2;
                }
            }
        }
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (bUpgraded) {
            description = DESCRIPTIONS[1];
        } else {
            description = DESCRIPTIONS[0];
        }
    }
}
