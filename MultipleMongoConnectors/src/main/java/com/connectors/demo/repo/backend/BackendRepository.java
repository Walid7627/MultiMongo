package com.connectors.demo.repo.backend;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BackendRepository extends ReactiveCrudRepository<Backend, String> {
}
