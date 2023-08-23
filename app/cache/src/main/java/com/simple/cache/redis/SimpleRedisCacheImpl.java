package com.simple.cache.redis;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.simple.service.api.cache.SimpleRedisCache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleRedisCacheImpl implements SimpleRedisCache {
    private final Duration ttl;
    private final RedisTemplate<String, String> redisTemplate;

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
