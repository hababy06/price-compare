package com.example.demo.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "商店 DTO")
public class StoreDTO {
    @Schema(description = "商店ID", example = "1")
    private Long id;

    @Schema(description = "商店名稱", example = "全聯福利中心")
    private String name;

    @Schema(description = "商店地址", example = "台北市中正區重慶南路一段122號")
    private String address;
}
