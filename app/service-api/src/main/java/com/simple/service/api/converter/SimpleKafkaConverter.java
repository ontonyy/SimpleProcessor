package com.simple.service.api.converter;

import java.io.IOException;

import com.simple.models.dto.SimpleResponseDto;
import com.simple.models.kafka.SimpleKafkaRequest;

public interface SimpleKafkaConverter {
    SimpleResponseDto convertSimpleKafkaResponse(final SimpleKafkaRequest simpleRequest);
    <T> T deserializePayload(final byte[] payload, Class<T> clazz);
    byte[] convertBytes(final SimpleKafkaRequest simpleResponse);
}
