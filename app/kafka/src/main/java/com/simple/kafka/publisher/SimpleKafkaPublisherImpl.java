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
    private final SimpleKafkaTopicsProperties kafkaTopicsProps;
    private final SimpleKafkaConverter kafkaConverter;

    @Override
    public void publish(final Object object, final SimpleKafkaMessageType messageType) {
        final byte[] payload = kafkaConverter.convertBytes(object);
        final Message<byte[]> message = createMessage(payload, messageType, kafkaTopicsProps.getSimple().getName());
        kafkaTemplate.send(message);
    }

    private Message<byte[]> createMessage(final byte[] payload, final SimpleKafkaMessageType messageType, final String topic) {
        final MessageBuilder<byte[]> messageBuilder = MessageBuilder
                                                            .withPayload(payload)
                                                            .setHeader(KafkaHeaders.TOPIC, topic)
                                                            .setHeader(KafkaHeaders.KEY, messageType.getName()) // messageKey is used by RobinRound for partitioning messages, that mean that message with same type will be in same partition
                                                            .setHeader(PAYLOAD_TYPE_HEADER, messageType.getName());
        return messageBuilder.build();
    }

}
