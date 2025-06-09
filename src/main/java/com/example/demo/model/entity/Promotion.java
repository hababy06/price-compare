package com.example.demo.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Promotion {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Store store;

    @ManyToOne
    private Product product;

    // 優惠類型（如：PERCENTAGE、FIXED_AMOUNT、THRESHOLD、BUY_ONE_GET_ONE、FREE_SHIPPING、OTHER）
    private String type;

    // 優惠後單個價格（只有常見類型才自動計算，其他類型為 null）
    private BigDecimal finalPrice;

    // 備註（type=OTHER 時必填，其餘可選填）
    private String remark;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
