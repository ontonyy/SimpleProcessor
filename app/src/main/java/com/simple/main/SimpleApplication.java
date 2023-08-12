package com.simple.main;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.simple.cache.redis.SimpleBeanRedisCacheImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class SimpleApplication {
    public static void main(String[] args) {
        final ApplicationContext applicationContext = SpringApplication.run(SimpleApplication.class, args);
        logBeans(applicationContext);
    }

    private static void logBeans(final ApplicationContext applicationContext) {
        final List<String> beanNames = Arrays.stream(applicationContext.getBeanDefinitionNames())
                                             .filter(bean -> bean.contains("com.simple"))
                                             .toList();

        final SimpleBeanRedisCacheImpl beanRedisCache = applicationContext.getBean(SimpleBeanRedisCacheImpl.class);
        beanRedisCache.save(beanNames);
    }
}
