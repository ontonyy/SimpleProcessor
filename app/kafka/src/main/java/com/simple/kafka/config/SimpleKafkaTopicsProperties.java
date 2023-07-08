package com.simple.kafka.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter

@ConfigurationProperties(prefix = "kafka.topics")
public class SimpleKafkaTopicsProperties {
    private TopicDefinition simple;
    private Integer messagePollRetrySleepS;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopicDefinition {
        private String name;
        private String retentionMs;
        private Integer partitions;
    }
}
