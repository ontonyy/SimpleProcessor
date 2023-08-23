package com.simple.kafka.handler;

import java.time.Duration;

import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.simple.kafka.config.SimpleKafkaTopicsProperties;
import com.simple.models.enums.SimpleKafkaMessageType;
import com.simple.models.kafka.SimpleKafkaRequest;
import com.simple.service.api.converter.SimpleKafkaConverter;
import com.simple.service.api.SimpleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleRequestMessageHandlerImpl implements SimpleMessageHandler {
    private final SimpleKafkaConverter kafkaConverter;
    private final SimpleKafkaTopicsProperties kafkaTopicsProperties;
    private final SimpleService service;

    @Override
    public void handle(final byte[] payload, final Acknowledgment acknowledgment) {
        try {
            final SimpleKafkaRequest deserialized = kafkaConverter.deserializePayload(payload, SimpleKafkaRequest.class);
            service.doSimpleRequest(deserialized);
            acknowledgment.acknowledge();
        } catch (Exception ex) {
            log.error("Failed to handle message from {}", kafkaTopicsProperties.getSimple().getName(), ex);
            acknowledgment.nack(Duration.ofSeconds(kafkaTopicsProperties.getMessagePollRetrySleepS()));
        }
    }

    @Override
    public SimpleKafkaMessageType forType() {
        return SimpleKafkaMessageType.SIMPLE_REQUEST;
    }
}
