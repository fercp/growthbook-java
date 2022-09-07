package com.fersoft.growtbook.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fersoft.growtbook.json.test.CasesJsonSource;
import com.fersoft.growthbook.ExperimentRunner;
import com.fersoft.growthbook.model.Namespace;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InNamespaceTest {
    @DisplayName("In namespace test")
    @ParameterizedTest(name = "{0}")
    @CasesJsonSource("inNamespace")
    void runTests(JsonNode testNode, ObjectMapper objectMapper) {
        ExperimentRunner experimentRunner = new ExperimentRunner();
        String id = testNode.get(1).asText();
        Namespace namespace = objectMapper.convertValue(testNode.get(2), Namespace.class);
        boolean expected = testNode.get(3).asBoolean();
        boolean actual = experimentRunner.inNamespace(id, namespace);
        assertEquals(expected, actual);
    }
}
