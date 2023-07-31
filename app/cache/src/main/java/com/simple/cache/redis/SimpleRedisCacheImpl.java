package com.simple.cache.redis;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.function.Function;

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


    private void doArticle() {
        redisTemplate.opsForValue().increment("article:hello", 10);
        final Map<String, ? extends Serializable> stringMap = Map.of("name", "Mikhail", "age", 25, "level", 10, "money", 1000, "work", "Developer");
        redisTemplate.opsForHash().put("simple:article", String.valueOf(stringMap.hashCode()), stringMap.toString());
    }

    private void updateCookieToken(final String token, final String user, final String item) {
        final Date date = new Date();
        final String formattedDate = formatDatetime(date);
        redisTemplate.opsForHash().put("token:" + token, user.hashCode(), user);
        redisTemplate.opsForZSet().add("recent:" + token, formattedDate, date.getTime());
        if (Objects.nonNull(item)) {
            redisTemplate.opsForZSet().add("viewed:" + token, item, date.getTime());
            redisTemplate.opsForZSet().removeRangeByScore("viewed:" + token, 0, -26);
        }
    }

    private void cleanCookieSessions() {
        final long limit = 100000L;
        long size;
        while (true) {
            size = redisTemplate.opsForZSet().zCard("recent:*");
            if (size < limit) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                break;
            }
        }

        final long endIndex = Math.min(size - limit, 100);
        final Set<String> tokens = redisTemplate.opsForZSet().range("recent:", 0, endIndex - 1);
        List<String> viewedTokens = new ArrayList<>();
        for (final String token : tokens) {
            viewedTokens.add("viewed:" + token);
        }

        redisTemplate.delete(viewedTokens);
        redisTemplate.opsForHash().delete("login:", tokens);
        redisTemplate.opsForZSet().remove("recent:", tokens);


    }

    private String formatDatetime(final Date date) {
        final DateFormat format = dateProviderService.getDateFormat();
        return format.format(date);
    }

    private void addToCart(final String session, final String item, final int count) {
        if (count <= 0) {
            redisTemplate.boundHashOps("cart:" + session).delete(item);
        } else {
            redisTemplate.boundHashOps("cart:" + session).put(item, count);
        }
    }

    private String cacheRequest(final String request, final Runnable func) {
        if (redisTemplate.hasKey(request)) {
            return redisTemplate.boundValueOps("cache:" + request).get();
        } else {
            final Thread thread = new Thread(func);
            thread.start();
            return null;
        }
    }
}
