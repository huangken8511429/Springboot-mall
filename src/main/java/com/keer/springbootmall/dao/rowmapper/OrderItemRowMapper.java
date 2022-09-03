package com.keer.springbootmall.dao.rowmapper;

import com.keer.springbootmall.model.OrderItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemRowMapper implements RowMapper<OrderItem> {
    @Override
    public OrderItem mapRow(ResultSet rs, int i) throws SQLException {
        OrderItem orderItem = new OrderItem();

        orderItem.setOrderItemId(rs.getInt("order_item_id"));
        orderItem.setOrderId(rs.getInt("order_id"));
        orderItem.setProductId(rs.getInt("product_id"));
        orderItem.setQuantity(rs.getInt("quantity"));
        orderItem.setAmount(rs.getInt("amount"));

        //RowMapper就是讓我們能從SELECT的指令拿出資料庫的數據，
        // 不用去管這個欄位是從哪個Table是從哪裡出來的

        orderItem.setProductName(rs.getString("product_name"));
        orderItem.setImageUrl(rs.getString("image_url"));
        return orderItem;
    }
}
