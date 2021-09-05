package catgirlmod.relics;

import basemod.abstracts.CustomRelic;
import catgirlmod.CatGirlMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class BeginnersLuckRelic extends CustomRelic {

    // ID, images, text.
    public static final String ID = CatGirlMod.makeID("BeginnersLuck");
    public static final String IMG = CatGirlMod.makePath("images/relics/beginners_luck_relic.png");
    public static final String OUTLINE = CatGirlMod.makePath("images/relics/outline/beginners_luck_relic_outline.png");

    public static final int MAX_STACKS = 3;
    public static final int DRAW_CARD = 1;

    public BeginnersLuckRelic() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void obtain() {
        //CatGirlMod.logger.debug("Starting obtain()");
        if (AbstractDungeon.player.hasRelic(TrainedRelic.ID)) {
            //CatGirlMod.logger.debug("Has TrainedRelic, amount of relics: " + AbstractDungeon.player.relics.size());
            for (int i = 0; i < AbstractDungeon.player.relics.size(); i++) {
                //CatGirlMod.logger.debug("i = " + i);
                if (AbstractDungeon.player.relics.get(i).relicId.equals(TrainedRelic.ID)) {
                    //CatGirlMod.logger.debug("Found TrainedRelic at slot: " + i);
                    instantObtain(AbstractDungeon.player, i, true);

                    break;
                }
            }

        } else {
            super.obtain();
        }
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(TrainedRelic.ID);
    }

    @Override
    public void onManualDiscard() {
        if (counter <= 0) {
            return;
        }

        counter -= 1;

        AbstractDungeon.actionManager.addToBottom(
                new com.megacrit.cardcrawl.actions.common.DrawCardAction(AbstractDungeon.player, DRAW_CARD)
        );
    }

    @Override
    public void atTurnStart() {
        flash();
        counter = MAX_STACKS;
    }

    @Override
    public void onVictory() {
        counter = -1;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + MAX_STACKS + this.DESCRIPTIONS[1];
    }

    // Which relic to return on making a copy of this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new BeginnersLuckRelic();
    }
}
