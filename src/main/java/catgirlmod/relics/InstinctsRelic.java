package catgirlmod.relics;

import basemod.abstracts.CustomRelic;
import catgirlmod.CatGirlMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class InstinctsRelic extends CustomRelic {

    // ID, images, text.
    public static final String ID = CatGirlMod.makeID("Instincts");
    public static final String IMG = CatGirlMod.makePath("images/relics/placeholder_relic2.png");
    public static final String OUTLINE = CatGirlMod.makePath("images/relics/outline/placeholder_relic2.png");

    public static final int INITIAL_STRENGTH = 3;
    public static final int LOSE_STRENGTH = 1;

    public InstinctsRelic() {
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
    public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;

        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new com.megacrit.cardcrawl.powers.StrengthPower(p, INITIAL_STRENGTH), INITIAL_STRENGTH)
        );

        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new com.megacrit.cardcrawl.powers.LoseStrengthPower(p, LOSE_STRENGTH), LOSE_STRENGTH)
        );
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + INITIAL_STRENGTH + this.DESCRIPTIONS[1] + LOSE_STRENGTH + this.DESCRIPTIONS[2];
    }

    // Which relic to return on making a copy of this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new InstinctsRelic();
    }
}
