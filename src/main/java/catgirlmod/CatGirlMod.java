package catgirlmod;

import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import catgirlmod.cards.basic.*;
import catgirlmod.cards.adventurer.*;
import catgirlmod.cards.beast.*;
import catgirlmod.cards.test.*;
import catgirlmod.relics.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import catgirlmod.characters.TheCatGirl;
import catgirlmod.patches.AbstractCardEnum;
import catgirlmod.patches.TheCatGirlEnum;
import catgirlmod.potions.PlaceholderPotion;
import catgirlmod.variables.DefaultCustomVariable;
import catgirlmod.variables.DefaultSecondMagicNumber;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;

//TODO: FIRST THINGS FIRST: RENAME YOUR PACKAGE AND ID NAMES FIRST-THING!!!
// Right click the package (folder with black dot on it. the name's at the very top) -> Refactor -> Rename, and name it whatever you wanna call your mod.
// Scroll to the very bottom of this file. Change the id string from "theDefault:" to "yourModName:" or whatever your heart desires (don't use spaces).
// Start with DefaultCommonAttack - it is the most commented card right now.
// After you sorta get the hang of how to make cards, check out the card template which will make your life easier

/*
 * With that out of the way:
 * Welcome to this mildly over-commented Slay the Spire modding base.
 * Use it to make your own mod of any type. - If you want to add any standard in-game content (Character,
 * cards, relics), this is a good starting point.
 * It features 1 character with a minimal set of things: 1 card of each type, 1 debuff, 3 types of relics, etc.
 * If you're new to modding, you basically *need* the BaseMod wiki for whatever you wish to add
 * https://github.com/daviscook477/BaseMod/wiki - work your way through with this base.
 * Feel free to use this in any way you like, of course. Happy modding!
 */

//NOTE: ASD
@SpireInitializer
public class CatGirlMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber {
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(CatGirlMod.class.getName());

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "CatGirl Mod";
    private static final String AUTHOR = "Saishy";
    private static final String DESCRIPTION = "Adds a catgirl adventurer as a playable character.";

    // =============== INPUT TEXTURE LOCATION =================

    // Colors (RGB)
    // Character Color
    public static final Color CATGIRL_TEAL = CardHelper.getColor(44.0f, 90.0f, 104.0f);

    // Potion Colors in RGB
    public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red
    public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
    public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown

    // Card backgrounds - The actual rectangular card.
    private static final String ATTACK_CATGIRL_TEAL = "images/512/bg_attack_catgirl.png";
    private static final String SKILL_CATGIRL_TEAL = "images/512/bg_skill_catgirl.png";
    private static final String POWER_CATGIRL_TEAL = "images/512/bg_power_catgirl.png";
    private static final String ENERGY_ORB_CATGIRL_TEAL = "images/512/card_catgirl_orb.png";
    private static final String CARD_ENERGY_ORB = "images/512/card_catgirl_small_orb.png";

    private static final String ATTACK_CATGIRL_TEAL_PORTRAIT = "images/1024/bg_attack_catgirl.png";
    private static final String SKILL_CATGIRL_TEAL_PORTRAIT = "images/1024/bg_skill_catgirl.png";
    private static final String POWER_CATGIRL_TEAL_PORTRAIT = "images/1024/bg_power_catgirl.png";
    private static final String ENERGY_ORB_CATGIRL_TEAL_PORTRAIT = "images/1024/card_catgirl_orb.png";

    // Character assets
    private static final String THE_CATGIRL_BUTTON = "images/charSelect/CatGirlCharacterButton.png";
    private static final String THE_CATGIRL_PORTRAIT = "images/charSelect/CatGirlCharacterPortraitBG.png";

    public static final String THE_CATGIRL_SHOULDER_1 = "images/char/catgirlCharacter/shoulder.png";
    public static final String THE_CATGIRL_SHOULDER_2 = "images/char/catgirlCharacter/shoulder2.png";
    public static final String THE_CATGIRL_CORPSE = "images/char/catgirlCharacter/corpse.png";

    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "images/Badge.png";

    // =============== /INPUT TEXTURE LOCATION/ =================


    // =============== SUBSCRIBE, CREATE THE COLOR, INITIALIZE =================

