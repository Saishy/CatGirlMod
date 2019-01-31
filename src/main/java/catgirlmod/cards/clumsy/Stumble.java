package catgirlmod.cards.clumsy;

import catgirlmod.CatGirlMod;
import catgirlmod.cards.AbstractDefaultCard;
import catgirlmod.powers.PilingErrorsPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import catgirlmod.patches.AbstractCardEnum;

public class Stumble extends AbstractDefaultCard {

    /*
     * "Hey, I wanna make a bunch of cards now." - You, probably.
     * ok cool my dude no problem here's the most convenient way to do it:
     *
     * Copy all of the code here (Ctrl+A > Ctrl+C)
     * Ctrl+Shift+A and search up "file and code template"
     * Press the + button at the top and name your template whatever it is for - "AttackCard" or "PowerCard" or something up to you.
     * Read up on the instructions at the bottom. Basically replace anywhere you'd put your cards name with Stumble
     * And then you can do custom ones like $ {DAMAGE} and $ {TARGET} if you want.
     * I'll leave some comments on things you might consider replacing with what.
     *
     * Of course, delete all the comments and add anything you want (For example, if you're making a skill card template you'll
     * likely want to replace that new DamageAction with a gain Block one, and add baseBlock instead, or maybe you want a
     * universal template where you delete everything unnecessary - up to you)
     */

    // TEXT DECLARATION

    public static final String ID = CatGirlMod.makeID("Stumble");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG =  "images/cards/Stumble.png";
    // This does mean that you will need to have an image with the same name as the card in your image folder for it to run correctly.

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.STATUS;       //
    public static final CardColor COLOR = AbstractCardEnum.CATGIRL_TEAL;

    private static final int COST = -2;

    private static final int DAMAGE = 6;

    private boolean bPowered = false;

    // /STAT DECLARATION/

    public Stumble() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.baseDamage = DAMAGE;
    }

    @Override
    public void applyPowers() {
        PilingErrorsPower pPower = (PilingErrorsPower)AbstractDungeon.player.getPower(PilingErrorsPower.POWER_ID);

        if (pPower != null) {
            retain = true;

            rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];

            //CatGirlMod.logger.debug("Stumble cost before:  " + cost);
            if (pPower.bUpgraded) {
               // modifyCostForCombat(COST);
                costForTurn = 1;
                cost = 1;
                isCostModified = true;
                bPowered = true;
                exhaust = true;
                rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0] + EXTENDED_DESCRIPTION[1];
                //CatGirlMod.logger.debug("Stumble cost now:  " + cost);
            } else {
                cost = COST;
                costForTurn = COST;
                isCostModified = false;
                bPowered = false;
                //CatGirlMod.logger.debug("Stumble cost now:  " + cost);
            }
        } else {
            retain = false;
            bPowered = false;
        }

        super.applyPowers();

        initializeDescription();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return (bPowered) && hasEnoughEnergy();
    }

    private void Clumsy() {
        AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);

        if (m == null) {
            return;
        }

        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT)
        );
    }

    @Override
    public void triggerOnManualDiscard() {
        AbstractDungeon.actionManager.addToBottom(
                new ExhaustSpecificCardAction(this,  AbstractDungeon.player.discardPile)
        );
    }

    @Override
    public void triggerOnExhaust() {
        Clumsy();
    }

    @Override
    public void use(AbstractPlayer paramAbstractPlayer, AbstractMonster paramAbstractMonster) {
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
}