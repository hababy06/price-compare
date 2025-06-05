package com.example.demo.repository;

import com.example.demo.model.entity.PriceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime; // <--- 要加這行！

import java.util.List;

public interface PriceInfoRepository extends JpaRepository<PriceInfo, Long> {
    List<PriceInfo> findByProductId(Long productId);
    List<PriceInfo> findByStoreId(Long storeId);
    List<PriceInfo> findByProductIdAndDateTimeBetween(Long productId, LocalDateTime start, LocalDateTime end);
}