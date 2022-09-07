package com.fersoft.growthbook.condition;

import java.util.Map;

/**
 * Conditions with an attribute key.
 * ex "fieldname":some condtion/value
 *
 * @param <V>
 */
public abstract class KeyedCondition<V> extends FreeCondition<V> {
    /**
     * attribute field name
     */
    private final String key;

    /**
     * Constructor
     *
     * @param key   attribute key/field name
     * @param value condition value
     */

    protected KeyedCondition(final String key, final V value) {
        super(value);
        this.key = key;
    }

    /**
     * key value getter.
     *
     * @return key value.
     */
    public String getKey() {
        return key;
    }

    /**
     * Evaluates condition against attributes object.
     *
     * @param attributes attributes object
     * @return evaluation result.
     */
    @Override
    public boolean eval(final Map<String, Object> attributes) {
        return evaluate(getValue(key, attributes));
    }

    private <A> A getValue(final String path, final Map<String, Object> attributes) {
        try {
            if ("$".equals(path)) {
                return (A) attributes;
            }
            String[] keys = path.split("\\.");
            Map<String, Object> inmostMap = attributes;
            for (int i = 0; i < keys.length - 1; i++) {
                inmostMap = (Map<String, Object>) inmostMap.get(keys[i]);
                if (inmostMap == null) {
                    return null;
                }
            }
            return (A) inmostMap.get(keys[keys.length - 1]);
        } catch (ClassCastException e) {
            return null;
        }
    }

    protected abstract <A> boolean evaluate(A attribute);
}
