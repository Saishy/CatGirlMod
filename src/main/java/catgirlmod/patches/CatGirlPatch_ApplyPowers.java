package catgirlmod.patches;

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

        AbstractPower strikenPower = mo.getPower(CatGirlPowerBuff_IncreaseStrikeDamage.POWER_ID);

        if (strikenPower != null && __instance.hasTag(AbstractCard.CardTags.STRIKE)) {
            tmp += strikenPower.amount * CatGirlPowerBuff_IncreaseStrikeDamage.DAMAGE_INCREASE_PER_STACK;

            if (__instance.baseDamage != (int)tmp) {
                __instance.isDamageModified = true;
            }
        }

        AbstractPower gashPower = mo.getPower(CatGirlPowerBuff_IncreaseClawDamage.POWER_ID);

        if (gashPower != null && __instance.hasTag(AbstractCardEnum.CLAW)) {
            tmp += gashPower.amount * CatGirlPowerBuff_IncreaseClawDamage.DAMAGE_INCREASE_PER_STACK;

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
            AbstractPower strikenPower = ((AbstractMonster) m.get(i)).getPower(CatGirlPowerBuff_IncreaseStrikeDamage.POWER_ID);

            if (strikenPower != null && __instance.hasTag(AbstractCard.CardTags.STRIKE)) {
                tmp[i] += strikenPower.amount * CatGirlPowerBuff_IncreaseStrikeDamage.DAMAGE_INCREASE_PER_STACK;

                if (__instance.baseDamage != (int) tmp[i]) {
                    __instance.isDamageModified = true;
                }
            }

            AbstractPower gashPower = ((AbstractMonster) m.get(i)).getPower(CatGirlPowerBuff_IncreaseClawDamage.POWER_ID);

            if (gashPower != null && __instance.hasTag(AbstractCardEnum.CLAW)) {
                tmp[i] += gashPower.amount * CatGirlPowerBuff_IncreaseClawDamage.DAMAGE_INCREASE_PER_STACK;

                if (__instance.baseDamage != (int) tmp[i]) {
                    __instance.isDamageModified = true;
                }
            }
        }
    }*/
}
