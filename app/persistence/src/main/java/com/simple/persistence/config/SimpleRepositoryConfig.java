package com.simple.persistence.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ComponentScan(basePackages = "com.simple.persistence")
@EnableMongoRepositories(basePackages = "com.simple.persistence.repository")
public class SimpleRepositoryConfig {
}

