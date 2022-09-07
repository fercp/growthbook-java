package com.fersoft.growthbook.condition.relational;

import com.fersoft.growthbook.condition.AttributeKey;
import com.fersoft.growthbook.condition.DependedCondition;

/**
 * Checks if at candition valu and attribute value are equal.
 *
 * @param <T> type of values.
 */
@AttributeKey("$eq")
public class Eq<T> extends DependedCondition<T, T> {
    /**
     * Constructor.
     *
     * @param value condition value
     */
    public Eq(final T value) {
        super(value);
    }

    /**
     * checks equality.
     *
     * @param attribute attribute value
     * @return true if condition and attribute value are same else false.
     */
    @Override
    public boolean eval(final T attribute) {
        return this.value.equals(attribute);
    }
}
