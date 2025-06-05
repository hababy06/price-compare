package com.example.demo.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "商品 DTO")
public class ProductDTO {
    @Schema(description = "商品ID", example = "1")
    private Long id;

    @Schema(description = "商品條碼", example = "4712345678901")
    private String barcode;

    @Schema(description = "商品名稱", example = "蘋果手機")
    private String name;

    @Schema(description = "商品描述", example = "最新款智慧型手機")
    private String description;

    @Schema(description = "商品圖片網址", example = "https://example.com/product/image.jpg")
    private String imageUrl;
}