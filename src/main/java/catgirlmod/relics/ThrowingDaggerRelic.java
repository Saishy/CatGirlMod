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
    public static final String IMG = CatGirlMod.makePath("images/relics/throwing_dagger_relic.png");
    public static final String OUTLINE = CatGirlMod.makePath("images/relics/outline/throwing_dagger_relic_outline.png");

    public static final int ATTACKS = 3;
    public static final int EVADE = 4;

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
                                p, p, new EvadePower(p, p, EVADE), EVADE
                        )
                );
            }
        }
    }

    @Override
    public void onVictory() {
        this.counter = 0;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + ATTACKS + this.DESCRIPTIONS[1] + EVADE + this.DESCRIPTIONS[2];
    }

    // Which relic to return on making a copy of this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new ThrowingDaggerRelic();
    }
}
