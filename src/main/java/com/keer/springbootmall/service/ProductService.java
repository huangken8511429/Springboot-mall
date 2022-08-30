package com.keer.springbootmall.service;

import com.keer.springbootmall.constant.CategoryParam;
import com.keer.springbootmall.constant.ProductCategory;
import com.keer.springbootmall.dto.ProductRequest;
import com.keer.springbootmall.model.Product;

import java.util.List;

public interface ProductService {
    Integer countProduct(CategoryParam categoryParam);
    List<Product> getProducts(CategoryParam categoryParam);
    Product getProductbyId(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId,ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
