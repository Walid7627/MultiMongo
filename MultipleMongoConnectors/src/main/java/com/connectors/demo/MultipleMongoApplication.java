package com.connectors.demo;

import com.connectors.demo.repo.backend.Backend;
import com.connectors.demo.repo.backend.BackendRepository;
import com.connectors.demo.repo.client.ClientConf;
import com.connectors.demo.repo.client.ClientConfRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootApplication(exclude = {
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class
})
@Configuration
public class MultipleMongoApplication implements CommandLineRunner {

    private final BackendRepository backendRepository;

    private final ClientConfRepository clientConfRepository;

    public MultipleMongoApplication(BackendRepository backendRepository, ClientConfRepository clientConfRepository) {
        this.backendRepository = backendRepository;
        this.clientConfRepository = clientConfRepository;
    }

    @Override
    public void run(String ... args)  {
        Mono<List<Backend>> storeList = this.testBackend();
        Mono<List<ClientConf>> ccList = this.testClientConf();

        storeList.then(ccList).subscribe();
    }

    protected Mono<List<Backend>> testBackend(){
        Mono storeDeleteMono = this.backendRepository.deleteAll();


        Mono<List<Backend>> rsp = storeDeleteMono
                .thenMany(
                        Flux.just("BK","MCD","FG")
                                .map(name -> new Backend(name,"testBack"))
                                .flatMap(backendRepository::save)
                ).thenMany(backendRepository.findAll())
                .collectList();

        return rsp;


    }
    protected Mono<List<ClientConf>> testClientConf(){
        Mono shopDeleteMono = this.clientConfRepository.deleteAll();


        return shopDeleteMono
                .thenMany(
                        Flux.just("SB","DD","TH")
                                .map(name -> new ClientConf(name,"test ClientConf"))
                                .flatMap(clientConfRepository::save)
                ).thenMany(clientConfRepository.findAll())
                .collectList();
    }

    public static void main(String[] args) {
        SpringApplication.run(MultipleMongoApplication.class, args);
    }

}
