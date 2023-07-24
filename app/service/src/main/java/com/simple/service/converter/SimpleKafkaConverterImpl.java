package com.simple.service.converter;

import java.io.IOException;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simple.models.dto.SimpleResponseDto;
import com.simple.models.exception.SimpleException;
import com.simple.models.kafka.SimpleKafkaRequest;
import com.simple.service.api.converter.SimpleKafkaConverter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SimpleKafkaConverterImpl implements SimpleKafkaConverter {
    private final ObjectMapper objectMapper;

    @Override
    public SimpleResponseDto convertSimpleKafkaResponse(final SimpleKafkaRequest simpleRequest) {
        return new SimpleResponseDto(simpleRequest.message());
    }

    @Override
    public <T> T deserializePayload(final byte[] payload, Class<T> clazz) {
        if (Objects.isNull(payload)) {
            return null;
        }
        try {
            return (T) objectMapper.readValue(payload, clazz);
        } catch (IOException ex) {
            log.error("Failed with deserialize payload of class {}", clazz, ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public byte[] convertBytes(final Object object) {
        try {
            return objectMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException ex) {
            log.error("Cannot be serialized to bytes: {}", object, ex);
            throw new SimpleException(ex);
        }
    }
}
