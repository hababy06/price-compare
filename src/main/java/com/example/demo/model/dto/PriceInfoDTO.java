package com.example.demo.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PriceInfoDTO {
    private Long id;
    private Long productId;
    private Long storeId;
    private BigDecimal price;
    private LocalDateTime dateTime;
}