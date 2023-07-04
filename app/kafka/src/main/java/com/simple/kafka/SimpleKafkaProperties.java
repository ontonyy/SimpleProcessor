package com.simple.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "simple.kafka.topic")
public class SimpleKafkaProperties {
    private String name;
    private Integer partitions;
}
