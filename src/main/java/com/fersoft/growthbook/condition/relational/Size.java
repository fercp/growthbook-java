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
public class Size<T> extends DependedCondition<Integer, List<T>> {
    /**
     * Constructor.
     *
     * @param size size
     */
    public Size(final Integer size) {
        super(size);
    }

    /**
     * Returns true if size of attribute list is equal to condition value.
     * @param attribute attribute value list
     * @return comparison result.
     */
    @Override
    public boolean eval(final List<T> attribute) {
        if (attribute != null) {
            return attribute.size() == value;
        }
        return false;
    }
}
