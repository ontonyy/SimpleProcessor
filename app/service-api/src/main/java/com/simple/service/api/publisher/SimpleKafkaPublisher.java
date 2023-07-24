package com.simple.service.api.publisher;

import com.simple.models.enums.SimpleKafkaMessageType;

public interface SimpleKafkaPublisher {
    void publish(final Object object, final SimpleKafkaMessageType messageType);
}
