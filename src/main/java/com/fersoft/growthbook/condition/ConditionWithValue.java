package com.fersoft.growthbook.condition;

import java.util.Map;

/**
 * Condition value is a pure object (not a condition).
 * ex : "fieldname"="some value"
 *
 * @param <T> type of condition value.
 */
public class ConditionWithValue<T> extends KeyedCondition<T> {
    /**
     * Constructor.
     *
     * @param key   attribute key ,field name
     * @param value condition value should not be condition itself.
     */
    public ConditionWithValue(final String key, final T value) {
        super(key, value);
    }

    /**
     * returns true if condition key=value matches attribute key=value.
     *
     * @param attribute value of the attribute
     * @param <A>       type of attribute value
     * @return evaluation result
     */
    @Override
    protected <A> boolean evaluate(final A attribute) {
        if (attribute == null) {
            return false;
        }
        if (value instanceof Map) {
            if (((Map<?, ?>) value).isEmpty()) {
                return true;
            }
            if (attribute instanceof Map) {
                return ((Map<?, ?>) value).entrySet().stream().allMatch(e ->
                        e.getValue().equals(((Map<?, ?>) attribute).get(e.getKey()))
                );
            }
            return false;
        }
        return attribute.equals(value);
    }
}
