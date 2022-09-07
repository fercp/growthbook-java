package com.fersoft.growthbook.condition;

import java.util.List;

/**
 * Conditions with attribute keys(keys does not start with $).
 */
public class ConditionWithKey extends KeyedCondition<List<DependedCondition>> {
    /** Constructor.
     * @param key attribute key , field name in the attribute object.
     * @param value list of depended conditions
     */
    public ConditionWithKey(final String key, final List<DependedCondition> value) {
        super(key, value);
    }


    /**
     * Evaluates all conditions against attribute value of attribute key.
     * @param attribute attribute value of the key
     * @return true if conditions evaluates to true else false.
     * @param <A> type of attribute value.
     */
    @Override
    protected <A> boolean evaluate(A attribute) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        return value.stream().allMatch(v -> {
            try {
                return v.eval(attribute);
            } catch (ClassCastException e) {
                return false;
            }
        });
    }

}
