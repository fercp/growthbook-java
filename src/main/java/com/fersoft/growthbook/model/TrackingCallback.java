package com.fersoft.growthbook.model;

/**
 * A callback function that is executed every time a user is included in an Experiment.
 *
 * @param <T> Experiment value type.
 */
@FunctionalInterface
public interface TrackingCallback<T> {
    /**
     * will be executed every time a use is included in an experiment.
     * @param experiment experiment
     * @param result experiment result
     */
    void track(Experiment<T> experiment, ExperimentResult<T> result);
}
