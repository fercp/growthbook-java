package com.fersoft.growthbook.condition;

/**
 * Base condition class.
 *
 * @param <V> type of condition value
 * @param <P> type of parameter which will be executed against condition.
 */
public abstract class Condition<V, P> {
    /**
     * Condition value.
     * can be a condition or any type.
     */
    protected final V value;

    /**
     * Constructor.
     *
     * @param value condition value.
     */
    public Condition(final V value) {
        this.value = value;
    }

    /**
     * parameter can be any object which will be evaluated with condition value.
     *
     * @param param
     * @return evaluation result.
     */
    public abstract boolean eval(P param);
}
