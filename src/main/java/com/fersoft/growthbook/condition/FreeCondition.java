package com.fersoft.growthbook.condition;

import java.util.Map;

/**
 * FreeConditions can be executed directly against whole attribute object.
 * logical operators (and,or,etc) and conditions with an attribute key can be
 * executed directly against whole attribute object because they don't need a
 * specific attribute value to execute.
 *
 * @param <T> Type of value of the condition,value of condition is evaluated
 *            with attribute object.
 */
public abstract class FreeCondition<T>
        extends Condition<T, Map<String, Object>> {
    /**
     * Constructor.
     *
     * @param value condition value. can be list of conditions, a fixed value or
     *              a single condition.
     */
    public FreeCondition(final T value) {
        super(value);
    }
}
