package com.fersoft.growthbook;

import com.fersoft.growthbook.model.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class ExperimentRunner {
    /**
     * FNV hash function.
     */
    private static final FNVHash FNV_HASH = new FNVHash();

    /**
     * The run method takes an Experiment object and returns an ExperimentResult.
     *
     * @param experiment Experiment
     * @param context    Context
     * @param featureId  feature id
     * @param <T>        Type of experiment value
     * @return Experiment Result
     */
    public <T> ExperimentResult<T> run(final Experiment<T> experiment, final Context context, final String featureId) {
        if (experiment.getVariations() == null || experiment.getVariations().size() < 2 || Boolean.FALSE.equals(context.getEnabled())) {
            return getExperimentResult(context, experiment, -1, false, featureId);
        }
        if (context.getUrl() != null) {
            Integer qsOverride = getQueryStringOverride(experiment.getKey(), context.getUrl(), experiment.getVariations().size());
            if (qsOverride != null) {
                return getExperimentResult(context, experiment, qsOverride, false, featureId);
            }
        }
        if (context.getForcedVariations() != null && context.getForcedVariations().containsKey(experiment.getKey())) {
            return getExperimentResult(context, experiment, (Integer) context.getForcedVariations().get(experiment.getKey()), false, null);
        }
        String hashAttribute = Objects.requireNonNullElse(experiment.getHashAttribute(), "id");
        String hashValue = "";
        if (context.getAttributes() != null) {
            hashValue = Objects.requireNonNullElse((String) context.getAttributes().get(hashAttribute), "");
        }

        if ((experiment.getActive() != null && !experiment.getActive()) || "".equals(hashValue) || (experiment.getNamespace() != null && !inNamespace(hashValue, experiment.getNamespace())) || (experiment.getCondition() != null && !experiment.getCondition().eval(context.getAttributes()))) {
            return getExperimentResult(context, experiment, -1, false, featureId);
        }

        List<BucketRange> ranges = getBucketRanges(experiment.getVariations().size(), Objects.requireNonNullElse(experiment.getCoverage(), 1.0), Objects.requireNonNullElse(experiment.getWeights(), Collections.emptyList()));
        double n = FNV_HASH.hash(hashValue + experiment.getKey());
        int assigned = chooseVariation(n, ranges);
        if (assigned == -1) {
            return getExperimentResult(context, experiment, -1, false, featureId);
        }
        if (experiment.getForce() != null) {
            return getExperimentResult(context, experiment, experiment.getForce(), false, featureId);
        }
        if (Boolean.TRUE.equals(context.getQaMode())) {
            return getExperimentResult(context, experiment, -1, false, featureId);
        }
        ExperimentResult<T> result = getExperimentResult(context, experiment, assigned, true, featureId);
        if (context.getTrackingCallback() != null) {
            context.getTrackingCallback().track(experiment, result);
        }
        return result;
    }

    /**
     * Given a hash and bucket ranges, assign one of the bucket ranges
     * If n is within the range, return the range index.
     *
     * @param n      value
     * @param ranges bucket ranges
     * @return int
     */
    public int chooseVariation(final double n, final List<BucketRange> ranges) {
        for (int i = 0; i < ranges.size(); i++) {
            if (n >= ranges.get(i).getStart() && n < ranges.get(i).getEnd()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * This converts and experiment's coverage and variation weights
     * into an array of bucket ranges.
     *
     * @param numVariations number of variations
     * @param coverage      value
     * @param weights       weight array
     * @return bucket ranges
     */
    public List<BucketRange> getBucketRanges(final int numVariations, final Double coverage, final List<Double> weights) {
        List<Double> result = weights;
        double coverageFixed = coverage;
        if (result == null) {
            result = Collections.emptyList();
        }
        if (coverage < 0) {
            coverageFixed = 0.0;
        }
        if (coverage > 1) {
            coverageFixed = 1.0;
        }
        double sum = result.stream().reduce(0.0, Double::sum);
        if (result.size() != numVariations || (sum < 0.99 || sum > 1.01)) {
            result = getEqualWeights(numVariations);
        }

        double cumulative = 0.0;
        List<BucketRange> ranges = new ArrayList<>();

        for (Double w : result) {
            double start = cumulative;
            cumulative += w;
            ranges.add(new BucketRange(start, start + coverageFixed * w));
        }

        return ranges;
    }

    /**
     * Returns an array of floats with numVariations items that are
     * all equal and sum to 1.
     * For example, getEqualWeights(2) would return [0.5, 0.5].
     *
     * @param numVariations number of variations
     * @return list of weights
     */
    public List<Double> getEqualWeights(int numVariations) {
        if (numVariations < 1) {
            return Collections.emptyList();
        }
        List<Double> equalWeights = new ArrayList<>(numVariations);
        IntStream.range(0, numVariations).forEach(i -> equalWeights.add(1.0 / numVariations));
        return equalWeights;
    }

    /**
     * This checks if a userId is within an experiment namespace or not.
     *
     * @param hashValue hash value
     * @param namespace namespace
     * @return true/false
     */
    public boolean inNamespace(final String hashValue, final Namespace namespace) {
        double n = FNV_HASH.hash(hashValue + "__" + namespace.getId());
        return n >= namespace.getStart() && n < namespace.getEnd();
    }

    /**
     * This checks if an experiment variation is being forced
     * via a URL query string.
     * This may not be applicable for all SDKs (e.g. mobile).
     *
     * @param id
     * @param url
     * @param numVariations
     * @return
     */
    public Integer getQueryStringOverride(final String id,
                                          final String url,
                                          final int numVariations) {
        try {
            URL aUrl = new URL(url);
            String[] paramValues = aUrl.getQuery().split("&");
            for (int i = 0; i < paramValues.length; i++) {
                String[] paramValue = paramValues[i].split("=");
                if (paramValue.length == 2 && id.equals(paramValue[0])) {
                    int value = Integer.parseInt(paramValue[1]);
                    if (value >= 0 && value < numVariations) {
                        return value;
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }


    private <T> ExperimentResult<T> getExperimentResult(final Context context,
                                                        final Experiment<T> experiment,
                                                        final Integer variationIndex,
                                                        final boolean hashUsed,
                                                        final String featureID) {
        // By default, assume everyone is in the experiment
        boolean inExperiment = true;
        int variationIndexFixed = variationIndex;
        // If the variation is invalid, use the baseline and set the inExperiment flag to false.
        if (variationIndex < 0 || variationIndex >= experiment.getVariations().size()) {
            variationIndexFixed = 0;
            inExperiment = false;
        }
        String hashValue = "";
        String hashAttribute = Objects.requireNonNullElse(experiment.getHashAttribute(), "id");
        if (context.getAttributes() != null) {
            hashValue = Objects.requireNonNullElse((String) context.getAttributes().get(hashAttribute), "");
        }
        return new ExperimentResult(inExperiment, variationIndexFixed,
                experiment.getVariations() != null ? experiment.getVariations().get(variationIndexFixed)
                        : null,
                hashUsed, hashAttribute, hashValue, featureID);
    }

}
