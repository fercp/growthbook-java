package com.fersoft.growthbook.model;

import com.fersoft.growthbook.condition.Condition;

import java.util.List;
import java.util.Objects;

/**
 * Defines a single Experiment.
 *
 * @param <T> type of variations.
 */
public class Experiment<T> {
    /**
     * The globally unique identifier for the experiment.
     */
    private String key;
    /**
     * The different variations to choose between.
     */
    private List<T> variations;
    /**
     * How to weight traffic between variations. Must add to 1.
     */
    private List<Double> weights;
    /**
     * If set to false, always return the control (first variation).
     */
    private Boolean active;
    /**
     * What percent of users should be included in the experiment
     * (between 0 and 1, inclusive).
     */
    private Double coverage;
    /**
     * Optional targeting condition.
     */
    private Condition condition;
    /**
     * Adds the experiment to a namespace.
     */
    private Namespace namespace;
    /**
     * All users included in the experiment will be
     * forced into the specific variation index.
     */
    private Integer force;
    /**
     * What user attribute should be used to assign variations (defaults to id).
     */
    private String hashAttribute = "id";

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<T> getVariations() {
        return variations;
    }

    public void setVariations(List<T> variations) {
        this.variations = variations;
    }

    public List<Double> getWeights() {
        return weights;
    }

    public void setWeights(List<Double> weights) {
        this.weights = weights;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Double getCoverage() {
        return coverage;
    }

    public void setCoverage(Double coverage) {
        this.coverage = coverage;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Namespace getNamespace() {
        return namespace;
    }

    public void setNamespace(Namespace namespace) {
        this.namespace = namespace;
    }

    public Integer getForce() {
        return force;
    }

    public void setForce(Integer force) {
        this.force = force;
    }

    public String getHashAttribute() {
        return hashAttribute;
    }

    public void setHashAttribute(String hashAttribute) {
        this.hashAttribute = hashAttribute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Experiment<?> that = (Experiment<?>) o;

        if (!Objects.equals(key, that.key)) return false;
        if (!Objects.equals(variations, that.variations)) return false;
        if (!Objects.equals(weights, that.weights)) return false;
        if (!Objects.equals(active, that.active)) return false;
        if (!Objects.equals(coverage, that.coverage)) return false;
        if (!Objects.equals(condition, that.condition)) return false;
        if (!Objects.equals(namespace, that.namespace)) return false;
        if (!Objects.equals(force, that.force)) return false;
        return Objects.equals(hashAttribute, that.hashAttribute);
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (variations != null ? variations.hashCode() : 0);
        result = 31 * result + (weights != null ? weights.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        result = 31 * result + (coverage != null ? coverage.hashCode() : 0);
        result = 31 * result + (condition != null ? condition.hashCode() : 0);
        result = 31 * result + (namespace != null ? namespace.hashCode() : 0);
        result = 31 * result + (force != null ? force.hashCode() : 0);
        result = 31 * result + (hashAttribute != null ? hashAttribute.hashCode() : 0);
        return result;
    }
}
