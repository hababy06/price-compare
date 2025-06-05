package com.example.demo.controller;

import com.example.demo.model.dto.StoreDTO;
import com.example.demo.service.StoreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    public StoreDTO create(@RequestBody StoreDTO dto) {
        return storeService.create(dto);
    }

    @PutMapping("/{id}")
    public StoreDTO update(@PathVariable Long id, @RequestBody StoreDTO dto) {
        return storeService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        storeService.delete(id);
    }

    @GetMapping("/{id}")
    public StoreDTO findById(@PathVariable Long id) {
        return storeService.findById(id);
    }

    @GetMapping
    public List<StoreDTO> findAll() {
        return storeService.findAll();
    }
}