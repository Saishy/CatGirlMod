package catgirlmod.relics;

import basemod.abstracts.CustomRelic;
import catgirlmod.CatGirlMod;
import catgirlmod.powers.CatGirlPowerBuff_Evade;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class CatGirlRelic_SRank extends CustomRelic {

    // ID, images, text.
    public static final String ID = CatGirlMod.makeID("SRank");
    public static final String IMG = "images/relics/placeholder_relic2.png";
    public static final String OUTLINE = "images/relics/outline/placeholder_relic2.png";

    public static final int AMOUNT = 2;

    public CatGirlRelic_SRank() {
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
    public int onAttacked(DamageInfo info, int damageAmount) {
        //CatGirlMod.logger.info("Catgirl info output: " + info.output);
        //CatGirlMod.logger.info("Catgirl type output: " + info.type);
        if (info.type == DamageInfo.DamageType.NORMAL) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(
                            AbstractDungeon.player, AbstractDungeon.player, new CatGirlPowerBuff_Evade(AbstractDungeon.player, AbstractDungeon.player, AMOUNT), AMOUNT
                    )
            );
        }

        return super.onAttacked(info, damageAmount);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

    // Which relic to return on making a copy of this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new CatGirlRelic_SRank();
    }
}
