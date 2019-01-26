package catgirlmod.patches;

import catgirlmod.powers.CatGirlPower_IncreaseClawDamage;
import catgirlmod.powers.CatGirlPower_IncreaseStrikeDamage;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

/*@SpirePatch(
        clz=AbstractCard.class,
        method="applyPowers"
)*/
public class CatGirlPatch_ApplyPowers {

    /*@SpireInsertPatch(
            rloc=52,
            localvars = {"tmp"}
    )
    public static void ApplyPowers(AbstractCard __instance, float tmp) {

        AbstractPower strikenPower = mo.getPower(CatGirlPower_IncreaseStrikeDamage.POWER_ID);

        if (strikenPower != null && __instance.hasTag(AbstractCard.CardTags.STRIKE)) {
            tmp += strikenPower.amount * CatGirlPower_IncreaseStrikeDamage.DAMAGE_INCREASE_PER_STACK;

            if (__instance.baseDamage != (int)tmp) {
                __instance.isDamageModified = true;
            }
        }

        AbstractPower gashPower = mo.getPower(CatGirlPower_IncreaseClawDamage.POWER_ID);

        if (gashPower != null && __instance.hasTag(AbstractCardEnum.CLAW)) {
            tmp += gashPower.amount * CatGirlPower_IncreaseClawDamage.DAMAGE_INCREASE_PER_STACK;

            if (__instance.baseDamage != (int)tmp) {
                __instance.isDamageModified = true;
            }
        }
    }

    @SpireInsertPatch(
            rloc=97,
            localvars = {"m", "tmp"}
    )
    public static void CalculateCardDamage(AbstractCard __instance, ArrayList<AbstractMonster> m, float[] tmp) {

        for (int i = 0; i < tmp.length; i++) {
            AbstractPower strikenPower = ((AbstractMonster) m.get(i)).getPower(CatGirlPower_IncreaseStrikeDamage.POWER_ID);

            if (strikenPower != null && __instance.hasTag(AbstractCard.CardTags.STRIKE)) {
                tmp[i] += strikenPower.amount * CatGirlPower_IncreaseStrikeDamage.DAMAGE_INCREASE_PER_STACK;

                if (__instance.baseDamage != (int) tmp[i]) {
                    __instance.isDamageModified = true;
                }
            }

            AbstractPower gashPower = ((AbstractMonster) m.get(i)).getPower(CatGirlPower_IncreaseClawDamage.POWER_ID);

            if (gashPower != null && __instance.hasTag(AbstractCardEnum.CLAW)) {
                tmp[i] += gashPower.amount * CatGirlPower_IncreaseClawDamage.DAMAGE_INCREASE_PER_STACK;

                if (__instance.baseDamage != (int) tmp[i]) {
                    __instance.isDamageModified = true;
                }
            }
        }
    }*/
}
