package com.casa.productos.app.services.impl;

import com.casa.productos.app.exceptions.ResourceIsAlreadyException;
import com.casa.productos.app.models.Product;
import com.casa.productos.app.models.Store;
import com.casa.productos.app.repository.ProductRepository;
import com.casa.productos.app.repositoryConfig.StoreManagerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.oauth2.sdk.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = ProductServiceImpl.class)
class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private StoreManagerRepository storeManagerRepository;

    private Product product;
    private BigDecimal bd;
    private Store store;

    @BeforeEach
    void setUp(){
        bd = BigDecimal.valueOf(1001);
        product = Product.builder()
                        .id(123456L)
                        .price(bd)
                        .code(999999)
                        .name("bicicleta")
                        .category("deportes")
                        .build();

        try {
            store = convertJSONToObject();
            System.out.println("Store>>>: "+store);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void Prueba1(){
        when(productRepository.findByCode(anyInt())).thenReturn(Optional.empty());

        when(productRepository.save(any(Product.class))).thenReturn(product);

        when(storeManagerRepository.getProduct(anyString())).thenReturn(Mono.just(ResponseEntity.status(HttpStatus.OK).body(store)));

        var response = productService.save(Product.builder()
                .price(bd)
                .code(999999)
                .name("bicicleta")
                .category("deportes")
                .build());

        StepVerifier.create(response)
                .assertNext(saveProduct -> {
                    assertNotNull(saveProduct.getId(),"El ID no debe ser nulo");
                    assertEquals("bicicleta", saveProduct.getName());
                })
                .verifyComplete();

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void Prueba2(){
        when(productRepository.findByCode(999999)).thenReturn(Optional.of(product));

        Product productToSave = Product.builder()
                .price(bd)
                .code(999999)
                .name("bicicleta")
                .category("deportes")
                .build();
        var response = productService.save(productToSave);

        StepVerifier.create(response)
                .expectErrorMatches(throwable ->
                        throwable instanceof ResourceIsAlreadyException &&
                        throwable.getMessage().equals("Error Recurso existente"))
                .verify();

        verify(productRepository, never()).save(any(Product.class));
    }

    private Store convertJSONToObject() throws JsonProcessingException {
        String json = "{\"id\":1,\"title\":\"Fjallraven-FoldsackNo.1Backpack,Fits15Laptops\",\"price\":109.95,\"description\":\"Yourperfectpackforeverydayuseandwalksintheforest.Stashyourlaptop(upto15inches)inthepaddedsleeve,youreveryday\",\"category\":\"men'sclothing\",\"image\":\"https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg\",\"rating\":{\"rate\":3.9,\"count\":120}}";
        ObjectMapper objectMapper = new ObjectMapper();
        //JsonNode jsonNode = objectMapper.readTree(json);
        return objectMapper.readValue(json, Store.class);
    }
}