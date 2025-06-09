package com.example.demo.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PromotionDTO {
    @Schema(description = "優惠ID")
    private Long id;

    @Schema(description = "商家ID")
    private Long storeId;
    @Schema(description = "商家名稱（可選）")
    private String storeName;

    @Schema(description = "商品ID")
    private Long productId;
    @Schema(description = "商品名稱（可選）")
    private String productName;

    @Schema(description = "優惠類型（如：PERCENTAGE, FIXED_AMOUNT, BUY_ONE_GET_ONE, FREE_SHIPPING, OTHER 等）")
    private String type;

    @Schema(description = "優惠後單個價格（僅常見類型自動計算，其他類型為 null）")
    private BigDecimal finalPrice;

    @Schema(description = "備註（type=OTHER 時必填，其餘可選填）")
    private String remark;

    @Schema(description = "優惠起始時間")
    private LocalDateTime startTime;

    @Schema(description = "優惠結束時間")
    private LocalDateTime endTime;
}
