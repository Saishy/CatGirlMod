package catgirlmod.patches;

import catgirlmod.powers.IncreaseClawDamagePower;
import catgirlmod.powers.IncreaseStrikeDamagePower;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(
        clz=AbstractCard.class,
        method="calculateCardDamage"
)
public class CalculateCardDamagePatch {

    @SpireInsertPatch(
            //rloc=69,
            locator = LocatorSingleEnemy.class,
            localvars = {"p", "tmp"}
    )
    public static void CalculateCardDamage(AbstractCard __instance, AbstractMonster mo, AbstractPower p, @ByRef float[] tmp) {

        IncreaseStrikeDamagePower strikenPower = (p instanceof IncreaseStrikeDamagePower ? (IncreaseStrikeDamagePower)p : null);

        if (strikenPower != null && __instance.hasTag(AbstractCard.CardTags.STRIKE)) {
            //CatGirlMod.logger.debug(mo.name + " have strikenPower: " + strikenPower.amount + "/" + IncreaseStrikeDamagePower.DAMAGE_INCREASE_PER_STACK);
            tmp[0] += strikenPower.amount * IncreaseStrikeDamagePower.DAMAGE_INCREASE_PER_STACK;

            if (__instance.baseDamage != (int)tmp[0]) {
                __instance.isDamageModified = true;
            }
        }

        IncreaseClawDamagePower gashPower = (p instanceof IncreaseClawDamagePower ? (IncreaseClawDamagePower)p : null);

        if (gashPower != null && __instance.hasTag(AbstractCardEnum.CLAW)) {
            //CatGirlMod.logger.debug(mo.name + " have gashPower: " + gashPower.amount + "/" + IncreaseClawDamagePower.DAMAGE_INCREASE_PER_STACK);
            tmp[0] += gashPower.amount * IncreaseClawDamagePower.DAMAGE_INCREASE_PER_STACK;

            if (__instance.baseDamage != (int)tmp[0]) {
                __instance.isDamageModified = true;
            }
        }
    }

    // ModTheSpire searches for a nested class that extends SpireInsertLocator
    // This class will be the Locator for the @SpireInsertPatch
    // When a Locator is not specified, ModTheSpire uses the default behavior for the @SpireInsertPatch
    private static class LocatorSingleEnemy extends SpireInsertLocator {
        // This is the abstract method from SpireInsertLocator that will be used to find the line
        // numbers you want this patch inserted at
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            // finalMatcher is the line that we want to insert our patch before -
            // in this example we are using a `MethodCallMatcher` which is a type
            // of matcher that matches a method call based on the type of the calling
            // object and the name of the method being called. Here you can see that
            // we're expecting the `end` method to be called on a `SpireBatch`
            Matcher finalMatcher = new Matcher.MethodCallMatcher(
                    "com.megacrit.cardcrawl.powers.AbstractPower", "atDamageFinalReceive");

            // the `new ArrayList<Matcher>()` specifies the prerequisites before the line can be matched -
            // the LineFinder will search for all the prerequisites in order before it will match the finalMatcher -
            // since we didn't specify any prerequisites here, the LineFinder will simply find the first expression
            // that matches the finalMatcher.
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
    }

    @SpireInsertPatch(
            //rloc=134,
            locator = LocatorMultiEnemy.class,
            localvars = {"p", "i", "tmp"}
    )
    public static void CalculateCardDamage(AbstractCard __instance, AbstractMonster mo, AbstractPower p, int i, float[] tmp) {

        IncreaseStrikeDamagePower strikenPower = (p instanceof IncreaseStrikeDamagePower ? (IncreaseStrikeDamagePower)p : null);

        if (strikenPower != null && __instance.hasTag(AbstractCard.CardTags.STRIKE)) {
            //CatGirlMod.logger.debug(m.get(i).name + " have strikenPower: " + strikenPower.amount + "/" + IncreaseStrikeDamagePower.DAMAGE_INCREASE_PER_STACK);
            tmp[i] += strikenPower.amount * IncreaseStrikeDamagePower.DAMAGE_INCREASE_PER_STACK;

            if (__instance.baseDamage != (int) tmp[i]) {
                __instance.isDamageModified = true;
            }
        }

        IncreaseClawDamagePower gashPower = (p instanceof IncreaseClawDamagePower ? (IncreaseClawDamagePower)p : null);

        if (gashPower != null && __instance.hasTag(AbstractCardEnum.CLAW)) {
            //CatGirlMod.logger.debug(m.get(i).name + " have gashPower: " + gashPower.amount + "/" + IncreaseClawDamagePower.DAMAGE_INCREASE_PER_STACK);
            tmp[i] += gashPower.amount * IncreaseClawDamagePower.DAMAGE_INCREASE_PER_STACK;

            if (__instance.baseDamage != (int) tmp[i]) {
                __instance.isDamageModified = true;
            }
        }
    }

    private static class LocatorMultiEnemy extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(
                    "com.megacrit.cardcrawl.powers.AbstractPower", "atDamageFinalReceive");

            int[] found = LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);

            return new int[]{found[found.length - 1]};
        }
    }
}
