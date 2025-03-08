package com.casa.productos.app.repositoryConfig.impl;

import com.casa.productos.app.models.Store;
import com.casa.productos.app.repositoryConfig.StoreManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

@Service
public class StoreManagerRepositoryImpl implements StoreManagerRepository {

    @Autowired
    @Qualifier("marketWebClient")
    private WebClient storeWebClient;

    @Override
    public Mono<ResponseEntity<Store>> getProduct(String id) {
        var request = storeWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/{id}")
                        .build(id));
        return request.retrieve()
                .toEntity(Store.class)
                .timeout(Duration.ofMillis(100L))
                .onErrorResume(error -> {
                    if(error instanceof TimeoutException){
                        System.out.println("Error TimeOut");
                    }
                    System.out.println("-> : "+error.getMessage());
                    return Mono.error(new RuntimeException(error.getMessage()));
                });
    }
}
