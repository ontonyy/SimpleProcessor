package com.simple.service.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
@ConditionalOnProperty(prefix = "spring.thread-executor.virtual", name = "enabled", havingValue = "true", matchIfMissing = true)
public class SimpleVirtualExecutorsConfig {
    public static final String VIRTUAL_ASYNC_EXECUTOR = "VIRTUAL_ASYNC_EXECUTOR";

    @Bean(name = VIRTUAL_ASYNC_EXECUTOR)
    public Executor virtualAsyncExecutor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }
}
