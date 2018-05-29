package com.myshop.dao;

import java.sql.SQLException;
import java.util.List;

import com.myshop.bean.Order;
import com.myshop.bean.OrderItem;
import com.myshop.bean.User;

public interface IOrderDao {

	void saveOrder(Order order) throws Exception;

	void saveOrderItem(OrderItem item) throws Exception;



	

	List<OrderItem> findOrderItems(Order order) throws Exception;

	

	Order findOrderByOid(String oid) throws SQLException;

	List<Order> findPageOrders(User user, Integer curPage, int pageSize) throws SQLException;

	long getCount(User user) throws SQLException;

	void updateOrder(Order order) throws SQLException;

}
