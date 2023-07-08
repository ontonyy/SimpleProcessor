package com.simple.models.enums;

import java.util.Arrays;
import java.util.Map;

import com.simple.models.kafka.SimpleKafkaRequest;

import lombok.Getter;

@Getter
public enum SimpleKafkaMessageType {
    SIMPLE_REQUEST("SIMPLE_REQUEST", SimpleKafkaRequest.class);

    private final String name;
    private final Class<?> clazz;
    SimpleKafkaMessageType(final String name, final Class<?> clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public static boolean containsMessageType(final String name) {
        return Arrays.stream(values()).anyMatch(value -> value.name().equals(name));
    }
}
