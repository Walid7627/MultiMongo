package com.connectors.demo.repo;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.connectors.demo.repo.backend",
        reactiveMongoTemplateRef = "backendMongoTemplate")
public class BackendMongoConfig {
}

