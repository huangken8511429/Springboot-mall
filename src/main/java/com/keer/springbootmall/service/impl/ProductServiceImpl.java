package com.keer.springbootmall.service.impl;


import com.keer.springbootmall.constant.CategoryParam;
import com.keer.springbootmall.dao.ProductDao;
import com.keer.springbootmall.dto.ProductRequest;
import com.keer.springbootmall.model.Product;
import com.keer.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
private ProductDao productDao;


    @Override
    public Integer countProduct(CategoryParam categoryParam) {
        return productDao.countProduct(categoryParam);
    }

    @Override
    public List<Product> getProducts(CategoryParam categoryParam) {
        return productDao.getProducts(categoryParam);

    }


    @Override
    public Product getProductbyId(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        productDao.updateProduct(productId, productRequest);
    }

    @Override
    public void deleteProductById(Integer productId) {
        productDao.deleteProductById(productId);
    }
}
