package com.example.demo.controller;

import com.example.demo.model.entity.PriceInfo;
import com.example.demo.model.entity.Product;
import com.example.demo.model.entity.Store;
import com.example.demo.repository.PriceInfoRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/import")
public class CsvImportController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private PriceInfoRepository priceInfoRepository;

    @PostMapping("/price-csv")
    public ResponseEntity<String> importPriceCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("未選擇檔案");
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            boolean isHeader = true;
            int count = 0;
            while ((line = br.readLine()) != null) {
                if (isHeader) { isHeader = false; continue; }
                String[] arr = line.split(",");

                if (arr.length < 5) continue; // store, barcode, product, price, dateTime

                String storeName = arr[0].trim();
                String barcode = arr[1].trim();
                String productName = arr[2].trim();
                BigDecimal price = new BigDecimal(arr[3].trim());
                LocalDateTime dateTime = LocalDateTime.parse(arr[4].trim());

                // 找或新增商品
                Product product = productRepository.findByBarcode(barcode)
                        .orElseGet(() -> productRepository.save(new Product(barcode, productName, "")));

                // 找或新增商店
                Store store = storeRepository.findByName(storeName)
                        .orElseGet(() -> storeRepository.save(new Store(storeName)));

                // 新增價格資訊
                PriceInfo info = new PriceInfo();
                info.setProduct(product);
                info.setStore(store);
                info.setPrice(price);
                info.setDateTime(dateTime);
                priceInfoRepository.save(info);
                count++;
            }
            return ResponseEntity.ok("匯入完成，共新增 " + count + " 筆資料");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("匯入失敗：" + e.getMessage());
        }
    }
}