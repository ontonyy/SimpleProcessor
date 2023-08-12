package com.simple.kafka.processor;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.simple.models.enums.SimpleKafkaMessageType;
import com.simple.service.api.kafka.SimpleKafkaMessageProcessor;
import com.simple.service.api.kafka.SimpleMessageHandler;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SimpleKafkaMessageProcessorImpl implements SimpleKafkaMessageProcessor {
    private final Map<SimpleKafkaMessageType, SimpleMessageHandler> handlerMap;

    public SimpleKafkaMessageProcessorImpl(final List<SimpleMessageHandler> handlers) {
        this.handlerMap = handlers.stream().collect(Collectors.toMap(SimpleMessageHandler::forType, Function.identity()));
    }

    @Override
    @SneakyThrows
    public void process(final byte[] payload, final Acknowledgment acknowledgment, final String messageType) {
        final SimpleKafkaMessageType type = SimpleKafkaMessageType.valueOf(messageType);
        log.info("Consuming message from simple kafka with type {}", type);

        handlerMap.get(type).handle(payload, acknowledgment);
    }
}
