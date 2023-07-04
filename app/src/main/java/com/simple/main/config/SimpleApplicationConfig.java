package com.simple.main.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.simple.kafka.config.SimpleKafkaConfig;

@Configuration
@EnableWebMvc
@Import(SimpleKafkaConfig.class)
public class SimpleApplicationConfig {
}
