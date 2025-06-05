package com.example.demo.service;

import com.example.demo.model.dto.StoreDTO;

import java.util.List;

public interface StoreService {
    StoreDTO create(StoreDTO dto);
    StoreDTO update(Long id, StoreDTO dto);
    void delete(Long id);
    StoreDTO findById(Long id);
    List<StoreDTO> findAll();
}