package com.fersoft.growthbook.model;

/**
 * <pre>
 * A tuple that describes a range of the number between 0 and 1.
 * The tuple has 2 parts, both floats - the start of the range and the end.
 * For example:
 *
 * [0.3, 0.7]
 *
 * </pre>
 */
public class BucketRange {
    /**
     * for hash code generation.
     */
    public static final int SOME_PRIME = 31;
    /**
     * Max precision 8 digits.
     */
    private static final double MAX_PRECISION = 1e8;
    /**
     * start of the range.
     */
    private final Double start;
    /**
     * end of the range.
     */
    private final Double end;


    /**
     * Constructor for the BucketRange object.
     *
     * @param start start of the range
     * @param end   end of the range
     */
    public BucketRange(final Double start, final Double end) {
        this.start = start;
        this.end = end;
    }

    /**
     * @return start of the range
     */
    public Double getStart() {
        return start;
    }

    /**
     * @return end of the range
     */
    public Double getEnd() {
        return end;
    }

    /**
     * equal check,rounds to 8 digits then compares start and end values.
     *
     * @param o comparison target
     * @return comparison result.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BucketRange range = (BucketRange) o;

        if (Double.compare(round(start), round(range.start)) != 0) {
            return false;
        }
        return Double.compare(round(end), round(range.end)) == 0;
    }

    private Double round(final Double n) {
        return Math.floor(n * MAX_PRECISION) / MAX_PRECISION;
    }

    /**
     * hashcode based on start and end values.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        int result = start != null ? start.hashCode() : 0;
        result = SOME_PRIME * result + (end != null ? end.hashCode() : 0);
        return result;
    }
}
