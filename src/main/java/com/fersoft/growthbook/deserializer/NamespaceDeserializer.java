package com.fersoft.growthbook.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fersoft.growthbook.model.Namespace;

import java.io.IOException;

/**
 * Deserializer for namespace objects.
 */
public class NamespaceDeserializer extends StdDeserializer<Namespace> {
    /**
     * Constructor.
     */
    public NamespaceDeserializer() {
        super(Namespace.class);
    }

    /**
     * deserializer metod.
     *
     * @param jsonParser
     * @param deserializationContext
     * @return
     * @throws IOException
     */
    @Override
    public Namespace deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        return new Namespace(node.get(0).asText(), node.get(1).asDouble(), node.get(2).asDouble());
    }
}
