package com.keer.springbootmall.controller;

import com.keer.springbootmall.constant.CategoryParam;
import com.keer.springbootmall.constant.ProductCategory;
import com.keer.springbootmall.dto.ProductRequest;
import com.keer.springbootmall.model.Product;
import com.keer.springbootmall.service.ProductService;
import com.keer.springbootmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated //對應@MAX,@Min
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/products")  //在Restful裡就表示要查詢的是商品列表
    public ResponseEntity<Page<Product>> getProducts
        //查詢條件
    (@RequestParam(required = false) ProductCategory category,
     @RequestParam(required = false) String search,
     //排序功能 Sorting
     @RequestParam(defaultValue = "created_date") String orderBy,
     @RequestParam(defaultValue = "desc") String sort, //預設 desc 降序: 商品大到小的排序
     //分頁 Pagination
     @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,  //一次限制只有幾筆資料出來
     @RequestParam(defaultValue = "0") @Min(0) Integer offset  //跳過幾筆數據
    ) {
        CategoryParam categoryParam = new CategoryParam();

        categoryParam.setCategory(category);
        categoryParam.setSearch(search);
        categoryParam.setOrderBy(orderBy);
        categoryParam.setSort(sort);
        categoryParam.setLimit(limit);
        categoryParam.setOffset(offset);
        //get productlist
        List<Product> productList = productService.getProducts(categoryParam);
        //get商品總數
        Integer total = productService.countProduct(categoryParam);
        //分頁 轉成Json
        Page<Product> page = new Page<>();

        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(productList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

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
        Integer productId = productService.createProduct(productRequest);

        Product product = productService.getProductbyId(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest) {
        //先檢查該Product的id是否存在
        Product product = productService.getProductbyId(productId);
        //有存在，再修改商品數據
        if (product == null) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        } else {
            productService.updateProduct(productId, productRequest);
            Product updateProduct = productService.getProductbyId(productId);

            return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
        }
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProductById(@PathVariable Integer productId) {
        productService.deleteProductById(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
