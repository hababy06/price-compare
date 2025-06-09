package com.example.demo.repository;

import com.example.demo.model.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.time.LocalDateTime;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    // 查詢指定商品、商家在指定時間內有效的優惠
    @Query("SELECT p FROM Promotion p WHERE p.product.id = :productId AND p.store.id = :storeId AND p.startTime <= :now AND p.endTime >= :now")
    List<Promotion> findActiveByProductIdAndStoreId(
        @Param("productId") Long productId,
        @Param("storeId") Long storeId,
        @Param("now") LocalDateTime now
    );

    // 查詢指定商品在指定時間內有效的優惠（不限商家）
    @Query("SELECT p FROM Promotion p WHERE p.product.id = :productId AND p.startTime <= :now AND p.endTime >= :now")
    List<Promotion> findActiveByProductId(
        @Param("productId") Long productId,
        @Param("now") LocalDateTime now
    );

    // 批次查詢多個商品與多個商家在指定時間內有效的優惠
    @Query("SELECT p FROM Promotion p WHERE p.product.id IN :productIds AND p.store.id IN :storeIds AND p.startTime <= :now AND p.endTime >= :now")
    List<Promotion> findActiveByProductIdsAndStoreIds(
        @Param("productIds") List<Long> productIds,
        @Param("storeIds") List<Long> storeIds,
        @Param("now") LocalDateTime now
    );
}
