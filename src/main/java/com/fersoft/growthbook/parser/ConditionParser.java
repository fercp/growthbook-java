package com.fersoft.growthbook.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fersoft.growthbook.condition.*;
import com.fersoft.growthbook.condition.logical.And;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * converts map/json representation of a condition into a Condition object.
 */
public class ConditionParser {
    /**
     * object mapper.
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * parses json condition into Condition.
     *
     * @param condition json string representation of condition.
     * @return Condition object.
     */
    public Condition parse(final String condition) {
        try {
            return parse(
                    objectMapper.readValue(condition, new TypeReference<Map<String, Object>>() {
                    }));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * parses map condition into Condition.
     *
     * @param conditions map representation of condition.
     * @return Condition object.
     */
    public Condition<? extends Object, Map<String, Object>> parse(final Map<String, Object> conditions) {
        if (isPureObject(conditions)) {
            return new ConditionWithValue<>("$", conditions);
        }
        return new And((List<DependedCondition<?, Map<String, Object>>>) parseFreeConditions(conditions));
    }

    private List<? extends Condition> parseFreeConditions(final Map<String, Object> conditions) {
        return conditions.keySet().stream().map(k -> {
            if (!k.startsWith("$")) {
                if (!(conditions.get(k) instanceof Map)) {
                    return new ConditionWithValue<>(k, conditions.get(k));
                }
                return new ConditionWithKey(k, (List<DependedCondition>) parseFreeConditions((Map<String, Object>) conditions.get(k)));
            }
            try {
                Class<? extends DependedCondition<?, ?>> conditionClass = ConditionTypes.getConditionClass(k);
                try {
                    Constructor<? extends Condition<?, ?>> constructor = (Constructor<? extends Condition<?, ?>>) conditionClass.getConstructors()[0];
                    Object conditionValue = conditions.get(k);

                    if (conditionValue instanceof Map) {
                        return constructor.newInstance(parseFreeConditions((Map) conditions.get(k)));
                    }
                    if (conditionValue instanceof List) {
                        try {
                            return constructor.newInstance(parse((List) conditionValue));
                        } catch (Exception e) {
                        }
                    }
                    return constructor.newInstance(conditions.get(k));
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            } catch (IllegalArgumentException e) {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private boolean isPureObject(final Object conditions) {
        if (conditions instanceof Map) {
            if (((Map<String, Object>) conditions).keySet().stream().noneMatch(c -> c.startsWith("$"))) {
                return ((Map<String, Object>) conditions).entrySet().stream()
                        .filter(e -> e.getValue() instanceof Map).allMatch(v -> isPureObject(v.getValue()));
            }
        } else if (conditions instanceof List) {
            ((List) conditions).stream().allMatch(this::isPureObject);
        }
        return false;
    }

    private List<? extends Condition> parse(final List conditions) {
        return (List) conditions.stream().flatMap(k -> {
            Map<String, Object> condition = (Map<String, Object>) k;
            return parseFreeConditions(condition).stream();
        }).collect(Collectors.toList());
    }

}
