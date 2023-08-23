package com.simple.service;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.simple.service.api.MDCService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MDCServiceImpl implements MDCService {
    @Value("${simple.logging.prefix}")
    private String prefix;
    private static final String MDC_DOCKERIZED_PREFIX = "dockerized";

    @Override
    public void putDiagnosticDockerized(final Runnable runnable) {
        try {
            MDC.put(MDC_DOCKERIZED_PREFIX, prefix);
            runnable.run();
        } finally {
            MDC.remove(MDC_DOCKERIZED_PREFIX);
        }
    }
}
