package com.connectors.demo.repo.client;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ClientConf {

    @Id
    private String id;

    private String name;

    public ClientConf(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ClientConf() {
    }
}
