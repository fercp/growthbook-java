package com.fersoft.growtbook.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fersoft.growtbook.json.test.CasesJsonSource;
import com.fersoft.growthbook.ExperimentRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EqualWeightsTest {
    @DisplayName("Equal Weights test")
    @ParameterizedTest(name="{0}")
    @CasesJsonSource("getEqualWeights")
    void runTests(JsonNode testNode,ObjectMapper objectMapper) {
        ExperimentRunner experimentRunner = new ExperimentRunner();
        int n = testNode.get(0).asInt();
        List<Double> expected = objectMapper.convertValue(testNode.get(1), new TypeReference<>() {
        });
        List<Double> actual = experimentRunner.getEqualWeights(n);
        assertEquals(expected.stream().map(e -> Math.floor(e * 1e8) / 1e8).collect(Collectors.toList()),
                actual.stream().map(a -> Math.floor(a * 1e8) / 1e8).collect(Collectors.toList()));
    }
}
