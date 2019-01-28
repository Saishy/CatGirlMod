package catgirlmod.relics;

import basemod.abstracts.CustomRelic;
import catgirlmod.CatGirlMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class CatGirlRelic_BeginnersLuck extends CustomRelic {

    // ID, images, text.
    public static final String ID = CatGirlMod.makeID("BeginnersLuck");
    public static final String IMG = "images/relics/placeholder_relic2.png";
    public static final String OUTLINE = "images/relics/outline/placeholder_relic2.png";

    public static final int MAX_STACKS = 3;
    public static final int DRAW_CARD = 1;

    public CatGirlRelic_BeginnersLuck() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(CatGirlRelic_Trained.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); i++) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(CatGirlRelic_Trained.ID)) {
                    this.counter = AbstractDungeon.player.getRelic(CatGirlRelic_Trained.ID).counter;
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
        return AbstractDungeon.player.hasRelic(CatGirlRelic_Trained.ID);
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
        return new CatGirlRelic_BeginnersLuck();
    }
}
