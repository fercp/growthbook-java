package com.fersoft.growtbook.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fersoft.growtbook.json.test.CasesJsonSource;
import com.fersoft.growthbook.GrowthBook;
import com.fersoft.growthbook.condition.Condition;
import com.fersoft.growthbook.model.*;
import com.fersoft.growthbook.parser.ConditionParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EvalFeatureTest {
    @DisplayName("Eval Feature test")
    @ParameterizedTest(name = "{0}")
    @CasesJsonSource("feature")
    void runTests(JsonNode testNode, ObjectMapper objectMapper) {
        Map<String, Object> featuresObj = objectMapper.convertValue(testNode.get(1), new TypeReference<Map<String, Object>>() {
        });
        String key = testNode.get(2).asText();
        Context context = new Context();
        context.setAttributes(objectMapper.convertValue(testNode.get(1).get("attributes"), new TypeReference<Map<String, Object>>() {
        }));
        context.setForcedVariations(objectMapper.convertValue(testNode.get(1).get("forcedVariations"), new TypeReference<Map<String, Object>>() {
        }));
        Map<String, Feature> featureMap = new HashMap<>();
        if (featuresObj.containsKey("features")) {
            JsonNode fm = testNode.get(1).get("features").get("feature");
            Feature feature = new Feature();
            if (fm.get("defaultValue") != null) {
                feature.setDefaultValue(objectMapper.convertValue(fm.get("defaultValue"), Object.class));
            }
            if (fm.get("rules") != null) {
                List<FeatureRule> featureRules = new ArrayList<>();
                fm.get("rules").iterator().forEachRemaining(r -> {
                    FeatureRule featureRule = new FeatureRule();
                    featureRule.setForce(objectMapper.convertValue(r.get("force"), Object.class));
                    if (r.get("coverage") != null) {
                        featureRule.setCoverage(r.get("coverage").asDouble());
                    }
                    if (r.get("condition") != null) {
                        ConditionParser conditionParser = new ConditionParser();
                        Condition condition = conditionParser.parse(objectMapper.convertValue(r.get("condition"), new TypeReference<Map<String, Object>>() {
                        }));
                        featureRule.setCondition(condition);
                    }
                    featureRule.setVariations(objectMapper.convertValue(r.get("variations"), List.class));
                    if (r.get("hashAttribute") != null) {
                        featureRule.setHashAttribute(r.get("hashAttribute").asText());
                    }
                    JsonNode namespace = r.get("namespace");
                    if (namespace != null) {
                        featureRule.setNamespace(new Namespace(namespace.get(0).asText(), namespace.get(1).asDouble(), namespace.get(2).asDouble()));
                    }
                    if (r.get("key") != null) {
                        featureRule.setKey(r.get("key").asText());
                    }

                    featureRule.setWeights(objectMapper.convertValue(r.get("weights"), new TypeReference<List<Double>>() {
                    }));
                    featureRules.add(featureRule);
                });

                feature.setRules(featureRules);

            }

            featureMap.put(key, feature);
        }


        context.setFeatures(featureMap);
        GrowthBook growthBook = new GrowthBook(context);
        FeatureResult actual = growthBook.evalFeature(key);
        FeatureResult expected = objectMapper.convertValue(testNode.get(3), new TypeReference<FeatureResult>() {
        });

        assertEquals(expected.getValue(), actual.getValue());
        assertEquals(expected.getOn(), actual.getOn());
        assertEquals(expected.getOff(), actual.getOff());
        assertEquals(expected.getExperiment(), actual.getExperiment());
        assertEquals(expected.getExperimentResult(), actual.getExperimentResult());

    }
}
