package com.keer.springbootmall.dao.impl;

import com.keer.springbootmall.constant.CategoryParam;
import com.keer.springbootmall.constant.ProductCategory;
import com.keer.springbootmall.dao.ProductDao;
import com.keer.springbootmall.dao.rowmapper.Rowmapper;
import com.keer.springbootmall.dto.ProductRequest;
import com.keer.springbootmall.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
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
    public List<Product> getProducts(CategoryParam categoryParam) {
        String sql = "SELECT product_id, product_name, category, image_url, price, stock, description, created_date, last_modified_date " +
                "FROM product WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        if (categoryParam.getCategory() != null) {
            sql = sql + " AND category = :category";
            map.put("category", categoryParam.getCategory().name()); //.name是把原本ProductCategory enum的值轉成String
        }


        if (categoryParam.getSearch() != null) {
            sql = sql + " AND product_name LIKE :search";
            map.put("search", "%" + categoryParam.getSearch() + "%");  //模糊查詢的%一定要放在MAP裡面,這是SpringJDBC的限制
        }

        sql=sql+" ORDER BY " +categoryParam.getOrderBy() + " " + categoryParam.getSort();

        sql=sql+" LIMIT :limit OFFSET :offset";

        map.put("limit",categoryParam.getLimit());
        map.put("offset",categoryParam.getOffset());

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new Rowmapper());

        return productList;
    }

    @Override
    public Product getById(Integer productId) {
        String sql = "SELECT product_id,product_name, category, image_url, price, stock, description, created_date, last_modified_date FROM product WHERE product_id= :productId";

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
                "VALUES ( :productName, :category, :imageUrl, :price, :stock, :description, " +
                "  :createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();

        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date now = new Date();

        map.put("createdDate", now);
        map.put("lastModifiedDate", now);


        //這邊比較重要，務必多複習
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int productId = keyHolder.getKey().intValue();

        return productId;

    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        String sql = "UPDATE product SET product_name = :productName, category = :category, image_url = :imageUrl, " +
                "price = :price, stock = :stock, description = :description, last_modified_date = :lastModifiedDate " +
                "WHERE product_id = :productId ";

        Map<String, Object> map = new HashMap<>();

        map.put("productId", productId);

        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        map.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteProductById(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id = :productId ";

        Map<String, Object> map = new HashMap<>();

        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql, map);
    }
}
