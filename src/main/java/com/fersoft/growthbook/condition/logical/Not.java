package com.fersoft.growthbook.condition.logical;

import com.fersoft.growthbook.condition.AttributeKey;
import com.fersoft.growthbook.condition.DependedCondition;
import com.fersoft.growthbook.condition.FreeCondition;

import java.util.List;

/**
 * Logical "Not AND"  operator ,evaluates conditions against given attributes.
 *
 * @see FreeCondition
 */
@AttributeKey("$not")
public class Not<P> extends DependedCondition<List<DependedCondition<?, P>>, P> {
    /**
     * Takes list of conditions as parameter.
     *
     * @param value list of free conditions to evaluate against attributes
     */
    public Not(final List<DependedCondition<?, P>> value) {
        super(value);
    }

    /**
     * evaluates conditions against given attributes.
     *
     * @param param attribute object
     * @return true if at one or more conditions returns false otherwise false
     */
    @Override
    public boolean eval(P param) {
        return !value.stream().allMatch(c -> c.eval(param));
    }


}
