package com.simple.service.api.service;

import com.simple.models.dto.SimpleBigResponseDto;
import com.simple.models.dto.SimpleResponseDto;
import com.simple.models.requests.SimpleCreateRequest;

public interface SimpleService {
    SimpleBigResponseDto createSimpleEntity(final SimpleCreateRequest simpleRequest);
    SimpleBigResponseDto findSimpleEntity(final Long id);
    SimpleResponseDto doSimple();
}
