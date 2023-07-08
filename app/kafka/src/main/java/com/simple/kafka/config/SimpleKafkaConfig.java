package com.simple.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableKafka
@RequiredArgsConstructor
@ComponentScan(basePackages = "com.simple.kafka")
@Import(SimpleKafkaTopicsProperties.class)
public class SimpleKafkaConfig {
    private final SimpleKafkaTopicsProperties props;
    public static final String PAYLOAD_TYPE_HEADER = "X-Payload-Type";


    @Bean
    public NewTopic newTopic() {
        return TopicBuilder
                .name(props.getSimple().getName())
                .partitions(props.getSimple().getPartitions())
                .config(TopicConfig.RETENTION_MS_CONFIG, props.getSimple().getRetentionMs())
                .replicas(1)
                .build();
    }
}
