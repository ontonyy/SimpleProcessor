package com.simple.kafka.handler;

import org.springframework.kafka.support.Acknowledgment;

public interface SimpleMessageHandler {
    void handle(byte[] payload, final Acknowledgment acknowledgment, final String messageType);
}
