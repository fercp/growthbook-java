package com.fersoft.growthbook.model;

import java.util.List;

/**
 * A Feature object consists of a default value plus rules that can override the default.
 *
 * @param <T> type of default value.
 */
public class Feature<T> {
    /**
     * The default value (should use null if not specified)
     */
    private T defaultValue;
    /**
     * Array of FeatureRule objects that determine when and
     * how the defaultValue gets overridden.
     */
    private List<FeatureRule<T>> rules;

    public T getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    public List<FeatureRule<T>> getRules() {
        return rules;
    }

    public void setRules(List<FeatureRule<T>> rules) {
        this.rules = rules;
    }
}
