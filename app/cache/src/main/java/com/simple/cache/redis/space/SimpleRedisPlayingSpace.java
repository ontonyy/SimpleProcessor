package com.simple.cache.redis.space;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.simple.service.api.date.SimpleDateProviderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
class SimpleRedisPlayingSpace {
    private final SimpleDateProviderService dateProviderService;
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * Redis transactions is working for not interrupt a couple of commands that should goes each by each
     */
    private void doTransaction() {
        final String transactionSetKey = "transaction:set";
        redisTemplate.watch(transactionSetKey);
        redisTemplate.multi();

        // here can be added every redis commands, and be executed only with exec()
        redisTemplate.boundSetOps(transactionSetKey).add("My", "Name", "Is", "Optimus", "Prime");
        final String randomMember = redisTemplate.boundSetOps(transactionSetKey).randomMember();
        final Set<String> members = redisTemplate.boundSetOps(transactionSetKey).members();

        final List<Object> exec = redisTemplate.exec();

        exec.forEach(command -> log.info("Executed command was {}", command.toString()));
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
