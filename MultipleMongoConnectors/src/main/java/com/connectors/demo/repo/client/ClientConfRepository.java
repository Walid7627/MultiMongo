package com.connectors.demo.repo.client;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ClientConfRepository extends ReactiveMongoRepository<ClientConf, String> {
    Mono<ClientConf> findById(String id);
}
