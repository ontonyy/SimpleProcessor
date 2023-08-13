package com.simple.kafka.listener;

import static com.simple.kafka.config.SimpleKafkaConfig.PAYLOAD_TYPE_HEADER;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.simple.kafka.processor.SimpleKafkaMessageProcessor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SimpleKafkaListener {
    private final SimpleKafkaMessageProcessor kafkaMessageProcessor;

    @KafkaListener(topics = "${kafka.topics.simple.name}", groupId = "${spring.application.name}-group")
    public void listen(@Payload final byte[] payload, @Header(PAYLOAD_TYPE_HEADER) final String messageType, final Acknowledgment acknowledgment) {
        kafkaMessageProcessor.process(payload, acknowledgment, messageType);
    }
}