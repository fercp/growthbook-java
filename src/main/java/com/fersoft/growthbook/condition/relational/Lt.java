package com.fersoft.growthbook.condition.relational;

import com.fersoft.growthbook.condition.AttributeKey;
import com.fersoft.growthbook.condition.DependedCondition;

/**
 * Less than comparison of attribute vs condition.
 * @param <T> type of condition value.
 */
@AttributeKey("$lt")
public class Lt<T extends Comparable<T>> extends DependedCondition<T, T> {
    /**
     * Constructor.
     * @param value condition value.
     */
    public Lt(final T value) {
        super(value);
    }

    /**
     * returns true if attribute is less than condition.
     *
     * @param attribute attribute value
     * @return comparison result
     */
    @Override
    public boolean eval(T attribute) {
        return attribute.compareTo(this.value) < 0;
    }
}
