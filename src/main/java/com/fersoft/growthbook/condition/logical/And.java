package com.fersoft.growthbook.condition.logical;

import com.fersoft.growthbook.condition.AttributeKey;
import com.fersoft.growthbook.condition.FreeCondition;

import java.util.List;
import java.util.Map;

/**
 * Logical AND operator ,evaluates oll conditions against given attributes.
 *
 * @see FreeCondition
 */
@AttributeKey("$and")
public class And extends FreeCondition<List<FreeCondition<?>>> {
    /**
     * Takes list of conditions as parameter.
     *
     * @param value list of free conditions to evaluate against attributes
     */
    public And(final List<FreeCondition<?>> value) {
        super(value);
    }

    /**
     * evaluates all conditions against given attributes.
     * @param attributes attribute object
     * @return true if all conditions returns true otherwise false
     */
    @Override
    public boolean eval(final Map<String, Object> attributes) {
        return value.stream().allMatch(c -> c.eval(attributes));
    }
}
