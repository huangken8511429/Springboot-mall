package com.keer.springbootmall.dao;

import com.keer.springbootmall.constant.ProductCategory;
import com.keer.springbootmall.dto.ProductRequest;
import com.keer.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getProducts(ProductCategory category,String search);
    Product getById (Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

     void deleteProductById(Integer productId);
}
