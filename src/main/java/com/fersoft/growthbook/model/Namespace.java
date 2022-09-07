package com.fersoft.growthbook.model;

import java.util.Objects;

/**
 * A tuple that specifies what part of a namespace an experiment includes.
 * If two experiments are in the same namespace and their ranges don't overlap,
 * they wil be mutually exclusive.
 */
public class Namespace {
    /**
     * The namespace id.
     */
    private String id;
    /**
     * The beginning of the range (double, between 0 and 1).
     */
    private Double start;
    /**
     * The end of the range (double, between 0 and 1).
     */
    private Double end;

    /**
     * Constructor.
     *
     * @param id
     * @param start
     * @param end
     */
    public Namespace(final String id, final Double start, final Double end) {
        this.id = id;
        this.start = start;
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getStart() {
        return start;
    }

    public void setStart(Double start) {
        this.start = start;
    }

    public Double getEnd() {
        return end;
    }

    public void setEnd(Double end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Namespace namespace = (Namespace) o;

        if (!Objects.equals(id, namespace.id)) return false;
        if (!Objects.equals(start, namespace.start)) return false;
        return Objects.equals(end, namespace.end);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (end != null ? end.hashCode() : 0);
        return result;
    }
}
