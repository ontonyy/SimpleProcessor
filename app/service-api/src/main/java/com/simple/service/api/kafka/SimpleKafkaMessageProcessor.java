package com.simple.service.api.kafka;

import org.springframework.kafka.support.Acknowledgment;

public interface SimpleKafkaMessageProcessor {
    void process(byte[] payload, final Acknowledgment acknowledgment, final String messageType);
}
