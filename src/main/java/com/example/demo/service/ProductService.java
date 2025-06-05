package com.example.demo.service;

import com.example.demo.model.dto.ProductDTO;
import org.springframework.data.domain.Page;  // <--- 補這行
import java.util.List;

public interface ProductService {
    List<ProductDTO> findAll();
    ProductDTO findById(Long id);
    ProductDTO create(ProductDTO productDTO);
    ProductDTO update(Long id, ProductDTO productDTO);
    void delete(Long id);
    Page<ProductDTO> searchProducts(String keyword, int page, int size);
}