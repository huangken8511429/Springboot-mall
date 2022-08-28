package com.keer.springbootmall.dao;

import com.keer.springbootmall.dto.ProductRequest;
import com.keer.springbootmall.model.Product;

public interface ProductDao {

    Product getById (Integer productId);

    Integer createProduct(ProductRequest productRequest);

}
