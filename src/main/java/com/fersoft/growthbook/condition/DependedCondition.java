package com.fersoft.growthbook.condition;

/**
 * Conditions require an attribute value for evaluation.
 * attribute value should be extracted by a keyedconditions beforehand.
 * Marker class for depended conditions.
 *
 * @param <V> condition value.
 * @param <A> attribute value.
 */
public abstract class DependedCondition<V, A> extends Condition<V, A> {
    /**
     * Constructor.
     *
     * @param value
     */
    public DependedCondition(V value) {
        super(value);
    }
}
