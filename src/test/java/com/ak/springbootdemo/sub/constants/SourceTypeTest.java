package com.ak.springbootdemo.sub.constants;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@DisplayName("SourceType tests")
class SourceTypeTest {

    public static Stream<Arguments> sourceTypeInputParams() {
        return Stream.of(
                Arguments.of("db", SourceType.DATABASE),
                Arguments.of("json", SourceType.JSON),
                Arguments.of("xml", SourceType.XML),
                Arguments.of("document", SourceType.UNDEFINED)
        );
    }

    @ParameterizedTest
    @MethodSource("sourceTypeInputParams")
    @DisplayName("getSourceType() test cases")
    void getSourceTypeTest(String type, SourceType sourceType) {
        Assertions.assertEquals(sourceType, SourceType.getSourceType(type).orElse(SourceType.UNDEFINED));
    }

}
