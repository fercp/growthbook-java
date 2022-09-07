package com.fersoft.growthbook.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fersoft.growthbook.condition.*;
import com.fersoft.growthbook.condition.logical.And;
import com.fersoft.growthbook.condition.logical.Nor;
import com.fersoft.growthbook.condition.logical.Not;
import com.fersoft.growthbook.condition.logical.Or;
import com.fersoft.growthbook.condition.relational.*;

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
    public FreeCondition<? extends Object> parse(final Map<String, Object> conditions) {
        if (isPureObject(conditions)) {
            return new ConditionWithValue<>("$", conditions);
        }
        return new And(parseFreeConditions(conditions));
    }

    private List<FreeCondition<?>> parseFreeConditions(final Map<String, Object> conditions) {
        return conditions.keySet().stream().map(k -> {
            if (!k.startsWith("$")) {
                if (!(conditions.get(k) instanceof Map)) {
                    return new ConditionWithValue<>(k, conditions.get(k));
                }
                return new ConditionWithKey(k, parseDependedConditions((Map<String, Object>) conditions.get(k)));
            }
            if (k.equals("$or")) {
                return new Or(parse((List) conditions.get(k)));
            }
            if (k.equals("$nor")) {
                return new Nor(parse((List) conditions.get(k)));
            }

            if (k.equals("$and")) {
                return new And(parse((List) conditions.get(k)));
            }
            if (k.equals("$not")) {
                return new Not(parseFreeConditions(((Map<String, Object>) conditions.get(k))));
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private List<DependedCondition> parseDependedConditions(final Map<String, Object> conditions) {
        return conditions.keySet().stream().map(k -> {
            if (k.equals("$all")) {
                return new All<>((List) conditions.get(k));
            }
            if (k.equals("$eq")) {
                return new Eq<>(conditions.get(k));
            }
            if (k.equals("$ne")) {
                return new Ne<>(conditions.get(k));
            }
            if (k.equals("$in")) {
                return new In<>((List) conditions.get(k));
            }
            if (k.equals("$not")) {
                return new com.fersoft.growthbook.condition.relational.Not<>(parseDependedConditions((Map<String, Object>) conditions.get(k)).get(0));
            }
            if (k.equals("$regex")) {
                return new Regex((String) conditions.get(k));
            }
            if (k.equals("$gt")) {
                return new Gt<>((Comparable) conditions.get(k));
            }
            if (k.equals("$lt")) {
                return new Lt<>((Comparable) conditions.get(k));
            }
            if (k.equals("$gte")) {
                return new Gte<>((Comparable) conditions.get(k));
            }
            if (k.equals("$lte")) {
                return new Lte<>((Comparable) conditions.get(k));
            }
            if (k.equals("$exists")) {
                return new Exists((Boolean) conditions.get(k));
            }
            if (k.equals("$nin")) {
                return new NotIn<>((List) conditions.get(k));
            }
            if (k.equals("$elemMatch")) {
                List<DependedCondition> condition = parseDependedConditions((Map<String, Object>) conditions.get(k));
                if (!condition.isEmpty()) {
                    return new ElemMatch<>(condition.get(0));
                } else {
                    return new ElemMatch<>(parseFreeConditions((Map<String, Object>) conditions.get(k)).get(0));
                }
            }
            if (k.equals("$type")) {
                return new Type<>((String) conditions.get(k));
            }
            if (k.equals("$size")) {
                if (conditions.get(k) instanceof Integer) {
                    return new Size<>((Integer) conditions.get(k));
                } else {
                    return new SizeWithCondition<>(parseDependedConditions((Map<String, Object>) conditions.get(k)).get(0));
                }
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private boolean isPureObject(final Map<String, Object> conditions) {
        if (conditions.keySet().stream().noneMatch(c -> c.startsWith("$"))) {
            return conditions.entrySet().stream().filter(e -> e.getValue() instanceof Map).allMatch(v -> isPureObject((Map) v.getValue()));
        }
        return false;
    }

    private List parse(final List conditions) {
        return (List) conditions.stream().flatMap(k -> {
            Map<String, Object> condition = (Map<String, Object>) k;
            return parseFreeConditions(condition).stream();
        }).collect(Collectors.toList());
    }
}
