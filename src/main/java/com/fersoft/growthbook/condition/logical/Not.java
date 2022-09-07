package com.fersoft.growthbook.condition.logical;

import com.fersoft.growthbook.condition.AttributeKey;
import com.fersoft.growthbook.condition.FreeCondition;

import java.util.List;
import java.util.Map;

/**
 * Logical "Not AND"  operator ,evaluates conditions against given attributes.
 *
 * @see FreeCondition
 */
@AttributeKey("$not")
public class Not extends FreeCondition<List<FreeCondition<?>>> {
    /**
     * Takes list of conditions as parameter.
     *
     * @param value list of free conditions to evaluate against attributes
     */
    public Not(final List<FreeCondition<?>> value) {
        super(value);
    }

    /**
     * evaluates conditions against given attributes.
     *
     * @param attributes attribute object
     * @return true if at one or more conditions returns false otherwise false
     */
    @Override
    public boolean eval(final Map<String, Object> attributes) {
        return !value.stream().allMatch(c -> c.eval(attributes));
    }
}
