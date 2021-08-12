package com.connectors.demo.config;

import com.connectors.demo.properties.MultipleMongoProperties;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableConfigurationProperties(MultipleMongoProperties.class)
@EnableReactiveMongoRepositories
public class BackendMongoTemplate {

    private final MultipleMongoProperties mongoProperties;

    public BackendMongoTemplate(MultipleMongoProperties mongoProperties) {
        this.mongoProperties = mongoProperties;
    }

    @Bean(name = "backendMongoProperties")
    @ConfigurationProperties(prefix = "secondary")
    public MongoProperties getSecondary() throws Exception {
        return mongoProperties.getSecondary();
    }

    @Bean(name = "backendMongoTemplate")
    public ReactiveMongoTemplate backendMongoTemplate() throws Exception {
        return new ReactiveMongoTemplate(secondaryMongoDatabaseFactory(getSecondary()));
    }

    @Bean
    public SimpleReactiveMongoDatabaseFactory secondaryMongoDatabaseFactory(MongoProperties backendMongoProperties) {
        MongoClient client = MongoClients.create(backendMongoProperties.getUri());
        return new SimpleReactiveMongoDatabaseFactory(client, backendMongoProperties.getDatabase());
    }
}
