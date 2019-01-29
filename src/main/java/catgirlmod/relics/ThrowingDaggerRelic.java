package catgirlmod.relics;

import basemod.abstracts.CustomRelic;
import catgirlmod.CatGirlMod;
import catgirlmod.powers.EvadePower;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class ThrowingDaggerRelic extends CustomRelic {

    // ID, images, text.
    public static final String ID = CatGirlMod.makeID("ThrowingDagger");
    public static final String IMG = "images/relics/placeholder_relic2.png";
    public static final String OUTLINE = "images/relics/outline/placeholder_relic2.png";

    public ThrowingDaggerRelic() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.CLINK);

        this.counter = 0;
    }

    @Override
    public void atTurnStart() {
        this.counter = 0;
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (targetCard.type == AbstractCard.CardType.ATTACK) {
            this.counter += 1;

            if (this.counter % 3 == 0) {
                this.counter = 0;

                flash();
                AbstractDungeon.actionManager.addToBottom(
                        new RelicAboveCreatureAction(AbstractDungeon.player, this)
                );

                AbstractPlayer p = AbstractDungeon.player;
                AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(
                                p, p, new EvadePower(p, p, 2), 2
                        )
                );
            }
        }
    }

    @Override
    public void onVictory() {
        this.counter = -1;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 3 + this.DESCRIPTIONS[1] + 2 + this.DESCRIPTIONS[2];
    }

    // Which relic to return on making a copy of this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new ThrowingDaggerRelic();
    }
}
