package com.example.demo.service;

import com.example.demo.model.dto.PriceInfoDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceInfoService {
    PriceInfoDTO create(PriceInfoDTO dto);
    PriceInfoDTO update(Long id, PriceInfoDTO dto);
    void delete(Long id);
    PriceInfoDTO findById(Long id);
    List<PriceInfoDTO> findAll();
    List<PriceInfoDTO> findByProductId(Long productId);
    List<PriceInfoDTO> findByStoreId(Long storeId);
    List<PriceInfoDTO> findByProductIdAndDateRange(Long productId, LocalDateTime start, LocalDateTime end);
}