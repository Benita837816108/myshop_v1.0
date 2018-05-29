package com.myshop.dao;

import java.sql.SQLException;
import java.util.List;

import com.myshop.bean.Order;
import com.myshop.bean.OrderItem;

public interface IAdminOrderDao {

	Long getCount(Integer state) throws SQLException;

	List<Order> findPageOrders(Integer curPage, Integer pageSize, Integer state) throws SQLException;

	Order findOrderByOid(String oid) throws SQLException;

	List<OrderItem> getOrderItemsByOid(String oid) throws Exception;

	void updateOrder(Order order) throws SQLException;

}
