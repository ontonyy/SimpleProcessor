package com.simple.cache.redis;

import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.simple.service.api.cache.SimpleBeanRedisCache;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleBeanRedisCacheImpl implements SimpleBeanRedisCache {
    private final RedisTemplate<String, String> redisTemplate;
    private static final String KEY = "beans:cached";

    @Override
    public void save(final List<String> beanNames) {
        beanNames.stream()
                 .map(this::preBuildBeanName)
                 .forEach(name -> redisTemplate.boundSetOps(KEY).add(name));
        log.info("Saving application bean names in redis cache {}", beanNames);
    }

    private String preBuildBeanName(final String name) {
        final String[] split = name.split("\\.");
        return split[split.length - 1];
    }

    @Override
    public Set<String> get() {
        return redisTemplate.boundSetOps(KEY).members();
    }

    @Override
    @PreDestroy
    public void delete() {
        final Set<String> cachedBeanNames = get();
        cachedBeanNames.forEach(beanName -> redisTemplate.boundSetOps(KEY).remove(beanName));
        log.info("Cleaning redis beans:cached, because application is shut downing!");
    }
}
