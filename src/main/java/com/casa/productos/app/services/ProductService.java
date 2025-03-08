package com.casa.productos.app.services;

import com.casa.productos.app.models.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
    Mono<Product> save(Product product);

    Mono<Product> update(String id, Product product);

    Flux<Product> findAll();

    Mono<Product> findById(String id);

    Mono<Product> findByCode(int code);

    Mono<String> deleteById(String code);

    Mono<String> deleteByCode(int code);
}