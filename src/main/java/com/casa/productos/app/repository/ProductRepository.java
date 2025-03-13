package com.casa.productos.app.repository;

import com.casa.productos.app.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> findByCode(int code);

    Void deleteByCode(int code);
}
