package com.ak.springbootdemo.sub.constants;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * Enum for input Source Types differentiation
 */
public enum SourceType {
    DATABASE("db"),
    JSON("json"),
    XML("xml"),
    UNDEFINED("undefined");

    @Getter
    private final String sourceTypeValue;

    SourceType(final String sourceTypeValue) {
        this.sourceTypeValue = sourceTypeValue;
    }

    public static Optional<SourceType> getSourceType(final String sourceTypeValue) {
        return Arrays.stream(SourceType.values())
                .filter(c -> c.getSourceTypeValue().equals(sourceTypeValue))
                .findFirst();
    }

}
