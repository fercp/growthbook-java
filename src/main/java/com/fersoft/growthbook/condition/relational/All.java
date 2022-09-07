package com.fersoft.growthbook.condition.relational;

import com.fersoft.growthbook.condition.AttributeKey;
import com.fersoft.growthbook.condition.DependedCondition;

import java.util.HashSet;
import java.util.List;

/**
 * Check if attribute values contains all condition values.
 *
 * @param <T> Type of values ,should be a List
 */
@AttributeKey("$all")
public class All<T extends List<?>> extends DependedCondition<T, T> {
    /**
     * Constructor.
     *
     * @param value condition values (list type)
     */
    public All(final T value) {
        super(value);
    }

    /**
     * Checks if attribute values contains all condition values.
     *
     * @param attribute list of values from attribute object
     * @return true if attribute values contains condition values o/w false
     */
    @Override
    public boolean eval(final T attribute) {
        if (attribute == null) {
            return false;
        }
        return new HashSet<>(attribute).containsAll(this.value);
    }
}
