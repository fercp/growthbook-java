package com.fersoft.growthbook.condition.relational;

import com.fersoft.growthbook.condition.AttributeKey;
import com.fersoft.growthbook.condition.DependedCondition;

/**
 * checks if given attribute is null and then compares the result
 * against condition value(true/false).
 *
 * @param <T> Type of attribute
 */
@AttributeKey("$exists")
public class Exists<T> extends DependedCondition<Boolean, T> {
    /**
     * Constructor.
     *
     * @param value if true attribute should be non-nul
     *              for null attributes false.
     */
    public Exists(final Boolean value) {
        super(value);
    }

    /**
     * check if attribute is null.
     *
     * @param attribute attribute value
     * @return true/false depending:
     * <ol>
     * <li>If condition is false, return true if attributeValue is null</li>
     * <li>Else, return true if attributeValue is NOT null</li>
     * <li>Return false by default</li>
     * </ol>
     */
    @Override
    public boolean eval(final T attribute) {
        return attribute != null == value;
    }
}
