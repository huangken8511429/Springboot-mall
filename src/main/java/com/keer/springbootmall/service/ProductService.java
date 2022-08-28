package com.keer.springbootmall.service;

import com.keer.springbootmall.dto.ProductRequest;
import com.keer.springbootmall.model.Product;

public interface ProductService {

    Product getProductbyId(Integer productId);

    Integer createProduct(ProductRequest productRequest);
}
