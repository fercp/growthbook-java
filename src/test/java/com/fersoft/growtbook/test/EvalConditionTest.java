package com.fersoft.growtbook.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fersoft.growtbook.json.test.CasesJsonSource;
import com.fersoft.growthbook.condition.Condition;
import com.fersoft.growthbook.parser.ConditionParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EvalConditionTest {
    @DisplayName("Eval Condition test")
    @ParameterizedTest(name = "{0}")
    @CasesJsonSource("evalCondition")
    void runTests(JsonNode testNode, ObjectMapper objectMapper) {
        ConditionParser conditionParser = new ConditionParser();
        Map<String, Object> condition = objectMapper.convertValue(testNode.get(1), new TypeReference<>() {
        });
        Map<String, Object> attributes = objectMapper.convertValue(testNode.get(2), new TypeReference<>() {
        });
        Condition<?, Map<String, Object>> topCondition = conditionParser.parse(condition);
        boolean actual = topCondition.eval(attributes);
        boolean expected = testNode.get(3).asBoolean();
        assertEquals(expected, actual);
    }
}
