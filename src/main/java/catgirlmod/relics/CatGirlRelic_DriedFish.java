package catgirlmod.relics;

import basemod.abstracts.CustomRelic;
import catgirlmod.CatGirlMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class CatGirlRelic_DriedFish extends CustomRelic {

    // ID, images, text.
    public static final String ID = CatGirlMod.makeID("DriedFish");
    public static final String IMG = "images/relics/placeholder_relic2.png";
    public static final String OUTLINE = "images/relics/outline/placeholder_relic2.png";

    private static final int NUM_CARDS = 10;
    public static final int HEAL_AMOUNT = 2;

    public CatGirlRelic_DriedFish() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.MAGICAL);

        this.counter = 0;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK) {
            this.counter += 1;

            if (this.counter % 10 == 0) {
                this.counter = 0;
                flash();
                AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction(AbstractDungeon.player, this));
                AbstractDungeon.player.heal(HEAL_AMOUNT);
            }
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 10 + this.DESCRIPTIONS[1];
    }

    // Which relic to return on making a copy of this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new CatGirlRelic_DriedFish();
    }
}
