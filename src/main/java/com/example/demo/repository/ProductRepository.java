package com.example.demo.repository;

import com.example.demo.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByBarcode(String barcode);
    boolean existsByBarcode(String barcode);
    Page<Product> findByNameContainingOrDescriptionContaining(String name, String description, Pageable pageable);
}