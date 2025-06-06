package com.example.demo.controller;

import com.example.demo.model.dto.PriceInfoDTO;
import com.example.demo.service.PriceInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/price-infos")
@Tag(name = "價格資訊", description = "價格資訊相關 API")
public class PriceInfoController {
    private final PriceInfoService priceInfoService;

    public PriceInfoController(PriceInfoService priceInfoService) {
        this.priceInfoService = priceInfoService;
    }

    @PostMapping
    @Operation(summary = "新增價格資訊", description = "建立一筆新的價格資訊紀錄")
    public PriceInfoDTO create(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "要新增的價格資訊") 
        @RequestBody PriceInfoDTO dto) {
        return priceInfoService.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新價格資訊", description = "根據 ID 更新價格資訊")
    public PriceInfoDTO update(
        @Parameter(description = "價格資訊ID", example = "1") @PathVariable Long id, 
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "要更新的價格資訊") 
        @RequestBody PriceInfoDTO dto) {
        return priceInfoService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "刪除價格資訊", description = "根據 ID 刪除價格資訊")
    public void delete(@Parameter(description = "價格資訊ID", example = "1") @PathVariable Long id) {
        priceInfoService.delete(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "查詢單一價格資訊", description = "根據 ID 查詢價格資訊")
    public PriceInfoDTO findById(@Parameter(description = "價格資訊ID", example = "1") @PathVariable Long id) {
        return priceInfoService.findById(id);
    }

    @GetMapping
    @Operation(summary = "查詢所有價格資訊", description = "取得所有價格資訊列表")
    public List<PriceInfoDTO> findAll() {
        return priceInfoService.findAll();
    }
    
    @GetMapping("/product/{productId}")
    @Operation(summary = "查詢某商品的所有價格資訊", description = "根據商品ID查詢所有價格資訊")
    public List<PriceInfoDTO> getPriceInfosByProduct(
        @Parameter(description = "商品ID", example = "10") @PathVariable Long productId) {
        return priceInfoService.findByProductId(productId);
    }

    @GetMapping("/store/{storeId}")
    @Operation(summary = "查詢某商店的所有價格資訊", description = "根據商店ID查詢所有價格資訊")
    public List<PriceInfoDTO> getPriceInfosByStore(
        @Parameter(description = "商店ID", example = "5") @PathVariable Long storeId) {
        return priceInfoService.findByStoreId(storeId);
    }

    @GetMapping("/product/{productId}/history")
    @Operation(summary = "查詢某商品一段時間內的價格資訊", description = "根據商品ID與時間範圍查詢價格資訊")
    public List<PriceInfoDTO> getPriceInfosByProductAndDateRange(
        @Parameter(description = "商品ID", example = "10") @PathVariable Long productId,
        @Parameter(description = "開始時間 (格式: yyyy-MM-dd'T'HH:mm:ss)", example = "2023-01-01T00:00:00") 
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
        @Parameter(description = "結束時間 (格式: yyyy-MM-dd'T'HH:mm:ss)", example = "2023-12-31T23:59:59") 
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return priceInfoService.findByProductIdAndDateRange(productId, start, end);
    }
}