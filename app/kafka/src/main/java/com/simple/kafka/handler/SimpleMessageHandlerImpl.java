package com.simple.kafka.handler;

import java.time.Duration;

import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.simple.kafka.config.SimpleKafkaTopicsProperties;
import com.simple.models.enums.SimpleKafkaMessageType;
import com.simple.models.kafka.SimpleKafkaRequest;
import com.simple.service.api.converter.SimpleKafkaConverter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SimpleMessageHandlerImpl implements SimpleMessageHandler {
    private final SimpleKafkaConverter simpleKafkaConverter;
    private final SimpleKafkaTopicsProperties kafkaTopicsProperties;

    @Override
    public void handle(final byte[] payload, final Acknowledgment acknowledgment, final String messageType) {
        try {
            if (SimpleKafkaMessageType.containsMessageType(messageType)) {
                if (messageType.equals(SimpleKafkaMessageType.SIMPLE_REQUEST.name())) {
                    final SimpleKafkaRequest simpleKafkaRequest = (SimpleKafkaRequest) simpleKafkaConverter.deserializePayload(payload, SimpleKafkaMessageType.SIMPLE_REQUEST.getClazz());
                    if (simpleKafkaRequest == null) {
                        log.warn("Handled message from kafka is null");
                        acknowledgment.acknowledge();
                        return;
                    }
                }
                acknowledgment.acknowledge();
            } else {
                log.warn("Handled not supported message type {}", messageType);
                acknowledgment.acknowledge();
            }
        } catch (Exception ex) {
            log.error("Failed to handle message from simple.topic", ex);
            acknowledgment.nack(Duration.ofSeconds(kafkaTopicsProperties.getMessagePollRetrySleepS()));
        }
    }
}
