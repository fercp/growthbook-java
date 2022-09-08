package com.fersoft.growthbook.condition.logical;

import com.fersoft.growthbook.condition.AttributeKey;
import com.fersoft.growthbook.condition.FreeCondition;

import java.util.List;
import java.util.Map;

/**
 * Logical "Not Or"  operator ,evaluates conditions against given attributes.
 *
 * @see FreeCondition
 */
@AttributeKey("$nor")
public class Nor extends FreeCondition<List<FreeCondition<?>>> {
    /**
     * Takes list of conditions as parameter.
     *
     * @param value list of free conditions to evaluate against attributes
     */
    public Nor(final List<FreeCondition<?>> value) {
        super(value);
    }

    /**
     * evaluates conditions against given attributes.
     *
     * @param attributes attribute object
     * @return true if all the conditions returns false otherwise false
     */
    @Override
    public boolean eval(final Map<String, Object> attributes) {
        return value.stream().noneMatch(c -> c.eval(attributes));
    }
}
