package com.fersoft.growthbook.condition.relational;

import com.fersoft.growthbook.condition.AttributeKey;
import com.fersoft.growthbook.condition.DependedCondition;

import java.util.List;

/**
 * Checks if attribute value is <b>not</b> in the condition value list.
 *
 * @param <T> type of condition value
 */
@AttributeKey("$nin")
public class NotIn<T> extends DependedCondition<List<T>, T> {
    /**
     * Constructor.
     *
     * @param value condition value list.
     */
    public NotIn(final List<T> value) {
        super(value);
    }

    /**
     * returns true if attribute is not in the condition list.
     *
     * @param attribute attribute value.
     * @return in check result.
     */
    @Override
    public boolean eval(final T attribute) {
        return !value.contains(attribute);
    }
}
