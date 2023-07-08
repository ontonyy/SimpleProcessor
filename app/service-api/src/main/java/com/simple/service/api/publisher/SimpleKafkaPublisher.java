package com.simple.service.api.publisher;

import com.simple.models.enums.SimpleKafkaMessageType;
import com.simple.models.kafka.SimpleKafkaRequest;

public interface SimpleKafkaPublisher {
    void publish(final SimpleKafkaRequest simpleKafkaRequest, final SimpleKafkaMessageType messageType);
}
