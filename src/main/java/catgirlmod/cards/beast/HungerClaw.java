package catgirlmod.cards.beast;

import catgirlmod.CatGirlMod;
import catgirlmod.cards.AbstractDefaultCard;
import catgirlmod.powers.IncreaseClawDamagePower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.unique.FeedAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import catgirlmod.patches.AbstractCardEnum;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class HungerClaw extends AbstractDefaultCard {

    /*
     * "Hey, I wanna make a bunch of cards now." - You, probably.
     * ok cool my dude no problem here's the most convenient way to do it:
     *
     * Copy all of the code here (Ctrl+A > Ctrl+C)
     * Ctrl+Shift+A and search up "file and code template"
     * Press the + button at the top and name your template whatever it is for - "AttackCard" or "PowerCard" or something up to you.
     * Read up on the instructions at the bottom. Basically replace anywhere you'd put your cards name with HungerClaw
     * And then you can do custom ones like $ {DAMAGE} and $ {TARGET} if you want.
     * I'll leave some comments on things you might consider replacing with what.
     *
     * Of course, delete all the comments and add anything you want (For example, if you're making a skill card template you'll
     * likely want to replace that new DamageAction with a gain Block one, and add baseBlock instead, or maybe you want a
     * universal template where you delete everything unnecessary - up to you)
     */

    // TEXT DECLARATION

    public static final String ID = CatGirlMod.makeID("HungerClaw");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = "images/cards/HungerClaw.png";
    // This does mean that you will need to have an image with the same name as the card in your image folder for it to run correctly.

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = AbstractCardEnum.CATGIRL_TEAL;

    private static final int COST = 1;

    private static final int DAMAGE = 18;

    private static final int INCREASE_MAX_HP = 3;
    private static final int UPGRADE_PLUS_INCREASE_MAX_HP = 1;

    // /STAT DECLARATION/

    public HungerClaw() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = INCREASE_MAX_HP;

        this.exhaust = true;

        this.tags.add(AbstractCardEnum.CLAW);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int currentGashStack = 0;

        for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            AbstractPower gashPower = mo.getPower(IncreaseClawDamagePower.POWER_ID);

            if (gashPower != null) {
                if (gashPower.amount > currentGashStack) {
                    m = mo;
                } else if (gashPower.amount == currentGashStack && (Math.random() < 0.5f)) {
                    m = mo;
                }
            }
        }

        if (m == null) {
            m = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        }

        calculateCardDamage(m);

        if (m != null) {
            AbstractDungeon.actionManager.addToBottom(
                    new VFXAction(new com.megacrit.cardcrawl.vfx.combat.ClawEffect(m.hb.cX, m.hb.cY, Color.SCARLET, Color.WHITE), 0.1F)
            );
        }

        AbstractDungeon.actionManager.addToBottom(
                new FeedAction(
                        m, new com.megacrit.cardcrawl.cards.DamageInfo(p, this.damage, this.damageTypeForTurn), this.magicNumber
                )
        );
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_INCREASE_MAX_HP);
            initializeDescription();
        }
    }
}