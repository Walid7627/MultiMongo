package com.connectors.demo.repo.backend;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Backend {

    @Id
    private String id;

    private String name;

    public Backend(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Backend() {
    }
}
