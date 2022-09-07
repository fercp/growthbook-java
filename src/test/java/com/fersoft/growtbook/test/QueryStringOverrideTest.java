package com.fersoft.growtbook.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fersoft.growtbook.json.test.CasesJsonSource;
import com.fersoft.growthbook.ExperimentRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryStringOverrideTest {
    @DisplayName("Query String Override test")
    @ParameterizedTest(name = "{0}")
    @CasesJsonSource("getQueryStringOverride")
    void runTests(JsonNode testNode) {
        ExperimentRunner experimentRunner = new ExperimentRunner();
        String key = testNode.get(1).asText();
        String url = testNode.get(2).asText();
        Integer numVariations = testNode.get(3).asInt();
        Integer expected = testNode.get(4).asInt(-1);
        if (expected == -1) {
            expected = null;
        }
        Integer actual = experimentRunner.getQueryStringOverride(key, url, numVariations);
        assertEquals(expected, actual);
    }
}
