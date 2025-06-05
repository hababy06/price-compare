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

    @Schema(description = "商品ID", example = "10")
    private Long productId;

    @Schema(description = "商店ID", example = "5")
    private Long storeId;

    @Schema(description = "價格", example = "99.99")
    private BigDecimal price;

    @Schema(description = "紀錄時間", example = "2024-06-05T13:00:00")
    private LocalDateTime dateTime;
}