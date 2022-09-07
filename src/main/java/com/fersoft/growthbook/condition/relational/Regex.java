package com.fersoft.growthbook.condition.relational;

import com.fersoft.growthbook.condition.AttributeKey;
import com.fersoft.growthbook.condition.DependedCondition;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Matches condition value which is a regular expression against attribute.
 */
@AttributeKey("$regex")
public class Regex extends DependedCondition<String, String> {
    /**
     * compiled pattern of regex.
     */
    private Pattern pattern;

    /**
     * Constructor
     *
     * @param value regular expression
     */
    public Regex(final String value) {
        super(value);
        try {
            pattern = Pattern.compile(value);
        } catch (PatternSyntaxException ex) {
            pattern = null;
        }
    }

    /**
     * Matches condition regex to attribute.
     * @param attribute attribute value.
     * @return true if match found else false.
     */
    @Override
    public boolean eval(final String attribute) {
        if (pattern == null) {
            return false;
        }
        return pattern.matcher(attribute).find();
    }
}
