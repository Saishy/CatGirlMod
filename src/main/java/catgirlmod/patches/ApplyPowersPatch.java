package catgirlmod.patches;

/*@SpirePatch(
        clz=AbstractCard.class,
        method="applyPowers"
)*/
public class ApplyPowersPatch {

    /*@SpireInsertPatch(
            rloc=52,
            localvars = {"tmp"}
    )
    public static void ApplyPowers(AbstractCard __instance, float tmp) {

        AbstractPower strikenPower = mo.getPower(IncreaseStrikeDamagePower.POWER_ID);

        if (strikenPower != null && __instance.hasTag(AbstractCard.CardTags.STRIKE)) {
            tmp += strikenPower.amount * IncreaseStrikeDamagePower.DAMAGE_INCREASE_PER_STACK;

            if (__instance.baseDamage != (int)tmp) {
                __instance.isDamageModified = true;
            }
        }

        AbstractPower gashPower = mo.getPower(IncreaseClawDamagePower.POWER_ID);

        if (gashPower != null && __instance.hasTag(AbstractCardEnum.CLAW)) {
            tmp += gashPower.amount * IncreaseClawDamagePower.DAMAGE_INCREASE_PER_STACK;

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
            AbstractPower strikenPower = ((AbstractMonster) m.get(i)).getPower(IncreaseStrikeDamagePower.POWER_ID);

            if (strikenPower != null && __instance.hasTag(AbstractCard.CardTags.STRIKE)) {
                tmp[i] += strikenPower.amount * IncreaseStrikeDamagePower.DAMAGE_INCREASE_PER_STACK;

                if (__instance.baseDamage != (int) tmp[i]) {
                    __instance.isDamageModified = true;
                }
            }

            AbstractPower gashPower = ((AbstractMonster) m.get(i)).getPower(IncreaseClawDamagePower.POWER_ID);

            if (gashPower != null && __instance.hasTag(AbstractCardEnum.CLAW)) {
                tmp[i] += gashPower.amount * IncreaseClawDamagePower.DAMAGE_INCREASE_PER_STACK;

                if (__instance.baseDamage != (int) tmp[i]) {
                    __instance.isDamageModified = true;
                }
            }
        }
    }*/
}
