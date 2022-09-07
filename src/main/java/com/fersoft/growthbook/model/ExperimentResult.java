package com.fersoft.growthbook.model;

import java.util.Objects;

/**
 * the result of running an Experiment given a specific Context.
 *
 * @param <T> type of the array value of the assigned variation.
 */
public class ExperimentResult<T> {
    /**
     * Whether the user is part of the experiment.
     */
    private Boolean inExperiment;
    /**
     * The array index of the assigned variation.
     */
    private Integer variationId;
    /**
     * The array value of the assigned variation.
     */
    private T value;
    /**
     * If a hash was used to assign a variation.
     */
    private Boolean hashUsed;
    /**
     * The user attribute used to assign a variation.
     */
    private String hashAttribute;
    /**
     * The value of that attribute.
     */
    private String hashValue;
    /**
     * The id of the feature (if any) that the experiment came from.
     */
    private String featureId;

    public ExperimentResult() {
    }

    public ExperimentResult(Boolean inExperiment, Integer variationId, T value, Boolean hashUsed, String hashAttribute, String hashValue, String featureId) {
        this.inExperiment = inExperiment;
        this.variationId = variationId;
        this.value = value;
        this.hashUsed = hashUsed;
        this.hashAttribute = hashAttribute;
        this.hashValue = hashValue;
        this.featureId = featureId;
    }

    public Boolean getInExperiment() {
        return inExperiment;
    }

    public void setInExperiment(Boolean inExperiment) {
        this.inExperiment = inExperiment;
    }

    public Integer getVariationId() {
        return variationId;
    }

    public void setVariationId(Integer variationId) {
        this.variationId = variationId;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Boolean getHashUsed() {
        return hashUsed;
    }

    public void setHashUsed(Boolean hashUsed) {
        this.hashUsed = hashUsed;
    }

    public String getHashAttribute() {
        return hashAttribute;
    }

    public void setHashAttribute(String hashAttribute) {
        this.hashAttribute = hashAttribute;
    }

    public String getHashValue() {
        return hashValue;
    }

    public void setHashValue(String hashValue) {
        this.hashValue = hashValue;
    }

    public String getFeatureId() {
        return featureId;
    }

    public void setFeatureId(String featureId) {
        this.featureId = featureId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExperimentResult<?> that = (ExperimentResult<?>) o;

        if (!Objects.equals(inExperiment, that.inExperiment)) return false;
        if (!Objects.equals(variationId, that.variationId)) return false;
        if (!Objects.equals(value, that.value)) return false;
        if (!Objects.equals(hashUsed, that.hashUsed)) return false;
        if (!Objects.equals(hashAttribute, that.hashAttribute))
            return false;
        if (!Objects.equals(hashValue, that.hashValue)) return false;
        return Objects.equals(featureId, that.featureId);
    }

    @Override
    public int hashCode() {
        int result = inExperiment != null ? inExperiment.hashCode() : 0;
        result = 31 * result + (variationId != null ? variationId.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (hashUsed != null ? hashUsed.hashCode() : 0);
        result = 31 * result + (hashAttribute != null ? hashAttribute.hashCode() : 0);
        result = 31 * result + (hashValue != null ? hashValue.hashCode() : 0);
        result = 31 * result + (featureId != null ? featureId.hashCode() : 0);
        return result;
    }
}
