package com.simple.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import com.simple.kafka.SimpleKafkaProperties;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SimpleKafkaTopicConfig {
    private final SimpleKafkaProperties simpleKafkaProperties;

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(simpleKafkaProperties.getName())
                           .partitions(simpleKafkaProperties.getPartitions())
                           .replicas(1)
                           .build();
    }
}
