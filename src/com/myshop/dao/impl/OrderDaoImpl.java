package com.myshop.dao.impl;

import java.sql.Connection;
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
import com.myshop.bean.User;
import com.myshop.dao.IOrderDao;
import com.myshop.utils.C3P0Util;
import com.myshop.utils.TransactionManager;

public class OrderDaoImpl implements IOrderDao{

	@Override
	public void saveOrder(Order order) throws Exception {
		QueryRunner runner= new QueryRunner();
		
		String sql="insert into orders values(?,?,?,?,?,?,?,?)";
		Object[] params={order.getOid(),order.getOrdertime(),order.getTotal(),order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid()};
		Connection conn = TransactionManager.getConnectionFromThreadLocal();
		runner.update(conn,sql,params);
		
	
	}

	@Override
	public void saveOrderItem(OrderItem item) throws Exception {
		QueryRunner runner= new QueryRunner();
		String sql= "insert into orderitem values(?,?,?,?,?)";
		Object[] params={item.getItemid(),item.getCount(),item.getSubtotal(),item.getProduct().getPid(),item.getOrder().getOid()};
		Connection conn = TransactionManager.getConnectionFromThreadLocal();
		runner.update(conn,sql,params);
	}

	@Override
	public long getCount(User user) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Util.getDataSource());
		String sql = "select count(*) from orders where uid=?";
		long count = (long) runner.query(sql, new ScalarHandler(), user.getUid());
		return count;
	}

	@Override
	public List<Order> findPageOrders(User user, Integer curPage, int pageSize) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Util.getDataSource());
		String sql = "select * from orders where uid=? limit ?,?";
		List<Order> orders = runner.query(sql, new BeanListHandler<>(Order.class), user.getUid(),(curPage - 1)*pageSize,pageSize);
		//已经能获取所有的订单
		return orders;
	}

	@Override
	public List<OrderItem> findOrderItems(Order order) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Util.getDataSource());
		String sql = "SELECT * FROM orderitem o,product p WHERE oid=? AND o.pid=p.pid";
		//获取一个map的集合，集合中的每一个map对应一条结果，一个结果就是一个订单项OrderItem
		List<Map<String, Object>> maps = runner.query(sql, new MapListHandler(), order.getOid());
		
		List<OrderItem> orderItems = new ArrayList<>();
		
		//遍历map集合
		for (Map<String,Object> map : maps) {
			//一个map就对应一个orderItem
			//将map中的数据封装到orderItem中
			OrderItem orderItem = new OrderItem();
			//将map中与OrderItem对象相关的数据封装到OrderItem对象中
			BeanUtils.populate(orderItem, map);
			
			Product product = new Product();
			BeanUtils.populate(product, map);
			
			//设置product到OrderItem中
			orderItem.setProduct(product);
			
			//将orderItem添加到集合中
			orderItems.add(orderItem);
			
			//设置order
			orderItem.setOrder(order);
		}
		return orderItems;
	}

	@Override
	public Order findOrderByOid(String oid) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Util.getDataSource());
		String sql = "select * from orders where oid=?";
		Order order = runner.query(sql, new BeanHandler<>(Order.class), oid);
		return order;
	}

	@Override
	public void updateOrder(Order order) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Util.getDataSource());
		String sql = "update orders set state=?,address=?,name=?,telephone=? where oid=?";
		runner.update(sql, order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getOid());
	}
	

}
