package com.simple.service.api.service;

import java.util.Set;

import com.simple.models.dto.SimpleBigResponseDto;
import com.simple.models.dto.SimpleResponseDto;
import com.simple.models.kafka.SimpleKafkaRequest;
import com.simple.models.requests.SimpleCreateRequest;

public interface SimpleService {
    SimpleBigResponseDto create(final SimpleCreateRequest simpleRequest);
    SimpleBigResponseDto find(final String id);
    void doSimpleRequest(final SimpleKafkaRequest simpleKafkaRequest);
    void doSimpleCreateRequest(final SimpleCreateRequest createRequest);
    SimpleResponseDto doSimple();
    Set<String> getBeanNames();
}
