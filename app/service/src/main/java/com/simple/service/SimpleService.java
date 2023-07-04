package com.simple.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simple.kafka.SimpleKafkaProducer;
import com.simple.models.requests.SimpleCreateRequest;
import com.simple.models.responses.SimpleResponse;
import com.simple.persistence.entity.SimpleEntity;
import com.simple.persistence.mapper.ModelsMapper;
import com.simple.persistence.repository.SimpleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SimpleService {
    private final SimpleRepository simpleRepository;
    private final SimpleKafkaProducer kafkaProducer;
    private ObjectMapper objectMapper;

    public SimpleResponse createSimpleEntity(final SimpleCreateRequest simpleRequest) {
        final SimpleEntity simple = ModelsMapper.INSTANCE.convertToSimpleEntity(simpleRequest);
        log.info("Simple entity was successfully created in mongo: {}", simple);

        final SimpleResponse simpleResponse = ModelsMapper.INSTANCE.convertToSimpleResponse(simple);
        kafkaProducer.sendMessage(simpleResponse);
        log.info("Simple response successfully goes to: {}", simpleResponse);

        return simpleResponse;
    }

    public SimpleResponse findSimpleEntity(final Long id) {
        log.info("Executed finding Simple entity");
        final SimpleResponse simpleResponse = new SimpleResponse(String.valueOf(id), "Good", "Luck", null, null, null, null, null, null);
        kafkaProducer.sendMessage(simpleResponse);
        return simpleResponse;
    }

    @Bean
    CommandLineRunner runner(final SimpleRepository simpleRepository) {
        return args -> {
        };
    }
}
