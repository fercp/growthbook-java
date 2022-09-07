package com.fersoft.growthbook.condition.relational;

import com.fersoft.growthbook.condition.AttributeKey;
import com.fersoft.growthbook.condition.DependedCondition;

/**
 * Less than or equal comparison of attribute vs condition.
 *
 * @param <T> type of condition.
 */
@AttributeKey("$lte")
public class Lte<T extends Comparable<T>> extends DependedCondition<T, T> {
    /**
     * Constructor.
     *
     * @param value condition value
     */
    public Lte(final T value) {
        super(value);
    }

    /**
     * returns true if attribute is less than or equal to condition.
     *
     * @param attribute attribute value
     * @return comparison result
     */
    @Override
    public boolean eval(final T attribute) {
        return attribute.compareTo(this.value) <= 0;
    }
}
