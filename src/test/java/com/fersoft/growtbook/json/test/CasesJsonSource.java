package com.fersoft.growtbook.json.test;

import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ArgumentsSource(CasesJsonArgumentsProvider.class)
public @interface CasesJsonSource {
    /**
     * The name of the tests in cases.json
     */
    String value();
}
