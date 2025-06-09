package com.example.demo.service;

import com.example.demo.model.dto.PromotionDTO;
import java.util.List;

public interface PromotionService {
    PromotionDTO create(PromotionDTO dto);
    PromotionDTO update(Long id, PromotionDTO dto);
    void delete(Long id);
    PromotionDTO findById(Long id);
    List<PromotionDTO> findAll();
    List<PromotionDTO> findByProductId(Long productId);
    List<PromotionDTO> findByStoreId(Long storeId);
}
