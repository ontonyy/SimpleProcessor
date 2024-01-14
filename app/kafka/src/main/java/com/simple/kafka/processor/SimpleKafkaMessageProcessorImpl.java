package com.simple.kafka.processor;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.simple.kafka.handler.SimpleMessageHandler;
import com.simple.models.enums.SimpleKafkaMessageType;
import com.simple.service.api.MDCService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SimpleKafkaMessageProcessorImpl implements SimpleKafkaMessageProcessor {
    private final Map<SimpleKafkaMessageType, SimpleMessageHandler> handlerMap;
    private final MDCService mdcService;

    public SimpleKafkaMessageProcessorImpl(final List<SimpleMessageHandler> handlers, final MDCService mdcService) {
        this.handlerMap = handlers.stream().collect(Collectors.toMap(SimpleMessageHandler::forType, Function.identity()));
        this.mdcService = mdcService;
    }

    @Override
    public void process(final byte[] payload, final Acknowledgment ack, final String messageType) {
        mdcService.putDiagnosticDockerized(() -> {
            final SimpleKafkaMessageType type = SimpleKafkaMessageType.valueOf(messageType);
            handlerMap.get(type).handle(payload, ack);

            log.info("Consuming message from simple kafka with type {}", type);
        });
    }
}
