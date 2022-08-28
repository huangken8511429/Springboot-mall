package com.keer.springbootmall.dao.impl;

import com.keer.springbootmall.dao.ProductDao;
import com.keer.springbootmall.dao.rowmapper.Rowmapper;
import com.keer.springbootmall.dto.ProductRequest;
import com.keer.springbootmall.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getById(Integer productId) {
        String sql = "SELECT product_id,product_name, category, image_url, price, stock, description, " +
                "created_date, last_modified_date FROM product WHERE product_id= :productId";

        Map<String, Object> map = new HashMap<>();

        map.put("productId", productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new Rowmapper());

        if (productList.size() > 0) {
            return productList.get(0);
        } else
            return null;
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "INSERT INTO product(product_name, category, image_url, price, stock, " +
                "description, created_date, last_modified_date) " +
                "VALUES (:productName, :category, :imageUrl, :price, :stock, :description, " +
                "  :createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();

        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date now = new Date();

        map.put("createdDate",now);
        map.put("lastModifiedDate",now);


        //這邊比較重要，務必多複習
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int productId = keyHolder.getKey().intValue();

        return productId;

    }
}
