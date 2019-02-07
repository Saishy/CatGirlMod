package catgirlmod.cards.beast;

import catgirlmod.CatGirlMod;
import catgirlmod.cards.AbstractDefaultCard;
import catgirlmod.patches.AbstractCardEnum;
import catgirlmod.powers.BeastificationPower;
import catgirlmod.powers.EvadePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Beastification extends AbstractDefaultCard {

    /*
     * "Hey, I wanna make a bunch of cards now." - You, probably.
     * ok cool my dude no problem here's the most convenient way to do it:
     *
     * Copy all of the code here (Ctrl+A > Ctrl+C)
     * Ctrl+Shift+A and search up "file and code template"
     * Press the + button at the top and name your template whatever it is for - "AttackCard" or "PowerCard" or something up to you.
     * Read up on the instructions at the bottom. Basically replace anywhere you'd put your cards name with BloodScent
     * And then you can do custom ones like $ {DAMAGE} and $ {TARGET} if you want.
     * I'll leave some comments on things you might consider replacing with what.
     *
     * Of course, delete all the comments and add anything you want (For example, if you're making a skill card template you'll
     * likely want to replace that new DamageAction with a gain Block one, and add baseBlock instead, or maybe you want a
     * universal template where you delete everything unnecessary - up to you)
     */

    // TEXT DECLARATION

    public static final String ID = CatGirlMod.makeID("Beastification");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = CatGirlMod.makePath("images/cards/Beastification.png");
    // This does mean that you will need to have an image with the same name as the card in your image folder for it to run correctly.

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.POWER;       //
    public static final CardColor COLOR = AbstractCardEnum.CATGIRL_TEAL;

    private static final int COST = 3;

    private static final int BLOCK = 15;

    private static final int ENERGY_LOST = 2;

    private static final int STRENGTH = 3;
    private static final int UPGRADE_PLUS_STRENGTH = 1;

    private static final int DEXTERITY = 3;
    private static final int UPGRADE_PLUS_DEXTERITY = 1;
    // /STAT DECLARATION/

    public Beastification() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.baseBlock = BLOCK;
        this.magicNumber = this.baseMagicNumber = STRENGTH;
        this.secondMagicNumber = this.baseSecondMagicNumber = DEXTERITY;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(
                        p, p, new StrengthPower(p, magicNumber), magicNumber
                )
        );

        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(
                        p, p, new DexterityPower(p, secondMagicNumber), secondMagicNumber
                )
        );

        AbstractDungeon.actionManager.addToBottom(
                new GainBlockAction(p, p, block)
        );

        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(
                        p, p, new BeastificationPower(p, p, ENERGY_LOST), ENERGY_LOST
                )
        );
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_STRENGTH);
            upgradeSecondMagicNumber(UPGRADE_PLUS_DEXTERITY);
            initializeDescription();
        }
    }
}