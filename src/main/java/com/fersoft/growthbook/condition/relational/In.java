package com.fersoft.growthbook.condition.relational;

import com.fersoft.growthbook.condition.AttributeKey;
import com.fersoft.growthbook.condition.DependedCondition;

import java.util.List;

/**
 * Checks if attribute value is in condition value list.
 *
 * @param <T> type of condition value
 */
@AttributeKey("$in")
public class In<T> extends DependedCondition<List<T>, T> {
    /**
     * Constructor.
     *
     * @param value condition value list.
     */
    public In(final List<T> value) {
        super(value);
    }

    /**
     * returns true if attribute is in condition list.
     *
     * @param attribute attribute value.
     * @return in check result.
     */
    @Override
    public boolean eval(final T attribute) {
        return value.contains(attribute);
    }
}
