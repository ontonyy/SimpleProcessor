package com.simple.models.enums;

import java.util.Arrays;
import java.util.Optional;

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

    public static Optional<SimpleKafkaMessageType> getMessageType(final String name) {
        return Arrays.stream(values())
                     .filter(value -> value.name().equals(name))
                     .findFirst();
    }
}
