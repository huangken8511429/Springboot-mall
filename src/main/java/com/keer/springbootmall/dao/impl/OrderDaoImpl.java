package com.keer.springbootmall.dao.impl;

import com.keer.springbootmall.dao.OrderDao;
import com.keer.springbootmall.dao.rowmapper.OrderItemRowMapper;
import com.keer.springbootmall.dao.rowmapper.OrderRowMapper;
import com.keer.springbootmall.model.Order;
import com.keer.springbootmall.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<OrderItem> getOrderItemByOrderId(Integer orderId) {
        String sql = "SELECT oi.order_item_id, oi.order_id, oi.product_id, oi.quantity, oi.amount, p.product_name, p.image_url " +
                "FROM order_item as oi " +
                "LEFT JOIN product as p ON oi.product_id = p.product_id " +
                "WHERE oi.order_id = :orderId";

        Map<String, Object> map = new HashMap<>();

        map.put("orderId",orderId);

        List<OrderItem> orderItemList = namedParameterJdbcTemplate.query(sql,map,new OrderItemRowMapper());

       return orderItemList;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date FROM `order` WHERE order_id = :orderId";

        Map<String, Object> map = new HashMap<>();

        map.put("orderId", orderId);

        List<Order> orderList = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());

        if (orderList.size() > 0)
            return orderList.get(0);
        else
            return null;
    }

    @Transactional
    @Override
    public Integer createdOrder(Integer userId, Integer totalAmount) {
        String sql = "INSERT INTO `order`(user_id, total_amount, created_date, last_modified_date) " +
                "VALUES (:userId, :totalAmount, :createdDate, :lastModifiedDate)";
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("totalAmount", totalAmount);

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        int orderId = keyHolder.getKey().intValue();
        return orderId;
    }

    @Override
    public void createdOrderItems(Integer orderId, List<OrderItem> orderItemList) {
     /*   效率較低，因為for loop一條一條加入
        for (OrderItem orderItem : orderItemList) {

            String sql = " INSERT INTO order_item (order_id, product_id, quantity, amount) " +
                    "VALUES (:orderId, :productId, :quantity, :amount)";

            Map<String, Object> map = new HashMap<>();
            map.put("orderId", orderId);
            map.put("productId", orderItem.getProductId());
            map.put("quantiy", orderItem.getQuantity());
            map.put("amount", orderItem.getAmount());

            namedParameterJdbcTemplate.update(sql, map);
        }
        */
        //使用batchUpdate 一次性加入數據，效率更高
        String sql = "INSERT INTO order_item (order_id, product_id, quantity, amount) " +
                "VALUES (:orderId, :productId, :quantity, :amount)";

        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[orderItemList.size()];

        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);

            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("orderId", orderId);
            parameterSources[i].addValue("productId", orderItem.getProductId());
            parameterSources[i].addValue("quantity", orderItem.getQuantity());
            parameterSources[i].addValue("amount", orderItem.getAmount());
        }
        namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
    }
}