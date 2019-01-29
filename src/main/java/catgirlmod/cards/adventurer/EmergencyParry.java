package catgirlmod.cards.adventurer;

import catgirlmod.CatGirlMod;
import catgirlmod.cards.AbstractDefaultCard;
import catgirlmod.powers.EvadePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import catgirlmod.patches.AbstractCardEnum;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EmergencyParry extends AbstractDefaultCard {

    /*
     * "Hey, I wanna make a bunch of cards now." - You, probably.
     * ok cool my dude no problem here's the most convenient way to do it:
     *
     * Copy all of the code here (Ctrl+A > Ctrl+C)
     * Ctrl+Shift+A and search up "file and code template"
     * Press the + button at the top and name your template whatever it is for - "AttackCard" or "PowerCard" or something up to you.
     * Read up on the instructions at the bottom. Basically replace anywhere you'd put your cards name with EmergencyParry
     * And then you can do custom ones like $ {DAMAGE} and $ {TARGET} if you want.
     * I'll leave some comments on things you might consider replacing with what.
     *
     * Of course, delete all the comments and add anything you want (For example, if you're making a skill card template you'll
     * likely want to replace that new DamageAction with a gain Block one, and add baseBlock instead, or maybe you want a
     * universal template where you delete everything unnecessary - up to you)
     */

    // TEXT DECLARATION

    public static final String ID = CatGirlMod.makeID("EmergencyParry");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = "images/cards/Skill.png"; // "images/cards/EmergencyParry.png"
    // This does mean that you will need to have an image with the same name as the card in your image folder for it to run correctly.

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = AbstractCardEnum.CATGIRL_TEAL;

    private static final int COST = 1;

    private static final int BLOCK_PER_EVADE_MULTIPLIER = 1;
    private static final int UPGRADE_PLUS_BLOCK_PER_EVADE_MULTIPLIER = 1;

    // /STAT DECLARATION/

    public EmergencyParry() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = BLOCK_PER_EVADE_MULTIPLIER;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower evadePower = p.getPower(EvadePower.POWER_ID);

        if (evadePower != null) {
            int evadeAmount = evadePower.amount;

            AbstractDungeon.actionManager.addToBottom(
                    new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(
                            p, p, EvadePower.POWER_ID
                    )
            );

            AbstractDungeon.actionManager.addToBottom(
                    new com.megacrit.cardcrawl.actions.common.GainBlockAction(p, p, evadeAmount * magicNumber)
            );
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_BLOCK_PER_EVADE_MULTIPLIER);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}