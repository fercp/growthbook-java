package com.fersoft.growtbook.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fersoft.growtbook.json.test.CasesJsonSource;
import com.fersoft.growthbook.ExperimentRunner;
import com.fersoft.growthbook.model.BucketRange;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BucketRangesTest {
    @DisplayName("BucketRanges test")
    @ParameterizedTest(name = "{0}")
    @CasesJsonSource("getBucketRange")
    void runTests(JsonNode testNode) {
        ExperimentRunner experimentRunner = new ExperimentRunner();
        JsonNode parameters = testNode.get(1);
        int param1 = parameters.get(0).asInt();
        double param2 = parameters.get(1).asDouble();
        JsonNode param3 = parameters.get(2);
        List<Double> weights = new ArrayList<>();
        if (param3 != null) {
            param3.elements().forEachRemaining(p ->
                    weights.add(p.asDouble()));
        }

        List<BucketRange> actual = experimentRunner.getBucketRanges(param1, param2, weights);
        List<BucketRange> expected = new ArrayList<>();
        testNode.get(2).elements().forEachRemaining(p -> expected.add(new BucketRange(p.get(0).asDouble(), p.get(1).asDouble())));
        assertEquals(expected, actual);
    }
}
