package com.example.demo.controller;

import com.example.demo.model.dto.ProductDTO;
import com.example.demo.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "商品管理", description = "商品相關 API")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    @Operation(summary = "取得所有商品", description = "查詢所有商品資料")
    public List<ProductDTO> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "取得單一商品", description = "根據商品ID查詢商品詳細資料")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "查詢成功"),
        @ApiResponse(responseCode = "404", description = "找不到此商品")
    })
    public ProductDTO getProductById(
            @Parameter(description = "商品ID", example = "1") @PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping
    @Operation(summary = "新增商品", description = "建立一筆新的商品資料")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "新增成功"),
        @ApiResponse(responseCode = "400", description = "參數錯誤")
    })
    public ResponseEntity<ProductDTO> createProduct(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "商品資料") 
            @RequestBody @Valid ProductDTO productDTO) {
        return ResponseEntity.ok(productService.create(productDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新商品", description = "根據商品ID更新商品資料")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "更新成功"),
        @ApiResponse(responseCode = "404", description = "找不到此商品")
    })
    public ResponseEntity<ProductDTO> updateProduct(
            @Parameter(description = "商品ID", example = "1") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "商品資料") 
            @RequestBody @Valid ProductDTO productDTO) {
        return ResponseEntity.ok(productService.update(id, productDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "刪除商品", description = "根據商品ID刪除商品")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "刪除成功"),
        @ApiResponse(responseCode = "404", description = "找不到此商品")
    })
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "商品ID", example = "1") @PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    @Operation(summary = "搜尋商品", description = "根據關鍵字分頁查詢商品")
    public Page<ProductDTO> searchProducts(
            @Parameter(description = "關鍵字", example = "iPhone") @RequestParam String keyword,
            @Parameter(description = "頁數(預設0)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "每頁筆數(預設10)", example = "10") @RequestParam(defaultValue = "10") int size) {
        return productService.searchProducts(keyword, page, size);
    }
}