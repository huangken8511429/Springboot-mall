package com.keer.springbootmall.controller;

import com.keer.springbootmall.dto.ProductRequest;
import com.keer.springbootmall.model.Product;
import com.keer.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId) {
        Product product = productService.getProductbyId(productId);

        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    //@Valid 是驗證請求參數,因為我們在ProductRequest有寫上Not Null，所以在這邊設定參數時要記得加上才會生效
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {
         Integer productId =  productService.createProduct(productRequest);

         Product product =productService.getProductbyId(productId);

         return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
}
