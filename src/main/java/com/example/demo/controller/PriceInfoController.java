package com.example.demo.controller;

import com.example.demo.model.dto.PriceInfoDTO;
import com.example.demo.service.PriceInfoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/price-infos")
public class PriceInfoController {
    private final PriceInfoService priceInfoService;

    public PriceInfoController(PriceInfoService priceInfoService) {
        this.priceInfoService = priceInfoService;
    }

    @PostMapping
    public PriceInfoDTO create(@RequestBody PriceInfoDTO dto) {
        return priceInfoService.create(dto);
    }

    @PutMapping("/{id}")
    public PriceInfoDTO update(@PathVariable Long id, @RequestBody PriceInfoDTO dto) {
        return priceInfoService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        priceInfoService.delete(id);
    }

    @GetMapping("/{id}")
    public PriceInfoDTO findById(@PathVariable Long id) {
        return priceInfoService.findById(id);
    }

    @GetMapping
    public List<PriceInfoDTO> findAll() {
        return priceInfoService.findAll();
    }
    
    @GetMapping("/product/{productId}")
    public List<PriceInfoDTO> getPriceInfosByProduct(@PathVariable Long productId) {
        return priceInfoService.findByProductId(productId);
    }

    @GetMapping("/store/{storeId}")
    public List<PriceInfoDTO> getPriceInfosByStore(@PathVariable Long storeId) {
        return priceInfoService.findByStoreId(storeId);
    }

    @GetMapping("/product/{productId}/history")
    public List<PriceInfoDTO> getPriceInfosByProductAndDateRange(
            @PathVariable Long productId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return priceInfoService.findByProductIdAndDateRange(productId, start, end);
    }
}