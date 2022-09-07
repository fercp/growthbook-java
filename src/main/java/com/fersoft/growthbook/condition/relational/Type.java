package com.fersoft.growthbook.condition.relational;

import com.fersoft.growthbook.condition.AttributeKey;
import com.fersoft.growthbook.condition.DependedCondition;

import java.util.List;

/**
 * This returns the data type of the passed in argument.
 * <p>
 * The valid types to return are:
 * <ul>
 * <li>string</li>
 * <li>number</li>
 * <li>boolean</li>
 * <li>array</li>
 * <li>object</li>
 * <li>null</li>
 * </ul>
 *
 * @param <T>
 */
@AttributeKey("$type")
public class Type<T> extends DependedCondition<String, T> {
    /**
     * Constructor.
     *
     * @param value type as string
     */
    public Type(final String value) {
        super(value);
    }

    /**
     * checks if type of the attribute as string is equal to condition value.
     *
     * @param attribute attribute value
     * @return comparison result.
     */
    @Override
    public boolean eval(final T attribute) {
        String type;
        if (attribute instanceof String) {
            type = "string";
        } else if (attribute instanceof Number) {
            type = "number";
        } else if (attribute instanceof Boolean) {
            type = "boolean";
        } else if (attribute instanceof Object[] || attribute instanceof List) {
            type = "array";
        } else if (attribute == null) {
            type = "null";
        } else {
            type = "object"; //default type for java
        }
        return type.equals(this.value);
    }
}
