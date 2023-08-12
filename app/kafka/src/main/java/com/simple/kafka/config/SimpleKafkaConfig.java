package com.simple.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@ComponentScan(basePackages = "com.simple.kafka")
public class SimpleKafkaConfig {
    public static final String PAYLOAD_TYPE_HEADER = "X-Payload-Type";

    @Bean
    public NewTopic simpleTopic(final SimpleKafkaTopicsProperties props) {
        return TopicBuilder
                .name(props.getSimple().getName())
                .partitions(props.getSimple().getPartitions())
                .config(TopicConfig.RETENTION_MS_CONFIG, props.getSimple().getRetentionMs())
                .replicas(1)
                .build();
    }
}
