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
    public static final String IMG = "images/relics/placeholder_relic2.png";
    public static final String OUTLINE = "images/relics/outline/placeholder_relic2.png";

    public LostAndFoundRelic() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public void onExhaust(AbstractCard card) {
        AbstractPlayer p = AbstractDungeon.player;

        if (card.cost <= 0) {
            return;
        }

        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(
                        p, p, new EvadePower(p, p, card.cost), card.cost
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
