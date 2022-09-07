package com.fersoft.growthbook.condition.relational;

import com.fersoft.growthbook.condition.AttributeKey;
import com.fersoft.growthbook.condition.DependedCondition;

/**
 * Not equal comparison of condition and attribute.
 *
 * @param <T> type of condition value
 */
@AttributeKey("$ne")
public class Ne<T> extends DependedCondition<T, T> {
    /**
     * Constructor.
     *
     * @param value condition value
     */
    public Ne(final T value) {
        super(value);
    }

    /**
     * returns true if attribute is not equal to condition value.
     *
     * @param attribute attribute value
     * @return comparison result
     */
    @Override
    public boolean eval(final T attribute) {
        return !this.value.equals(attribute);
    }
}
