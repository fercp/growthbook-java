package com.fersoft.growtbook.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fersoft.growtbook.json.test.CasesJsonSource;
import com.fersoft.growthbook.FNVHash;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HashTest {
    @DisplayName("Hash test")
    @ParameterizedTest(name = "{0}")
    @CasesJsonSource("hash")
    void runTests(JsonNode testNode) {
        FNVHash fnvHash = new FNVHash();
        double actual = fnvHash.hash(testNode.get(0).asText());
        double expected = testNode.get(1).asDouble();
        assertEquals(expected, actual);
    }
}
