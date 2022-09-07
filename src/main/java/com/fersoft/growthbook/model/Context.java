package com.fersoft.growthbook.model;

import java.util.Map;

/**
 * Context object passed into the GrowthBook constructor.
 *
 * @param <T> tyep of tracking callback function
 */
public class Context<T> {
    /**
     * Switch to globally disable all experiments. Default true.
     */
    private Boolean enabled = true;
    /**
     * Map of user attributes that are used to assign variations
     */
    private Map<String, Object> attributes;
    /**
     * The URL of the current page.
     */
    private String url;
    /**
     * Feature definitions (usually pulled from an API or cache).
     */
    private Map<String, Feature<T>> features;
    /**
     * Force specific experiments to always
     * assign a specific variation (used for QA).
     */
    private Map<String, Object> forcedVariations;
    /**
     * If true, random assignment is disabled and only explicitly
     * forced variations are used.
     */
    private Boolean qaMode;
    /**
     * A function that takes experiment and result as arguments.
     */
    private TrackingCallback<T> trackingCallback;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Feature<T>> getFeatures() {
        return features;
    }

    public void setFeatures(Map<String, Feature<T>> features) {
        this.features = features;
    }

    public Map<String, Object> getForcedVariations() {
        return forcedVariations;
    }

    public void setForcedVariations(Map<String, Object> forcedVariations) {
        this.forcedVariations = forcedVariations;
    }

    public Boolean getQaMode() {
        return qaMode;
    }

    public void setQaMode(Boolean qaMode) {
        this.qaMode = qaMode;
    }

    public TrackingCallback<T> getTrackingCallback() {
        return trackingCallback;
    }

    public void setTrackingCallback(TrackingCallback<T> trackingCallback) {
        this.trackingCallback = trackingCallback;
    }
}
