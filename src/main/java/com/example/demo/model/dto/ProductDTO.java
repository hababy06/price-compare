package com.example.demo.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductDTO {
    private Long id;

    @NotBlank(message = "Barcode 不可為空")
    @Size(max = 30, message = "Barcode 長度不可超過 30 字元")
    private String barcode;

    @NotBlank(message = "商品名稱不可為空")
    @Size(max = 50, message = "商品名稱長度不可超過 50 字元")
    private String name;

    private String description;

    private String imageUrl;
}