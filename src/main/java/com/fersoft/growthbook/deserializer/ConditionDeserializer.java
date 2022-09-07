package com.fersoft.growthbook.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fersoft.growthbook.condition.Condition;
import com.fersoft.growthbook.parser.ConditionParser;

import java.io.IOException;
import java.util.Map;

/**
 * Deserializer for Condition objects.
 */
public class ConditionDeserializer extends StdDeserializer<Condition> {
    /**
     * Object mapper.
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Constructor.
     */
    public ConditionDeserializer() {
        super(Condition.class);
    }

    /**
     * deserializer.
     *
     * @param jsonParser
     * @param deserializationContext
     * @return
     * @throws IOException
     */
    @Override
    public Condition deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        ConditionParser conditionParser = new ConditionParser();
        return conditionParser.parse(objectMapper.convertValue(node, new TypeReference<Map<String, Object>>() {
        }));
    }
}
