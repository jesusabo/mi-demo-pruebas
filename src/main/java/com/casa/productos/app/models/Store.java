package com.casa.productos.app.models;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Store {

    public Integer id;
    public String title;
    public Double price;
    public String description;
    public String category;
    public String image;
    public Rating rating;

}
