package com.fersoft.growthbook.condition.relational;

import com.fersoft.growthbook.condition.AttributeKey;
import com.fersoft.growthbook.condition.DependedCondition;

/**
 * Greater than condition.
 * @param <T> condition value
 */
@AttributeKey("$gt")
public class Gt<T extends Comparable<T>> extends DependedCondition<T, T> {
    /**
     * Constructor.
     *
     * @param value condition value
     */
    public Gt(final T value) {
        super(value);
    }

    /**
     * returns true if attribute is greater than condition.
     *
     * @param attribute attribute value
     * @return comparison result
     */
    @Override
    public boolean eval(final T attribute) {
        return attribute.compareTo(this.value) > 0;
    }
}
