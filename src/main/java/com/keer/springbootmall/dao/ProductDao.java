package com.keer.springbootmall.dao;

import com.keer.springbootmall.constant.CategoryParam;
import com.keer.springbootmall.constant.ProductCategory;
import com.keer.springbootmall.dto.ProductRequest;
import com.keer.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {
    Integer countProduct(CategoryParam categoryParam);

    List<Product> getProducts(CategoryParam categoryParam);
    Product getById (Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

     void deleteProductById(Integer productId);
}
