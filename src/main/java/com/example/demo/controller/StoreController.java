package com.example.demo.controller;

import com.example.demo.model.dto.StoreDTO;
import com.example.demo.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
@Tag(name = "商店管理", description = "商店相關 API")
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    @Operation(summary = "新增商店", description = "建立一筆新的商店資料")
    public StoreDTO create(
            @RequestBody(description = "要新增的商店資料") @org.springframework.web.bind.annotation.RequestBody StoreDTO dto) {
        return storeService.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新商店", description = "根據商店ID更新商店資料")
    public StoreDTO update(
            @Parameter(description = "商店ID", example = "1") @PathVariable Long id,
            @RequestBody(description = "要更新的商店資料") @org.springframework.web.bind.annotation.RequestBody StoreDTO dto) {
        return storeService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "刪除商店", description = "根據商店ID刪除商店")
    public void delete(@Parameter(description = "商店ID", example = "1") @PathVariable Long id) {
        storeService.delete(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "查詢單一商店", description = "根據商店ID查詢商店詳細資料")
    public StoreDTO findById(@Parameter(description = "商店ID", example = "1") @PathVariable Long id) {
        return storeService.findById(id);
    }

    @GetMapping
    @Operation(summary = "查詢所有商店", description = "取得所有商店列表")
    public List<StoreDTO> findAll() {
        return storeService.findAll();
    }
}