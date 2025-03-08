package com.casa.productos.app.repositoryConfig;

import com.casa.productos.app.models.Store;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface StoreManagerRepository {

    Mono<ResponseEntity<Store>> getProduct(String id);
}
