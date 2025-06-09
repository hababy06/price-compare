package com.example.demo.controller;

import com.example.demo.model.dto.PromotionDTO;
import com.example.demo.service.PromotionService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promotions")
@Tag(name = "優惠管理", description = "優惠相關 API")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    // 新增優惠
    @PostMapping
    public ResponseEntity<PromotionDTO> createPromotion(@RequestBody PromotionDTO promotionDTO) {
        PromotionDTO created = promotionService.create(promotionDTO);
        return ResponseEntity.ok(created);
    }

    // 修改優惠
    @PutMapping("/{id}")
    public ResponseEntity<PromotionDTO> updatePromotion(@PathVariable Long id, @RequestBody PromotionDTO promotionDTO) {
        PromotionDTO updated = promotionService.update(id, promotionDTO);
        return ResponseEntity.ok(updated);
    }

    // 刪除優惠
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromotion(@PathVariable Long id) {
        promotionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 查詢單一優惠
    @GetMapping("/{id}")
    public ResponseEntity<PromotionDTO> getPromotionById(@PathVariable Long id) {
        PromotionDTO dto = promotionService.findById(id);
        return ResponseEntity.ok(dto);
    }

    // 查詢全部優惠
    @GetMapping
    public ResponseEntity<List<PromotionDTO>> getAllPromotions() {
        List<PromotionDTO> list = promotionService.findAll();
        return ResponseEntity.ok(list);
    }

    // 查詢某商品的所有優惠
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<PromotionDTO>> getPromotionsByProduct(@PathVariable Long productId) {
        List<PromotionDTO> list = promotionService.findByProductId(productId);
        return ResponseEntity.ok(list);
    }

    // 查詢某商家的所有優惠
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<PromotionDTO>> getPromotionsByStore(@PathVariable Long storeId) {
        List<PromotionDTO> list = promotionService.findByStoreId(storeId);
        return ResponseEntity.ok(list);
    }
}
