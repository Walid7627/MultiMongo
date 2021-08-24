package com.connectors.demo.config;

import com.connectors.demo.properties.MultipleProperties;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories
public class BackendTemplate {
    @Autowired
    private MultipleProperties mongoProperties;


    @Qualifier("secondary")
    @Bean(name = "backendMongoProperties")
    @ConfigurationProperties(prefix = "secondary")
    public MongoProperties getSecondary() throws Exception {
        return mongoProperties.getSecondary();
    }

    @Qualifier("secondary")
    @Bean(name = "backendMongoTemplate")
    public ReactiveMongoTemplate backendMongoTemplate() throws Exception {
        return new ReactiveMongoTemplate(secondaryMongoDatabaseFactory(getSecondary()));
    }

    @Bean
    @Qualifier("multipleProperties")
    public SimpleReactiveMongoDatabaseFactory secondaryMongoDatabaseFactory(MongoProperties backendMongoProperties) {
        MongoClient client = MongoClients.create(backendMongoProperties.getUri());
        return new SimpleReactiveMongoDatabaseFactory(client, backendMongoProperties.getDatabase());
    }
}
