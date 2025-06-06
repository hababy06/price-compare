package com.example.demo.repository;

import com.example.demo.model.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    // 新增依名稱查詢
    Optional<Store> findByName(String name);
}