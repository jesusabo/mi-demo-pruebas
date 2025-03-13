package com.casa.productos.app.services.impl;

import com.casa.productos.app.exceptions.ResourceIsAlreadyException;
import com.casa.productos.app.exceptions.ResourceNotFoundException;
import com.casa.productos.app.models.Product;
import com.casa.productos.app.repository.ProductRepository;
import com.casa.productos.app.repositoryConfig.StoreManagerRepository;
import com.casa.productos.app.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.concurrent.TimeoutException;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final StoreManagerRepository storeManagerRepository;

    @Override
    public Mono<Product> save(Product product) {
        return this.findByCode(product.getCode())
                .flatMap(res -> Mono.<Product>error(new ResourceIsAlreadyException("Recurso existente")))
                .onErrorResume(error -> {
                    if(error instanceof ResourceNotFoundException){
                       return storeManagerRepository.getProduct("4")
                                .flatMap(res -> {
                                    if(res.getBody()!=null){
                                        product.setName(res.getBody().getDescription());
                                        return Mono.fromCallable( () -> productRepository.save(product));
                                    }
                                   return Mono.error(new RuntimeException("Error sin Body"));
                                })
                               .onErrorResume(er -> {
                                   System.out.println("ErrorXXX");
                                   return Mono.error(new RuntimeException("ERRORXXXX"));
                               });
                    }else{
                        System.out.println("2");
                        
                        return Mono.error(error);
                    }
                });

//                .defaultIfEmpty(product)
//                .flatMap(prod -> prod.getId()==null? productRepository.save(prod) :Mono.just(prod));
    }

    @Override
    public Mono<Product> update(String id, Product product) {
        log.info("update");
        return this.findById(id)
                .flatMap(prod -> {
                    prod.setName(product.getName());
                    prod.setCategory(product.getCategory());
                    prod.setPrice(product.getPrice());
                    return Mono.fromCallable(() -> productRepository.save(prod));
                });

    }

    @Override
    public Flux<Product> findAll() {
        return Flux.fromIterable(productRepository.findAll());
    }

    @Override
    public Mono<Product> findById(String id) {
        return Mono.fromCallable(() -> productRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("Producto no existe")));
    }

    @Override
    public Mono<Product> findByCode(int code) {
        return Mono.fromCallable(() -> productRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no existe")));
    }

    @Override
    public Mono<String> deleteById(String id) {
        return Mono.fromRunnable(() -> {
            Product product = productRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no existe"));

            productRepository.deleteById(Long.parseLong(id));
        }).thenReturn("Se eliminó correctamente por Id");
    }

    @Override
    public Mono<String> deleteByCode(int code) {
        return Mono.fromRunnable(() -> {
            Product product = productRepository.findByCode(code)
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no existe"));

            productRepository.deleteByCode(product.getCode());
        }).thenReturn("Se eliminó correctamente por Code");

    }

}
