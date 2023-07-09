package com.simple.service;

import org.springframework.stereotype.Service;

import com.simple.models.dto.SimpleBigResponseDto;
import com.simple.models.dto.SimpleResponseDto;
import com.simple.models.enums.SimpleKafkaMessageType;
import com.simple.models.kafka.SimpleKafkaRequest;
import com.simple.models.requests.SimpleCreateRequest;
import com.simple.persistence.entity.SimpleEntity;
import com.simple.persistence.mapper.ModelsMapper;
import com.simple.persistence.repository.SimpleEntityRepository;
import com.simple.service.api.publisher.SimpleKafkaPublisher;
import com.simple.service.api.service.SimpleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleServiceImpl implements SimpleService {
    private final SimpleEntityRepository simpleRepository;
    private final SimpleKafkaPublisher kafkaPublisher;

    @Override
    public SimpleBigResponseDto createSimpleEntity(final SimpleCreateRequest simpleRequest) {
        final SimpleEntity simple = ModelsMapper.INSTANCE.convertToSimpleEntity(simpleRequest);
        final SimpleBigResponseDto simpleResponse = ModelsMapper.INSTANCE.convertToSimpleResponse(simple);

        final SimpleKafkaRequest kafkaRequest = new SimpleKafkaRequest("message bla-bla-bla");
        kafkaPublisher.publish(kafkaRequest, SimpleKafkaMessageType.SIMPLE_REQUEST);

        return simpleResponse;
    }

    @Override
    public SimpleBigResponseDto findSimpleEntity(final Long id) {
        log.info("Executed finding Simple entity");
        final SimpleKafkaRequest simpleRequest = new SimpleKafkaRequest(String.valueOf(id));
        kafkaPublisher.publish(simpleRequest, SimpleKafkaMessageType.SIMPLE_REQUEST);
        return new SimpleBigResponseDto(null, null, null, null, null);
    }

    @Override
    public void findSimpleMessageResponse(final SimpleKafkaRequest simpleKafkaRequest) {
        log.info("Trying to find simple message response in db {}", simpleKafkaRequest);
    }

    @Override
    public SimpleResponseDto doSimple() {
        final String message = "Hello, hello, I am good guy, I think ...";
        final SimpleKafkaRequest kafkaRequest = new SimpleKafkaRequest(message);
        kafkaPublisher.publish(kafkaRequest, SimpleKafkaMessageType.SIMPLE_REQUEST);
        return new SimpleResponseDto(message);
    }
}
