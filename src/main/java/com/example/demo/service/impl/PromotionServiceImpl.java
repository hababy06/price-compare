package com.example.demo.service.impl;

import com.example.demo.model.dto.PromotionDTO;
import com.example.demo.model.entity.Promotion;
import com.example.demo.model.entity.Product;
import com.example.demo.model.entity.Store;
import com.example.demo.repository.PromotionRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.StoreRepository;
import com.example.demo.service.PromotionService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StoreRepository storeRepository;

    @Override
    public PromotionDTO create(PromotionDTO dto) {
        Promotion promotion = new Promotion();
        promotion.setType(dto.getType());
        promotion.setFinalPrice(dto.getFinalPrice());
        promotion.setRemark(dto.getRemark());
        promotion.setStartTime(dto.getStartTime());
        promotion.setEndTime(dto.getEndTime());

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Store store = storeRepository.findById(dto.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store not found"));
        promotion.setProduct(product);
        promotion.setStore(store);

        promotion = promotionRepository.save(promotion);
        return mapToDTO(promotion);
    }

    @Override
    public PromotionDTO update(Long id, PromotionDTO dto) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));
        // ...同 create()，根據 dto 更新欄位
        promotion.setType(dto.getType());
        promotion.setFinalPrice(dto.getFinalPrice());
        promotion.setRemark(dto.getRemark());
        promotion.setStartTime(dto.getStartTime());
        promotion.setEndTime(dto.getEndTime());

        if (dto.getProductId() != null) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            promotion.setProduct(product);
        }
        if (dto.getStoreId() != null) {
            Store store = storeRepository.findById(dto.getStoreId())
                    .orElseThrow(() -> new RuntimeException("Store not found"));
            promotion.setStore(store);
        }

        promotion = promotionRepository.save(promotion);
        return mapToDTO(promotion);
    }

    @Override
    public void delete(Long id) {
        promotionRepository.deleteById(id);
    }

    @Override
    public PromotionDTO findById(Long id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));
        return mapToDTO(promotion);
    }

    @Override
    public List<PromotionDTO> findAll() {
        return promotionRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PromotionDTO> findByProductId(Long productId) {
        return promotionRepository.findActiveByProductId(productId, java.time.LocalDateTime.now()).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PromotionDTO> findByStoreId(Long storeId) {
        // 你可以根據實際需求實作
        return promotionRepository.findAll().stream()
                .filter(p -> p.getStore().getId().equals(storeId))
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private PromotionDTO mapToDTO(Promotion entity) {
        PromotionDTO dto = new PromotionDTO();
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setFinalPrice(entity.getFinalPrice());
        dto.setRemark(entity.getRemark());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setProductId(entity.getProduct() != null ? entity.getProduct().getId() : null);
        dto.setProductName(entity.getProduct() != null ? entity.getProduct().getName() : null);
        dto.setStoreId(entity.getStore() != null ? entity.getStore().getId() : null);
        dto.setStoreName(entity.getStore() != null ? entity.getStore().getName() : null);
        return dto;
    }
}
