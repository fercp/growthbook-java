package com.fersoft.growthbook.condition.relational;

import com.fersoft.growthbook.condition.AttributeKey;
import com.fersoft.growthbook.condition.DependedCondition;

import java.util.List;

/**
 * Evaluates conditon value which is a condition itself
 * against size of attribute list.
 *
 * @param <T> type of attribute list
 */
@AttributeKey("$size")
public class SizeWithCondition<T>
        extends DependedCondition<DependedCondition<Integer, Integer>, List<T>> {
    /**
     * Constructor.
     *
     * @param value condition value which is a condition.
     */
    public SizeWithCondition(final DependedCondition<Integer, Integer> value) {
        super(value);
    }

    /**
     * returns true if size of attribute list evaluates true for the condition value.
     * @param attribute list of attribute values
     * @return comparison result.
     */
    @Override
    public boolean eval(final List<T> attribute) {
        return value.eval(attribute.size());
    }
}
