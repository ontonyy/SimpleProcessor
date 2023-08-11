package com.simple.cache.redis;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.simple.service.api.cache.SimpleRedisCache;
import com.simple.service.api.date.SimpleDateProviderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SimpleRedisCacheImpl implements SimpleRedisCache {
    private final SimpleDateProviderService dateProviderService;
    private final Duration ttl; // TimeToLive = 10 seconds
    private final RedisTemplate<String, String> redisTemplate;

    public SimpleRedisCacheImpl(@Value("${cache.ttl:PT10S}") final String ttl,
                                final RedisTemplate<String, String> redisTemplate,
                                final SimpleDateProviderService dateProviderService) {
        this.ttl = Duration.parse(ttl);
        this.redisTemplate = redisTemplate;
        this.dateProviderService = dateProviderService;
    }

    @Override
    public void hello() {
        final String key = key("1", "hello");
        redisTemplate.opsForSet().add(key, "hello redis!");
        redisTemplate.expire(key, ttl);
    }

    private String key(final String... keys) {
        return "simple:" + String.join(":", keys);
    }
}
