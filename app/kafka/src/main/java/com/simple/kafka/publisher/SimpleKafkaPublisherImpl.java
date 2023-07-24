package com.simple.kafka.publisher;

import static com.simple.kafka.config.SimpleKafkaConfig.PAYLOAD_TYPE_HEADER;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.simple.kafka.config.SimpleKafkaTopicsProperties;
import com.simple.models.enums.SimpleKafkaMessageType;
import com.simple.service.api.converter.SimpleKafkaConverter;
import com.simple.service.api.publisher.SimpleKafkaPublisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SimpleKafkaPublisherImpl implements SimpleKafkaPublisher {
    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final SimpleKafkaTopicsProperties simpleKafkaTopicsProperties;
    private final SimpleKafkaConverter simpleKafkaConverter;

    public void publish(final Object object, final SimpleKafkaMessageType messageType) {
        final byte[] payload = simpleKafkaConverter.convertBytes(object);
        final Message<byte[]> message = MessageBuilder
                                        .withPayload(payload)
                                        .setHeader(KafkaHeaders.TOPIC, simpleKafkaTopicsProperties.getSimple().getName())
                                        .setHeader(PAYLOAD_TYPE_HEADER, messageType.getName())
                                        .build();

        log.info("Will be sent simple message: {}", object);
        kafkaTemplate.send(message);
    }
}
