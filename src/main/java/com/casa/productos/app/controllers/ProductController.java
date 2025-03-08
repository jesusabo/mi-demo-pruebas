package com.casa.productos.app.controllers;

import com.azure.core.annotation.Post;
import com.casa.productos.app.models.Product;
import com.casa.productos.app.services.ProductService;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Product>> buscarPorId(@PathVariable String id){
        log.info("buscarPorId: {}",id);
        return productService.findById(id).map(prod -> ResponseEntity.status(HttpStatus.OK).body(prod));
    }

    @GetMapping("/code/{id}")
    public Mono<ResponseEntity<Product>> buscarPorCode(@PathVariable Integer id){
        log.info("buscarPorCode: {}",id);
        return productService.findByCode(id).map(prod -> ResponseEntity.status(HttpStatus.OK).body(prod));
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<Product>>> listar(){
        return Mono.just(ResponseEntity.status(HttpStatus.OK).body(productService.findAll()));
    }

    @PostMapping
    public Mono<ResponseEntity<Product>> guardar(@RequestBody Product product){
        return productService.save(product).map(prod-> ResponseEntity.status(HttpStatus.OK).body(prod));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Product>> actualizar(@PathVariable String id, @RequestBody Product product){
        return productService.update(id,product).map(prod-> ResponseEntity.status(HttpStatus.OK).body(prod));
    }

    @DeleteMapping("/id/{id}")
    public Mono<ResponseEntity<String>> eliminarPorId(@PathVariable String id){
        log.info("eliminarPorId: {}",id);
        return productService.deleteById(id).map(prod->ResponseEntity.status(HttpStatus.OK).body(prod));
    }

    @DeleteMapping("/code/{code}")
    public Mono<ResponseEntity<String>> eliminarPorCode(@PathVariable int code){
        log.info("eliminarPorCode: {}", code);
        return productService.deleteByCode(code).map(prod-> ResponseEntity.status(HttpStatus.OK).body(prod));
    }
}
