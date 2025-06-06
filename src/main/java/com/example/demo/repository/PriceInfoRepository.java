package com.example.demo.repository;

import com.example.demo.model.entity.PriceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime; // <--- 要加這行！

import java.util.List;

public interface PriceInfoRepository extends JpaRepository<PriceInfo, Long> {
    List<PriceInfo> findByProductId(Long productId);
    List<PriceInfo> findByStoreId(Long storeId);
    List<PriceInfo> findByProductIdAndDateTimeBetween(Long productId, LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT p FROM PriceInfo p WHERE p.product.name LIKE %:name% ORDER BY p.price ASC")
    List<PriceInfo> findByProductNameLikeOrderByPriceAsc(@Param("name") String name);
}