package com.fersoft.growthbook.condition.relational;

import com.fersoft.growthbook.condition.AttributeKey;
import com.fersoft.growthbook.condition.DependedCondition;

@AttributeKey("$gte")
public class Gte<T extends Comparable<T>> extends DependedCondition<T, T> {
    public Gte(T value) {
        super(value);
    }

    /**
     * returns true if attribute is greater than condition.
     *
     * @param attribute attribute value
     * @return comparison result
     */
    @Override
    public boolean eval(T attribute) {
        return attribute.compareTo(this.value) >= 0;
    }
}
