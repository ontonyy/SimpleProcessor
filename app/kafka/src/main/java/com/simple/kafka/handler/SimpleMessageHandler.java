package com.simple.kafka.handler;

import org.springframework.kafka.support.Acknowledgment;

import com.simple.models.enums.SimpleKafkaMessageType;

public interface SimpleMessageHandler {
    void handle(final byte[] payload, final Acknowledgment acknowledgment);
    SimpleKafkaMessageType forType();
}
