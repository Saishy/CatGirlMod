package catgirlmod.relics;

import basemod.abstracts.CustomRelic;
import catgirlmod.CatGirlMod;
import catgirlmod.powers.EvadePower;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class LostAndFoundRelic extends CustomRelic {

    // ID, images, text.
    public static final String ID = CatGirlMod.makeID("LostAndFound");
    public static final String IMG = CatGirlMod.makePath("images/relics/lost_and_found_relic.png");
    public static final String OUTLINE = CatGirlMod.makePath("images/relics/outline/lost_and_found_relic_outline.png");

    public LostAndFoundRelic() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public void onExhaust(AbstractCard card) {
        AbstractPlayer p = AbstractDungeon.player;

        int evadeGiven = card.cost * 2;
        if (evadeGiven <= 0) {
            evadeGiven = 1;
        }

        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(
                        p, p, new EvadePower(p, p, evadeGiven), evadeGiven
                )
        );
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    // Which relic to return on making a copy of this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new LostAndFoundRelic();
    }
}
