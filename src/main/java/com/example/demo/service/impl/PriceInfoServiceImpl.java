package com.example.demo.service.impl;

import com.example.demo.model.dto.PriceInfoDTO;
import com.example.demo.model.entity.PriceInfo;
import com.example.demo.model.entity.Product;
import com.example.demo.model.entity.Store;
import com.example.demo.repository.PriceInfoRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.StoreRepository;
import com.example.demo.service.PriceInfoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriceInfoServiceImpl implements PriceInfoService {

    private final PriceInfoRepository priceInfoRepository;
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final ModelMapper modelMapper;

    public PriceInfoServiceImpl(
            PriceInfoRepository priceInfoRepository,
            ProductRepository productRepository,
            StoreRepository storeRepository,
            ModelMapper modelMapper
    ) {
        this.priceInfoRepository = priceInfoRepository;
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
        this.modelMapper = modelMapper;
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

        return modelMapper.map(priceInfoRepository.save(priceInfo), PriceInfoDTO.class);
    }

    @Override
    public PriceInfoDTO update(Long id, PriceInfoDTO dto) {
        PriceInfo priceInfo = priceInfoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PriceInfo not found"));

        if (dto.getProductId() != null) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            priceInfo.setProduct(product);
        }
        if (dto.getStoreId() != null) {
            Store store = storeRepository.findById(dto.getStoreId())
                    .orElseThrow(() -> new RuntimeException("Store not found"));
            priceInfo.setStore(store);
        }
        if (dto.getPrice() != null) {
            priceInfo.setPrice(dto.getPrice());
        }
        if (dto.getDateTime() != null) {
            priceInfo.setDateTime(dto.getDateTime());
        }

        return modelMapper.map(priceInfoRepository.save(priceInfo), PriceInfoDTO.class);
    }

    @Override
    public void delete(Long id) {
        priceInfoRepository.deleteById(id);
    }

    @Override
    public PriceInfoDTO findById(Long id) {
        PriceInfo priceInfo = priceInfoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PriceInfo not found"));
        return modelMapper.map(priceInfo, PriceInfoDTO.class);
    }

    @Override
    public List<PriceInfoDTO> findAll() {
        return priceInfoRepository.findAll().stream()
                .map(entity -> modelMapper.map(entity, PriceInfoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PriceInfoDTO> findByProductId(Long productId) {
        return priceInfoRepository.findByProductId(productId).stream()
                .map(entity -> modelMapper.map(entity, PriceInfoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PriceInfoDTO> findByStoreId(Long storeId) {
        return priceInfoRepository.findByStoreId(storeId).stream()
                .map(entity -> modelMapper.map(entity, PriceInfoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PriceInfoDTO> findByProductIdAndDateRange(Long productId, LocalDateTime start, LocalDateTime end) {
        return priceInfoRepository.findByProductIdAndDateTimeBetween(productId, start, end).stream()
                .map(entity -> modelMapper.map(entity, PriceInfoDTO.class))
                .collect(Collectors.toList());
    }
}