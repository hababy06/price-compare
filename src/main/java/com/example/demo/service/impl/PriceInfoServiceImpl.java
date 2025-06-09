package com.example.demo.service.impl;

import com.example.demo.model.dto.PriceInfoDTO;
import com.example.demo.model.entity.PriceInfo;
import com.example.demo.model.entity.Product;
import com.example.demo.model.entity.Store;
import com.example.demo.model.entity.Promotion;
import com.example.demo.repository.PriceInfoRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.StoreRepository;
import com.example.demo.repository.PromotionRepository;
import com.example.demo.service.PriceInfoService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PriceInfoServiceImpl implements PriceInfoService {

    private final PriceInfoRepository priceInfoRepository;
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    @Autowired
    private PromotionRepository promotionRepository;

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

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Store store = storeRepository.findById(dto.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store not found"));

        priceInfo.setProduct(product);
        priceInfo.setStore(store);
        priceInfo.setPrice(dto.getPrice());
        priceInfo.setDateTime(dto.getDateTime());

        priceInfo = priceInfoRepository.save(priceInfo);

        return mapToDTO(priceInfo, null);
    }

    @Override
    public PriceInfoDTO update(Long id, PriceInfoDTO dto) {
        PriceInfo priceInfo = priceInfoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PriceInfo not found"));

        if (dto.getProductName() != null) {
            Product product = productRepository.findByName(dto.getProductName())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            priceInfo.setProduct(product);
        }
        if (dto.getStoreName() != null) {
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
        return mapToDTO(priceInfo, null);
    }

    @Override
    public void delete(Long id) {
        priceInfoRepository.deleteById(id);
    }

    @Override
    public PriceInfoDTO findById(Long id) {
        PriceInfo priceInfo = priceInfoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PriceInfo not found"));
        return mapToDTO(priceInfo, null);
    }

    @Override
    public List<PriceInfoDTO> findAll() {
        return priceInfoRepository.findAll().stream()
                .map(pi -> mapToDTO(pi, null))
                .collect(Collectors.toList());
    }

    @Override
    public List<PriceInfoDTO> findByProductId(Long productId) {
        return priceInfoRepository.findByProductId(productId).stream()
                .map(pi -> mapToDTO(pi, null))
                .collect(Collectors.toList());
    }

    @Override
    public List<PriceInfoDTO> findByStoreId(Long storeId) {
        return priceInfoRepository.findByStoreId(storeId).stream()
                .map(pi -> mapToDTO(pi, null))
                .collect(Collectors.toList());
    }

    @Override
    public List<PriceInfoDTO> findByProductIdAndDateRange(Long productId, LocalDateTime start, LocalDateTime end) {
        return priceInfoRepository.findByProductIdAndDateTimeBetween(productId, start, end).stream()
                .map(pi -> mapToDTO(pi, null))
                .collect(Collectors.toList());
    }

    /**
     * 比價核心：依據商品名稱搜尋所有價格，將有自動計算優惠的（finalPrice 不為 null 且 type ≠ OTHER）優先排序，
     * 其次為有特殊優惠但無法自動計算，最後為沒有優惠的。
     */
    @Override
    public List<PriceInfoDTO> searchByProductNameOrderByPromotionAndPrice(String productName) {
        List<PriceInfo> priceInfos = priceInfoRepository.findByProduct_NameContaining(productName);
        LocalDateTime now = LocalDateTime.now();

        List<Long> productIds = priceInfos.stream()
                .map(pi -> pi.getProduct().getId())
                .distinct()
                .collect(Collectors.toList());
        List<Long> storeIds = priceInfos.stream()
                .map(pi -> pi.getStore().getId())
                .distinct()
                .collect(Collectors.toList());

        List<Promotion> promotions = promotionRepository.findActiveByProductIdsAndStoreIds(productIds, storeIds, now);

        // 建立 Promotion Map
        Map<String, Promotion> promoMap = promotions.stream()
                .collect(Collectors.toMap(
                        p -> p.getStore().getId() + "_" + p.getProduct().getId(),
                        p -> p,
                        (p1, p2) -> {
                            // 若有多個優惠，選 finalPrice 較低者
                            if (p1.getFinalPrice() != null && p2.getFinalPrice() != null) {
                                return p1.getFinalPrice().compareTo(p2.getFinalPrice()) <= 0 ? p1 : p2;
                            }
                            return p1;
                        }
                ));

        List<PriceInfoDTO> withAutoPromo = new ArrayList<>();
        List<PriceInfoDTO> withOtherPromo = new ArrayList<>();
        List<PriceInfoDTO> noPromo = new ArrayList<>();

        for (PriceInfo pi : priceInfos) {
            String key = pi.getStore().getId() + "_" + pi.getProduct().getId();
            Promotion promo = promoMap.get(key);

            if (promo != null) {
                if (!"OTHER".equalsIgnoreCase(promo.getType()) && promo.getFinalPrice() != null) {
                    // 有自動計算優惠
                    withAutoPromo.add(mapToDTO(pi, promo));
                } else {
                    // 有特殊優惠但無法自動計算
                    withOtherPromo.add(mapToDTO(pi, promo));
                }
            } else {
                // 沒有優惠
                noPromo.add(mapToDTO(pi, null));
            }
        }

        // 排序：先優惠後價格低到高，再特殊優惠，再原價
        withAutoPromo.sort(Comparator.comparing(dto -> dto.getFinalPrice() != null ? dto.getFinalPrice() : dto.getPrice()));
        // withOtherPromo 可依需求排序
        noPromo.sort(Comparator.comparing(PriceInfoDTO::getPrice));

        List<PriceInfoDTO> result = new ArrayList<>();
        result.addAll(withAutoPromo);
        result.addAll(withOtherPromo);
        result.addAll(noPromo);

        return result;
    }

    /**
     * 支援 Promotion 的 DTO 轉換
     */
    private PriceInfoDTO mapToDTO(PriceInfo entity, Promotion promo) {
        PriceInfoDTO dto = new PriceInfoDTO();
        dto.setId(entity.getId());
        dto.setProductId(entity.getProduct() != null ? entity.getProduct().getId() : null);
        dto.setProductName(entity.getProduct() != null ? entity.getProduct().getName() : null);
        dto.setStoreId(entity.getStore() != null ? entity.getStore().getId() : null);
        dto.setStoreName(entity.getStore() != null ? entity.getStore().getName() : null);
        dto.setPrice(entity.getPrice());
        dto.setDateTime(entity.getDateTime());
        if (promo != null) {
            dto.setPromotionType(promo.getType());
            dto.setPromotionRemark(promo.getRemark());
            dto.setFinalPrice(promo.getFinalPrice());
        }
        return dto;
    }

    @Override
    public List<PriceInfoDTO> searchByProductNameOrderByPriceAsc(String productName) {
        List<PriceInfo> infos = priceInfoRepository.findByProductNameLikeOrderByPriceAsc(productName);
        return infos.stream().map(pi -> mapToDTO(pi, null)).collect(Collectors.toList());
    }
}
