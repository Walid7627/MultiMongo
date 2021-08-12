package com.connectors.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(exclude = {
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class
},
        scanBasePackageClasses = {
                MultipleMongoApplication.class,
        })
@Configuration
public class MultipleMongoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultipleMongoApplication.class, args);
    }
}
