package com.fersoft.growthbook.condition.relational;

import com.fersoft.growthbook.condition.AttributeKey;
import com.fersoft.growthbook.condition.DependedCondition;

/**
 * Negates result of condition evaluated.
 * @param <T> type of condition value.
 * @param <A> type of attribute value.
 */
@AttributeKey("$not")
public class Not<T, A> extends DependedCondition<DependedCondition<T, A>, A> {
    /**
     * Constructor.
     * @param value condition value which is a condition itself.
     */
    public Not(final DependedCondition<T, A> value) {
        super(value);
    }

    /**
     * returns true if condition value evaluated false else true.
     * @param attribute attribute value
     * @return reverse of condition value execution result.
     */
    @Override
    public boolean eval(A attribute) {
        return !value.eval(attribute);
    }
}
