package com.simple.cache.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.simple.cache")
@Import(RedisAutoConfiguration.class)
public class SimpleCacheConfig {
    @Value("${cache.ttl:PT10S}") private String ttl;

    @Bean
    public Duration ttl() {
        return Duration.parse(ttl);
    }
}
