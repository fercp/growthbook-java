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

class ChooseVariationTest {
    @DisplayName("Choose Variation test")
    @ParameterizedTest(name="{0}")
    @CasesJsonSource("chooseVariation")
    void runTests(JsonNode testNode) {
       Double n = testNode.get(1).asDouble();
        List<BucketRange> bucketRanges = new ArrayList<>();
        testNode.get(2).iterator().forEachRemaining(b ->
                bucketRanges.add(new BucketRange(b.get(0).asDouble(), b.get(1).asDouble()))
        );
        int expected = testNode.get(3).asInt();
        ExperimentRunner experimentRunner = new ExperimentRunner();
        int actual = experimentRunner.chooseVariation(n, bucketRanges);
        assertEquals(expected, actual);
    }
}

