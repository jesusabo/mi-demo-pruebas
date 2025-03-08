package com.casa.productos.app.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("products")
@Builder
public class Product {

    @Id
    private String id;

    private int code;

    private String name;

    private BigDecimal price;

    private String category;
}
