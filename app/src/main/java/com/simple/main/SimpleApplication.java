package com.simple.main;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class SimpleApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(SimpleApplication.class, args);

        final List<String> projectBeans = Arrays.stream(applicationContext.getBeanDefinitionNames())
                                                       .filter(bean -> bean.contains("com.simple."))
                                                       .toList();
        log.info("Project beans is total {} and: {}", projectBeans.size(), projectBeans);
    }
}
