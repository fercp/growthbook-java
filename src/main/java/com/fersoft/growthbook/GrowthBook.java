package com.fersoft.growthbook;

import com.fersoft.growthbook.model.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import static com.fersoft.growthbook.model.FeatureSource.*;

/**
 * The GrowthBook class is the main export of the SDK.
 *
 * @param <T> type of experiment value
 */
public class GrowthBook<T> {
    /**
     * FNV hash function.
     */
    private static final FNVHash FNV_HASH = new FNVHash();
    /**
     * Context object.
     */
    private final Context<T> context;
    /**
     * Experiment runner.
     */
    private final ExperimentRunner experimentRunner = new ExperimentRunner();
    /**
     * subscription callback.
     */
    private BiConsumer<Experiment<T>, ExperimentResult<T>> subscribeCallback;

    /**
     * The constructor takes a Context object and stores
     * the properties for later.
     * Nothing else needs to be done during initialization.
     *
     * @param context
     */
    public GrowthBook(final Context<T> context) {
        this.context = context;
    }

    /**
     * The evalFeature method takes a single string argument,
     * which is the unique identifier for the feature and returns
     * a FeatureResult object.
     *
     * @param key feature key
     * @return feature reuslt
     */
    public FeatureResult<T> evalFeature(final String key) {
        Map<String, Feature<T>> features = context.getFeatures();
        if (!features.containsKey(key)) {
            return getFeatureResult(null, unknownFeature, null, null);
        }
        Feature<T> feature = features.get(key);
        List<FeatureRule<T>> rules = feature.getRules();
        if (rules != null && !rules.isEmpty()) {
            for (FeatureRule<T> rule : rules) {
                if (rule.getCondition() != null) {
                    if (!rule.getCondition().eval(context.getAttributes())) {
                        continue;
                    }
                }
                if (rule.getForce() != null) {
                    if (rule.getCoverage() != null) {
                        Object hashAttribute = context.getAttributes().get(Objects.requireNonNullElse(rule.getHashAttribute(), "id"));
                        if (hashAttribute == null) {
                            continue;
                        }
                        double value = FNV_HASH.hash(hashAttribute + Objects.requireNonNullElse(key, ""));
                        if (value > rule.getCoverage()) {
                            continue;
                        }
                    }
                    return getFeatureResult(rule.getForce(), force, null, null);
                } else {
                    Experiment<T> experiment = new Experiment<>();
                    experiment.setVariations(rule.getVariations());
                    experiment.setKey(rule.getKey() != null ? rule.getKey() : key);
                    experiment.setCoverage(rule.getCoverage());
                    experiment.setWeights(rule.getWeights());
                    experiment.setHashAttribute(rule.getHashAttribute());
                    experiment.setNamespace(rule.getNamespace());

                    ExperimentResult<T> experimentResult = run(experiment, key);
                    experimentResult.setFeatureId(key);
                    if (experimentResult.getInExperiment()) {
                        return getFeatureResult(experimentResult.getValue(), FeatureSource.experiment, experiment, experimentResult);
                    }
                }
            }
        }


        return getFeatureResult(feature.getDefaultValue(), defaultValue, null, null);
    }

    /**
     * The run method takes an Experiment object and returns an ExperimentResult.
     *
     * @param experiment
     * @param featureId
     * @return
     */
    public ExperimentResult<T> run(final Experiment<T> experiment,
                                   final String featureId) {
        ExperimentResult<T> result = null;
        try {
            result = experimentRunner.run(experiment, context, featureId);
        } finally {
            if (this.subscribeCallback != null) {
                this.subscribeCallback.accept(experiment, result);
            }
        }
        return result;
    }

    /**
     * The run method takes an Experiment object and returns an ExperimentResult.
     *
     * @param experiment
     * @return
     */
    public ExperimentResult<T> run(final Experiment<T> experiment) {
        return run(experiment, null);
    }

    private FeatureResult<T> getFeatureResult(final T value, final FeatureSource source,
                                              final Experiment<T> experiment,
                                              final ExperimentResult<T> experimentResult) {
        FeatureResult<T> featureResult = new FeatureResult<>();
        featureResult.setExperimentResult(experimentResult);
        featureResult.setExperiment(experiment);
        featureResult.setSource(source);
        featureResult.setValue(value);
        boolean onOff = value != null && !Boolean.FALSE.equals(value) && !Integer.valueOf(0).equals(value);
        featureResult.setOn(onOff);
        featureResult.setOff(!onOff);
        return featureResult;
    }

    /**
     * feature is on
     *
     * @param key
     * @return
     */
    public boolean isOn(final String key) {
        return evalFeature(key).getOn();
    }

    /**
     * feature is off
     *
     * @param key
     * @return
     */
    public boolean isOff(final String key) {
        return this.evalFeature(key).getOff();
    }

    /**
     * @param key
     * @param fallback
     * @return
     */
    public T getFeatureValue(final String key, final Supplier<T> fallback) {
        T value = this.evalFeature(key).getValue();
        return value == null ? fallback.get() : value;
    }

    /**
     * Sometimes it's useful to be able to "subscribe" to a GrowthBook
     * instance and be alerted every time growthbook.run is called.
     * This is different from the tracking callback
     * since it also fires when a user is not included in an experiment.
     *
     * @param callback
     * @return unsubscriber
     */
    public Unsubscriber subscribe(
            final BiConsumer<Experiment<T>, ExperimentResult<T>> callback) {
        this.subscribeCallback = callback;
        return () -> this.subscribeCallback = null;
    }
}
