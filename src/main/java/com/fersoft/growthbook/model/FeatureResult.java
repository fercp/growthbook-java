package com.fersoft.growthbook.model;

import java.util.Objects;

/**
 * The result of evaluating a Feature.
 * @param <T> type of assigned value.
 */
public class FeatureResult<T> {
    /**
     * The assigned value of the feature.
     */
    private T value;
    /**
     * The assigned value cast to a boolean.
     */
    private Boolean on;
    /**
     * The assigned value cast to a boolean and then negated.
     */
    private Boolean off;
    /**
     * One of "unknownFeature", "defaultValue", "force", or "experiment".
     */
    private FeatureSource source;
    /**
     * When source is "experiment", this will be an Experiment object.
     */
    private Experiment<T> experiment;
    /**
     * When source is "experiment", this will be an ExperimentResult object.
     */
    private ExperimentResult<T> experimentResult;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Boolean getOn() {
        return on;
    }

    public void setOn(Boolean on) {
        this.on = on;
    }

    public Boolean getOff() {
        return off;
    }

    public void setOff(Boolean off) {
        this.off = off;
    }

    public FeatureSource getSource() {
        return source;
    }

    public void setSource(FeatureSource source) {
        this.source = source;
    }

    public Experiment<T> getExperiment() {
        return experiment;
    }

    public void setExperiment(Experiment<T> experiment) {
        this.experiment = experiment;
    }

    public ExperimentResult<T> getExperimentResult() {
        return experimentResult;
    }

    public void setExperimentResult(ExperimentResult<T> experimentResult) {
        this.experimentResult = experimentResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeatureResult<?> that = (FeatureResult<?>) o;

        if (!Objects.equals(value, that.value)) return false;
        if (!Objects.equals(on, that.on)) return false;
        if (!Objects.equals(off, that.off)) return false;
        if (source != that.source) return false;
        if (!Objects.equals(experiment, that.experiment)) return false;
        return Objects.equals(experimentResult, that.experimentResult);
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (on != null ? on.hashCode() : 0);
        result = 31 * result + (off != null ? off.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (experiment != null ? experiment.hashCode() : 0);
        result = 31 * result + (experimentResult != null ? experimentResult.hashCode() : 0);
        return result;
    }
}