    public CatGirlMod() {
        logger.info("Subscribe to BaseMod hooks");

        BaseMod.subscribe(this);

        logger.info("Done subscribing");

        logger.info("Creating the color " + AbstractCardEnum.CATGIRL_TEAL.toString());

        BaseMod.addColor(AbstractCardEnum.CATGIRL_TEAL, CATGIRL_TEAL, CATGIRL_TEAL, CATGIRL_TEAL,
                CATGIRL_TEAL, CATGIRL_TEAL, CATGIRL_TEAL, CATGIRL_TEAL,
                ATTACK_CATGIRL_TEAL, SKILL_CATGIRL_TEAL, POWER_CATGIRL_TEAL, ENERGY_ORB_CATGIRL_TEAL,
                ATTACK_CATGIRL_TEAL_PORTRAIT, SKILL_CATGIRL_TEAL_PORTRAIT, POWER_CATGIRL_TEAL_PORTRAIT,
                ENERGY_ORB_CATGIRL_TEAL_PORTRAIT, CARD_ENERGY_ORB);

        logger.info("Done creating the color");
    }

    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        CatGirlMod catgirlmod = new CatGirlMod();
        logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }

    // ============== /SUBSCRIBE, CREATE THE COLOR, INITIALIZE/ =================


    // =============== LOAD THE CHARACTER =================

    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + TheCatGirlEnum.THE_CATGIRL.toString());

        BaseMod.addCharacter(new TheCatGirl("the CatGirl", TheCatGirlEnum.THE_CATGIRL),
                THE_CATGIRL_BUTTON, THE_CATGIRL_PORTRAIT, TheCatGirlEnum.THE_CATGIRL);

        receiveEditPotions();
        logger.info("Added " + TheCatGirlEnum.THE_CATGIRL.toString());
    }

    // =============== /LOAD THE CHARACTER/ =================


    // =============== POST-INITIALIZE =================


    @Override
    public void receivePostInitialize() {

        logger.info("Loading badge image and mod options");
        // Load the Mod Badge
        Texture badgeTexture = new Texture(BADGE_IMAGE);

        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        settingsPanel.addUIElement(new ModLabel("CatGirlMod doesn't have any settings! An example of those may come later.", 400.0f, 700.0f,
                settingsPanel, (me) -> {
        }));
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        logger.info("Done loading badge Image and mod options");

    }

    // =============== / POST-INITIALIZE/ =================


    // ================ ADD POTIONS ===================


    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");

        // Class Specific Potion. If you want your potion to not be class-specific,
        // just remove the player class at the end (in this case the "TheCatGirlEnum.THE_CATGIRL".
        // Remember, you can press ctrl+P inside parentheses like addPotions)
        BaseMod.addPotion(PlaceholderPotion.class, PLACEHOLDER_POTION_LIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, PlaceholderPotion.POTION_ID, TheCatGirlEnum.THE_CATGIRL);

        logger.info("Done editing potions");
    }

    // ================ /ADD POTIONS/ ===================


    // ================ ADD RELICS ===================

    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        BaseMod.addRelicToCustomPool(new TrainedRelic(), AbstractCardEnum.CATGIRL_TEAL);

        BaseMod.addRelicToCustomPool(new LostAndFoundRelic(), AbstractCardEnum.CATGIRL_TEAL);
        BaseMod.addRelicToCustomPool(new MonsterGuideRelic(), AbstractCardEnum.CATGIRL_TEAL);
        BaseMod.addRelicToCustomPool(new ThrowingDaggerRelic(), AbstractCardEnum.CATGIRL_TEAL);
        //Boss Relics
        //BaseMod.addRelicToCustomPool(new BeginnersLuckRelic(), AbstractCardEnum.CATGIRL_TEAL);
        //BaseMod.addRelicToCustomPool(new InstinctsRelic(), AbstractCardEnum.CATGIRL_TEAL);
        BaseMod.addRelicToCustomPool(new SRankRelic(), AbstractCardEnum.CATGIRL_TEAL);

      //  BaseMod.addRelicToCustomPool(new DefaultClickableRelic(), AbstractCardEnum.CATGIRL_TEAL);

        // This adds a relic to the Shared pool. Every character can find this relic.
        //BaseMod.addRelic(new PlaceholderRelic2(), RelicType.SHARED);
        BaseMod.addRelic(new AmbushRelic(), RelicType.SHARED);
        BaseMod.addRelic(new DriedFishRelic(), RelicType.SHARED);

        logger.info("Done adding relics!");
    }

    // ================ /ADD RELICS/ ===================


    // ================ ADD CARDS ===================

    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        // Add the Custom Dynamic Variables
        logger.info("Add variables");
        // Add the Custom Dynamic variables
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());

        logger.info("Adding cards");
        // Add the cards
        // Basic
        BaseMod.addCard(new ClawStrike());
        BaseMod.addCard(new Strike());
        BaseMod.addCard(new Defend());
        BaseMod.addCard(new Evasion());

        // Adventurer
        BaseMod.addCard(new AllIn());
        BaseMod.addCard(new ElegantStrike());
        BaseMod.addCard(new FocusedStrike());
        BaseMod.addCard(new Parry());
        BaseMod.addCard(new PlayAround());
        BaseMod.addCard(new PoisedAttack());
        BaseMod.addCard(new SpinningStrike());

        BaseMod.addCard(new AdaptivePacing());
        BaseMod.addCard(new ChangeOfPlans());
        BaseMod.addCard(new DefensiveStance());
        BaseMod.addCard(new EmergencyParry());
        BaseMod.addCard(new KeepAlert());
        BaseMod.addCard(new LearnThePattern());
        BaseMod.addCard(new PerfectBlock());
        BaseMod.addCard(new Prepared());
        BaseMod.addCard(new TwoStep());

        BaseMod.addCard(new AdventurersInsight());
        BaseMod.addCard(new Counterattack());
        BaseMod.addCard(new PoiseStance());

        // Beast
        BaseMod.addCard(new Claws());

        // Clumsy


        // Test
        BaseMod.addCard(new MultiHitStrikenTest());
        BaseMod.addCard(new MultiHitGashTest());
        BaseMod.addCard(new AllEnemyStrike());
        BaseMod.addCard(new AllEnemyClaw());

        /*BaseMod.addCard(new DefaultSecondMagicNumberSkill());
        //BaseMod.addCard(new DefaultCommonAttack());
        BaseMod.addCard(new DefaultAttackWithVariable());
        //BaseMod.addCard(new DefaultCommonSkill());
        BaseMod.addCard(new DefaultCommonPower());
        BaseMod.addCard(new DefaultUncommonSkill());
        BaseMod.addCard(new DefaultUncommonAttack());
        BaseMod.addCard(new DefaultUncommonPower());
        BaseMod.addCard(new DefaultRareAttack());
        BaseMod.addCard(new DefaultRareSkill());
        BaseMod.addCard(new DefaultRarePower());*/

        logger.info("Making sure the basic cards are unlocked.");
        // Unlock the cards
        // Basic
        UnlockTracker.unlockCard(ClawStrike.ID);
        UnlockTracker.unlockCard(Strike.ID);
        UnlockTracker.unlockCard(Defend.ID);
        UnlockTracker.unlockCard(Evasion.ID);

        // Adventurer
        //UnlockTracker.unlockCard(Parry.ID);
        //UnlockTracker.unlockCard(PlayAround.ID);

        // Beast
        //UnlockTracker.unlockCard(Claws.ID);

        // Clumsy

        // Test

        /*UnlockTracker.unlockCard(OrbSkill.ID);
        UnlockTracker.unlockCard(DefaultSecondMagicNumberSkill.ID);
        UnlockTracker.unlockCard(DefaultCommonAttack.ID);
        UnlockTracker.unlockCard(DefaultAttackWithVariable.ID);
        UnlockTracker.unlockCard(DefaultCommonSkill.ID);
        UnlockTracker.unlockCard(DefaultCommonPower.ID);
        UnlockTracker.unlockCard(DefaultUncommonSkill.ID);
        UnlockTracker.unlockCard(DefaultUncommonAttack.ID);
        UnlockTracker.unlockCard(DefaultUncommonPower.ID);
        UnlockTracker.unlockCard(DefaultRareAttack.ID);
        UnlockTracker.unlockCard(DefaultRareSkill.ID);
        UnlockTracker.unlockCard(DefaultRarePower.ID);*/

        logger.info("Done adding cards!");
    }

    // There are better ways to do this than listing every single individual card, but I do not want to complicate things
    // in a "tutorial" mod. This will do and it's completely ok to use. If you ever want to clean up and
    // shorten all the imports, go look take a look at other mods, such Conspire or Hubris.

    // ================ /ADD CARDS/ ===================


    // ================ LOAD THE TEXT ===================

    @Override
    public void receiveEditStrings() {
        logger.info("Beginning to edit strings");

        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                "localization/eng/CatGirlMod-Card-Strings.json");

        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                "localization/eng/CatGirlMod-Power-Strings.json");

        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                "localization/eng/CatGirlMod-Relic-Strings.json");

        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                "localization/eng/CatGirlMod-Potion-Strings.json");

        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                "localization/eng/CatGirlMod-Character-Strings.json");

        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                "localization/eng/CatGirlMod-Orb-Strings.json");

        logger.info("Done edittting strings");
    }

    // ================ /LOAD THE TEXT/ ===================

    // ================ LOAD THE KEYWORDS ===================

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal("localization/eng/CatGirlMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    // ================ /LOAD THE KEYWORDS/ ===================    

    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return "theCatGirl:" + idText;
    }

}