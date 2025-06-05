package com.example.demo.model.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String barcode;
    private String name;
    private String description;
    private String imageUrl;
}