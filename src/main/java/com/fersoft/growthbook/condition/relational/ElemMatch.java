package com.fersoft.growthbook.condition.relational;

import com.fersoft.growthbook.condition.AttributeKey;
import com.fersoft.growthbook.condition.Condition;
import com.fersoft.growthbook.condition.DependedCondition;

import java.util.List;

/**
 * Checks if at least one of the attribute list items must match the condition.
 *
 * @param <X> type of condition value.
 * @param <A> type of attribute which is a List of X.
 */
@AttributeKey("$elemMatch")
public class ElemMatch<X, A extends List<X>>
        extends DependedCondition<List<DependedCondition<?, X>>, A> {
    /**
     * Constructor.
     *
     * @param value Condition to be executed against elements in the list
     */
    public ElemMatch(final List<DependedCondition<?, X>> value) {
        super(value);
    }

    /**
     * evaluates condition against each item in the attribute list.
     *
     * @param attribute list of attribute values
     * @return true if at least one item evaluated by condition returns true
     */
    @Override
    public boolean eval(final A attribute) {
        if (attribute == null) {
            return false;
        }

        return attribute.stream().anyMatch(a -> value.stream().allMatch(v -> v.eval(a)));
    }
}
