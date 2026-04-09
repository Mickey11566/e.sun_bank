package com.bank.backend.controller;

import com.bank.backend.dto.ProductDto;
import com.bank.backend.service.PreferenceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 金融商品主檔控制器
 * 負責維護銀行提供的手續費率與商品價格
 */
@CrossOrigin
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private PreferenceService preferenceService;

    /**
     * 取得所有商品清單
     */
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(preferenceService.getAllProducts());
    }

    /**
     * 新增商品
     */
    @PostMapping
    public ResponseEntity<String> addProduct(@Valid @RequestBody ProductDto productDto) {
        preferenceService.addProduct(
            productDto.getProductName(), 
            productDto.getPrice(), 
            productDto.getFeeRate()
        );
        return ResponseEntity.ok("新增產品成功");
    }

    /**
     * 更新商品
     */
    @PutMapping
    public ResponseEntity<String> updateProduct(@Valid @RequestBody ProductDto productDto) {
        preferenceService.updateProduct(
            productDto.getNo(), 
            productDto.getProductName(), 
            productDto.getPrice(), 
            productDto.getFeeRate()
        );
        return ResponseEntity.ok("產品更新成功");
    }

    /**
     * 刪除商品
     */
    @DeleteMapping("/{no}")
    public ResponseEntity<String> deleteProduct(@PathVariable("no") int no) {
        preferenceService.deleteProduct(no);
        return ResponseEntity.ok("產品刪除成功");
    }
}
