package com.myshop.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.myshop.bean.Order;
import com.myshop.bean.OrderItem;
import com.myshop.bean.Product;
import com.myshop.dao.IAdminOrderDao;
import com.myshop.utils.C3P0Util;

public class AdminOrderDaoImpl implements IAdminOrderDao{
	@Override
	public Long getCount(Integer state) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Util.getDataSource());
		String sql = "select count(*) from orders";
		
		List<Object> params = new ArrayList<>();
		
		if (state != null) {
			sql += " where state=?";
			params.add(state);
		}
		Long count = (Long) runner.query(sql, new ScalarHandler(), params.toArray());
		return count;
	}

	@Override
	public List<Order> findPageOrders(Integer curPage, Integer pageSize, Integer state) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Util.getDataSource());
		List<Order> orders = new ArrayList<>();
		if (state == null) {
			String sql = "select * from orders limit ?,?";
			orders = runner.query(sql, new BeanListHandler<>(Order.class), (curPage - 1)*pageSize,pageSize);
		}else {
			String sql = "select * from orders where state=? limit ?,?";
			orders = runner.query(sql, new BeanListHandler<>(Order.class), state,(curPage - 1)*pageSize,pageSize);
		}
		return orders;
	}

	@Override
	public Order findOrderByOid(String oid) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Util.getDataSource());
		String sql = "select * from orders where oid=?";
		Order order = runner.query(sql, new BeanHandler<>(Order.class), oid);
		return order;
	}

	@Override
	public List<OrderItem> getOrderItemsByOid(String oid) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Util.getDataSource());
		String sql = "select * from orderitem o,product p where o.oid=? and o.pid=p.pid";
		List<Map<String, Object>> maps = runner.query(sql, new MapListHandler(),oid);
		
		List<OrderItem> orderItems = new ArrayList<>();
		//遍历出每一个map，每一个map就对应一条结果集
		for (Map<String, Object> map : maps) {
			//将map中和OrderItem对象相对应的字段设置到OrderItem中，将与Product丢向对应的字段设置到Product对象中
			OrderItem orderItem = new OrderItem();
			BeanUtils.populate(orderItem, map);
			
			Product product = new Product();
			BeanUtils.populate(product, map);
			orderItem.setProduct(product);
			
			orderItems.add(orderItem);
		}
		
		return orderItems;
	}

	@Override
	public void updateOrder(Order order) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Util.getDataSource());
		String sql = "update orders set total=?,state=?,address=?,name=?,telephone=?";
		runner.update(sql, order.getTotal(),order.getState(),order.getAddress(),order.getName(),order.getTelephone());
	}

	
	
}
