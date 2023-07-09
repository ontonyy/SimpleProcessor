package com.simple.kafka.handler;

import java.time.Duration;
import java.util.Optional;

import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.simple.kafka.config.SimpleKafkaTopicsProperties;
import com.simple.models.enums.SimpleKafkaMessageType;
import com.simple.models.kafka.SimpleKafkaRequest;
import com.simple.service.api.converter.SimpleKafkaConverter;
import com.simple.service.api.service.SimpleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SimpleMessageHandlerImpl implements SimpleMessageHandler {
    private final SimpleKafkaConverter simpleKafkaConverter;
    private final SimpleKafkaTopicsProperties kafkaTopicsProperties;
    private final SimpleService simpleService;

    @Override
    public void handle(final byte[] payload, final Acknowledgment acknowledgment, final String messageType) {
        try {
            final Optional<SimpleKafkaMessageType> kafkaMessageType = SimpleKafkaMessageType.getMessageType(messageType);
            if (kafkaMessageType.isEmpty()) {
                log.warn("Handled not supported message type {}", messageType);
                acknowledgment.acknowledge();
                return;
            }

            final Object deserialized = simpleKafkaConverter.deserializePayload(payload, kafkaMessageType.get().getClazz());
            if (deserialized == null) {
                log.warn("Handled message from kafka is null");
                acknowledgment.acknowledge();
                return;
            }

            if (kafkaMessageType.get().equals(SimpleKafkaMessageType.SIMPLE_REQUEST)) {
                simpleService.findSimpleMessageResponse((SimpleKafkaRequest) deserialized);
            }

            acknowledgment.acknowledge();
        } catch (Exception ex) {
            log.error("Failed to handle message from simple.topic", ex);
            acknowledgment.nack(Duration.ofSeconds(kafkaTopicsProperties.getMessagePollRetrySleepS()));
        }
    }
}
