package com.connectors.demo.config;

import com.connectors.demo.properties.MultipleProperties;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories
public class ClientConfTemplate {

    private final MultipleProperties mongoProperties;

    public ClientConfTemplate(MultipleProperties mongoProperties) {
        this.mongoProperties = mongoProperties;
    }

    @Primary
    @Bean(name = "clientConfMongoProperties")
    @ConfigurationProperties(prefix = "primary")
    public MongoProperties getPrimary() throws Exception {
        return mongoProperties.getPrimary();
    }

    @Primary
    @Bean(name = "clientConfMongoTemplate")
    public ReactiveMongoTemplate clientConfMongoTemplate() throws Exception {
        System.out.println(primaryMongoDatabaseFactory(getPrimary()));
        return new ReactiveMongoTemplate(primaryMongoDatabaseFactory(getPrimary()));
    }

    @Primary
    @Bean
    public SimpleReactiveMongoDatabaseFactory primaryMongoDatabaseFactory(MongoProperties clientConfMongoProperties) {
        System.out.println(clientConfMongoProperties.getUri());
        MongoClient client = MongoClients.create(clientConfMongoProperties.getUri());
        return new SimpleReactiveMongoDatabaseFactory(client, clientConfMongoProperties.getDatabase());
    }

}
