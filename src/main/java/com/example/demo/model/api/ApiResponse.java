package com.example.demo.model.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "統一 API 回應格式")
public class ApiResponse<T> {
    @Schema(description = "狀態碼", example = "200")
    private int code;
    @Schema(description = "訊息", example = "成功")
    private String message;
    @Schema(description = "時間戳記", example = "2025-06-05T15:35:00")
    private LocalDateTime timestamp;
    @Schema(description = "回傳資料")
    private T data;
}