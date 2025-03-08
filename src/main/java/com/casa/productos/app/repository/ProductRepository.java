package com.casa.productos.app.repository;

import com.casa.productos.app.models.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveMongoRepository<Product,String> {

    Mono<Product> findByCode(int code);

    Mono<Void> deleteByCode(Product product);
}
