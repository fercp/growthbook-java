package com.fersoft.growtbook.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fersoft.growtbook.json.test.CasesJsonSource;
import com.fersoft.growthbook.GrowthBook;
import com.fersoft.growthbook.condition.Condition;
import com.fersoft.growthbook.model.Context;
import com.fersoft.growthbook.model.Experiment;
import com.fersoft.growthbook.model.ExperimentResult;
import com.fersoft.growthbook.model.Namespace;
import com.fersoft.growthbook.parser.ConditionParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExperimentRunTest {
    @DisplayName("Experiment Run  test")
    @ParameterizedTest(name = "{0}")
    @CasesJsonSource("run")
    void runTests(JsonNode testNode, ObjectMapper objectMapper) {
        Context context = objectMapper.convertValue(testNode.get(1), Context.class);
        Experiment experiment = objectMapper.convertValue(testNode.get(2), Experiment.class);
        if (testNode.get(2).get("condition") != null) {
            ConditionParser conditionParser = new ConditionParser();
            Condition condition = conditionParser.parse(objectMapper.convertValue(testNode.get(2).get("condition"), new TypeReference<Map<String, Object>>() {
            }));
            experiment.setCondition(condition);
        }
        if (testNode.get(2).get("namespace") != null) {
            JsonNode node = testNode.get(2).get("namespace");
            Namespace namespace = new Namespace(node.get(0).asText(), node.get(1).asDouble(), node.get(2).asDouble());
            experiment.setNamespace(namespace);
        }
        Object value = objectMapper.convertValue(testNode.get(3), Object.class);
        Boolean inExperiment = testNode.get(4).asBoolean();
        Boolean hashUsed = testNode.get(5).asBoolean();
        GrowthBook growthBook = new GrowthBook(context);
        ExperimentResult actual = growthBook.run(experiment);
        assertEquals(value, actual.getValue());
        assertEquals(inExperiment, actual.getInExperiment());
        assertEquals(hashUsed, actual.getHashUsed());
    }
}
