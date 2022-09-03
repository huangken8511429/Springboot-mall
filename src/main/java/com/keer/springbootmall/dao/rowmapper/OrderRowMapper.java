package com.keer.springbootmall.dao.rowmapper;

import com.keer.springbootmall.model.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<Order>  {
    @Override
    public Order mapRow(ResultSet rs, int i) throws SQLException {
        Order order = new Order();

        order.setOrder_id(rs.getInt("order_id"));
        order.setUser_id(rs.getInt("user_id"));
        order.setTotalAmount(rs.getInt("total_amount"));
        order.setCreatedDate(rs.getTimestamp("created_date"));
        order.setLastModifiedDate(rs.getTimestamp("last_modified_date"));

        return order;
    }
}