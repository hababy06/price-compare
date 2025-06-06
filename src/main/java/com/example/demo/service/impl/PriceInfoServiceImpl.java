package com.example.demo.service.impl;

import com.example.demo.model.dto.PriceInfoDTO;
import com.example.demo.model.entity.PriceInfo;
import com.example.demo.model.entity.Product;
import com.example.demo.model.entity.Store;
import com.example.demo.repository.PriceInfoRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.StoreRepository;
import com.example.demo.service.PriceInfoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriceInfoServiceImpl implements PriceInfoService {

    private final PriceInfoRepository priceInfoRepository;
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    public PriceInfoServiceImpl(
            PriceInfoRepository priceInfoRepository,
            ProductRepository productRepository,
            StoreRepository storeRepository
    ) {
        this.priceInfoRepository = priceInfoRepository;
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
    }

    @Override
    public PriceInfoDTO create(PriceInfoDTO dto) {
        PriceInfo priceInfo = new PriceInfo();

        Product product = productRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Store store = storeRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Store not found"));

        priceInfo.setProduct(product);
        priceInfo.setStore(store);
        priceInfo.setPrice(dto.getPrice());
        priceInfo.setDateTime(dto.getDateTime());

        priceInfo = priceInfoRepository.save(priceInfo);

        return mapToDTO(priceInfo);
    }

    @Override
    public PriceInfoDTO update(Long id, PriceInfoDTO dto) {
        PriceInfo priceInfo = priceInfoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PriceInfo not found"));

        if (dto.getProductName() != null) {
            // 依照商品名稱查找
            Product product = productRepository.findByName(dto.getProductName())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            priceInfo.setProduct(product);
        }
        if (dto.getStoreName() != null) {
            // 依照商店名稱查找
            Store store = storeRepository.findByName(dto.getStoreName())
                    .orElseThrow(() -> new RuntimeException("Store not found"));
            priceInfo.setStore(store);
        }
        if (dto.getPrice() != null) {
            priceInfo.setPrice(dto.getPrice());
        }
        if (dto.getDateTime() != null) {
            priceInfo.setDateTime(dto.getDateTime());
        }

        priceInfo = priceInfoRepository.save(priceInfo);
        return mapToDTO(priceInfo);
    }

    @Override
    public void delete(Long id) {
        priceInfoRepository.deleteById(id);
    }

    @Override
    public PriceInfoDTO findById(Long id) {
        PriceInfo priceInfo = priceInfoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PriceInfo not found"));
        return mapToDTO(priceInfo);
    }

    @Override
    public List<PriceInfoDTO> findAll() {
        return priceInfoRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PriceInfoDTO> findByProductId(Long productId) {
        return priceInfoRepository.findByProductId(productId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PriceInfoDTO> findByStoreId(Long storeId) {
        return priceInfoRepository.findByStoreId(storeId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PriceInfoDTO> findByProductIdAndDateRange(Long productId, LocalDateTime start, LocalDateTime end) {
        return priceInfoRepository.findByProductIdAndDateTimeBetween(productId, start, end).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * 將 PriceInfo entity 轉換為 PriceInfoDTO，帶出商品名稱與商店名稱
     */
    private PriceInfoDTO mapToDTO(PriceInfo priceInfo) {
        PriceInfoDTO dto = new PriceInfoDTO();
        dto.setId(priceInfo.getId());
        dto.setProductName(priceInfo.getProduct() != null ? priceInfo.getProduct().getName() : null);
        dto.setStoreName(priceInfo.getStore() != null ? priceInfo.getStore().getName() : null);
        dto.setPrice(priceInfo.getPrice());
        dto.setDateTime(priceInfo.getDateTime());
        return dto;
    }
}