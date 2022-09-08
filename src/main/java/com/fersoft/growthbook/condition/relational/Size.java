package com.fersoft.growthbook.condition.relational;

import com.fersoft.growthbook.condition.AttributeKey;
import com.fersoft.growthbook.condition.DependedCondition;

import java.util.List;

/**
 * Checks the size of atribute value which is a list.
 *
 * @param <T> type of attribute list element
 */
@AttributeKey("$size")
public class Size<V, T> extends DependedCondition<V, List<T>> {
    /**
     * Constructor.
     *
     * @param value size
     */
    public Size(final V value) {
        super(value);
    }

    /**
     * Returns true if size of attribute list is equal to condition value.
     *
     * @param attribute attribute value list
     * @return comparison result.
     */
    @Override
    public boolean eval(final List<T> attribute) {
        if (attribute != null) {
            if (value instanceof Integer) {
                return attribute.size() == (Integer) value;
            } else if (value instanceof List) {
                return ((List<DependedCondition>) value).stream().allMatch(v -> v.eval(attribute.size()));
            }
        }
        return false;
    }
}
