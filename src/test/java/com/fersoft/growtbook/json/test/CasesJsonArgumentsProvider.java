package com.fersoft.growtbook.json.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fersoft.growthbook.condition.Condition;
import com.fersoft.growthbook.deserializer.ConditionDeserializer;
import com.fersoft.growthbook.deserializer.NamespaceDeserializer;
import com.fersoft.growthbook.model.Namespace;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CasesJsonArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<CasesJsonSource> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String testName;

    @Override
    public void accept(CasesJsonSource casesJsonSource) {
        this.testName = casesJsonSource.value();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Namespace.class, new NamespaceDeserializer());
        module.addDeserializer(Condition.class, new ConditionDeserializer());
        objectMapper.registerModule(module);
        JsonNode tests = objectMapper.readTree(CasesJsonArgumentsProvider.class.getClassLoader().getResourceAsStream("cases.json"));
        return StreamSupport.stream(tests.get(testName).spliterator(), true).map(t ->
                Arguments.of(Named.of(t.get(0).asText(), t), objectMapper));
    }
}
