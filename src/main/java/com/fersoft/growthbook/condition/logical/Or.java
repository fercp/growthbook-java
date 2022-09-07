package com.fersoft.growthbook.condition.logical;

import com.fersoft.growthbook.condition.AttributeKey;
import com.fersoft.growthbook.condition.FreeCondition;

import java.util.List;
import java.util.Map;

/**
 * Logical OR operator ,evaluates conditions against given attributes.
 *
 * @see FreeCondition
 */
@AttributeKey("$or")
public class Or extends FreeCondition<List<FreeCondition<?>>> {
    /**
     * Takes list of conditions as parameter.
     *
     * @param value list of free conditions to evaluate against attributes
     */
    public Or(final List<FreeCondition<?>> value) {
        super(value);
    }

    /**
     * evaluates conditions against given attributes.
     *
     * @param attributes attribute object
     * @return true if at least one condition returns true otherwise false
     */
    @Override
    public boolean eval(final Map<String, Object> attributes) {
        return value.isEmpty() || value.stream().anyMatch(c -> c.eval(attributes));
    }
}
