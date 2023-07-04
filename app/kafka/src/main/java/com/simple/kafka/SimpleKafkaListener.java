package com.simple.kafka;

import java.io.IOException;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simple.models.responses.SimpleResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SimpleKafkaListener {
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "#{simpleKafkaProperties.name}")
    public void listen(final byte[] payload) {
        final SimpleResponse simpleResponse = convertResponse(payload);
        log.info("Kafka is get: {}", simpleResponse);
    }

    private SimpleResponse convertResponse(final byte[] payload) {
        try {
            return objectMapper.readValue(payload, SimpleResponse.class);
        } catch (IOException ex) {
            log.error("Cannot be converted Simple response from: {}", payload, ex);
            return null;
        }
    }
}
