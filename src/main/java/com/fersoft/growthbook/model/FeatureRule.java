package com.fersoft.growthbook.model;

import com.fersoft.growthbook.condition.Condition;

import java.util.List;

/**
 * Overrides the defaultValue of a Feature.
 *
 * @param <T>
 */
public class FeatureRule<T> {
    /**
     * Optional targeting condition.
     */
    private Condition condition;
    /**
     * What percent of users should be included in the experiment
     * (between 0 and 1, inclusive).
     */
    private Double coverage;
    /**
     * Immediately force a specific value
     * (ignore every other option besides condition and coverage).
     */
    private T force;
    /**
     * Run an experiment (A/B test) and randomly choose
     * between these variations.
     */
    private List<T> variations;
    /**
     *  The globally unique tracking key for the experiment
     *  (default to the feature key).
     */
    private String key;
    /**
     * How to weight traffic between variations. Must add to 1.
     */
    private List<Double> weights;
    /**
     *  Adds the experiment to a namespace.
     */
    private Namespace namespace;
    /**
     * What user attribute should be used to assign variations (defaults to id).
     */
    private String hashAttribute = "id";

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Double getCoverage() {
        return coverage;
    }

    public void setCoverage(Double coverage) {
        this.coverage = coverage;
    }

    public T getForce() {
        return force;
    }

    public void setForce(T force) {
        this.force = force;
    }

    public List<T> getVariations() {
        return variations;
    }

    public void setVariations(List<T> variations) {
        this.variations = variations;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Double> getWeights() {
        return weights;
    }

    public void setWeights(List<Double> weights) {
        this.weights = weights;
    }

    public Namespace getNamespace() {
        return namespace;
    }

    public void setNamespace(Namespace namespace) {
        this.namespace = namespace;
    }

    public String getHashAttribute() {
        return hashAttribute;
    }

    public void setHashAttribute(String hashAttribute) {
        this.hashAttribute = hashAttribute;
    }
}
