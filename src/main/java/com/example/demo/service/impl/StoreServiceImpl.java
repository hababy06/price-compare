package com.example.demo.service.impl;

import com.example.demo.model.dto.StoreDTO;
import com.example.demo.model.entity.Store;
import com.example.demo.repository.StoreRepository;
import com.example.demo.service.StoreService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final ModelMapper modelMapper;

    public StoreServiceImpl(StoreRepository storeRepository, ModelMapper modelMapper) {
        this.storeRepository = storeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public StoreDTO create(StoreDTO dto) {
        Store store = modelMapper.map(dto, Store.class);
        return modelMapper.map(storeRepository.save(store), StoreDTO.class);
    }

    @Override
    public StoreDTO update(Long id, StoreDTO dto) {
        Store store = storeRepository.findById(id).orElseThrow();
        store.setName(dto.getName());
        store.setAddress(dto.getAddress());
        return modelMapper.map(storeRepository.save(store), StoreDTO.class);
    }

    @Override
    public void delete(Long id) {
        storeRepository.deleteById(id);
    }

    @Override
    public StoreDTO findById(Long id) {
        return modelMapper.map(storeRepository.findById(id).orElseThrow(), StoreDTO.class);
    }

    @Override
    public List<StoreDTO> findAll() {
        return storeRepository.findAll().stream()
                .map(store -> modelMapper.map(store, StoreDTO.class))
                .collect(Collectors.toList());
    }
}