package com.fersoft.growthbook.condition;

import com.fersoft.growthbook.condition.logical.And;
import com.fersoft.growthbook.condition.logical.Nor;
import com.fersoft.growthbook.condition.logical.Or;
import com.fersoft.growthbook.condition.relational.*;

public enum ConditionTypes {
    AND(And.class), $OR(Or.class), NOT(com.fersoft.growthbook.condition.logical.Not.class), NOR(Nor.class),
    ALL(All.class), EQ(Eq.class), NE(Ne.class), IN(In.class), REGEX(Regex.class), GT(Gt.class), LT(Lt.class), GTE(Gte.class),
    LTE(Lte.class), EXISTS(Exists.class), NIN(NotIn.class), ELEM_MATCH(ElemMatch.class), TYPE(Type.class), SIZE(Size.class);

    private final Class<? extends DependedCondition<?, ?>> clazz;


    ConditionTypes(final Class clazz) {
        this.clazz = clazz;
    }

    public static Class<? extends DependedCondition<?, ?>> getConditionClass(final String type) {
        for (ConditionTypes condition : ConditionTypes.values()) {
            AttributeKey key = condition.getConditionClass().getAnnotation(AttributeKey.class);
            if (key != null && key.value().equals(type)) {
                return condition.getConditionClass();
            }
        }
        throw new IllegalArgumentException("illegal type " + type);
    }

    private Class<? extends DependedCondition<?, ?>> getConditionClass() {
        return this.clazz;
    }
}
