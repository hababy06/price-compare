package com.example.demo.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "價格資訊 DTO")
public class PriceInfoDTO {
    @Schema(description = "價格資訊ID", example = "1001")
    private Long id;

    @Schema(description = "商品名稱", example = "可樂")
    private String productName;

    @Schema(description = "商店名稱", example = "7-11")
    private String storeName;

    @Schema(description = "價格", example = "99.99")
    private BigDecimal price;

    @Schema(description = "紀錄時間", example = "2024-06-05T13:00:00")
    private LocalDateTime dateTime;
}