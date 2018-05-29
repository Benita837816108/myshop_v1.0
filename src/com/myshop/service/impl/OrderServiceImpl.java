package com.myshop.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.msyshop.constant.Constant;
import com.myshop.bean.Car;
import com.myshop.bean.CarItem;
import com.myshop.bean.Order;
import com.myshop.bean.OrderItem;
import com.myshop.bean.PageBean;
import com.myshop.bean.User;
import com.myshop.dao.IOrderDao;
import com.myshop.factory.ContextFactory;
import com.myshop.service.IOrderService;
import com.myshop.utils.DateUtil;
import com.myshop.utils.TransactionManager;
import com.myshop.utils.UUIDUtils;

public class OrderServiceImpl implements IOrderService{

	@Override
	public Order saveOrder(Car car, User user) {
		
		Order order = new Order();
		//设置oid
		order.setOid(UUIDUtils.getId());
		//设置ordertime
		order.setOrdertime(DateUtil.getCurrentTime());
		//设置总价
		order.setTotal(car.getTotalPrice());
		//设置支付状态
		order.setState(Constant.ORDERS_NOPAIED);
		//设置user
		order.setUser(user);
		//设置订单项
		List<OrderItem> orderItems= new ArrayList<>();
		
		//订单全部在购物车里面
		Collection<CarItem> values=car.getValues();
		for (CarItem carItem : values) {
			OrderItem item = new OrderItem();
			//设置order
			item.setOrder(order);
			//设置product
			item.setProduct(carItem.getProduct());
			//设置count
			item.setCount(carItem.getCount());
			//设置itemid
			item.setItemid(UUIDUtils.getId());
			//设置小计价格subtotal
			item.setSubtotal(carItem.getSubTotal());
			//将item添加到order里面
			orderItems.add(item);		
		}
		order.setOrderItems(orderItems);
		//设置address\telephone等信息还未填写所以先不设置
		/*order.setName("");
		order.setAddress("");
		order.setTelephone("");*/
		
		//调用dao层的方法将order保存到数据库的数据表中并返回servlet
		
		try {
			//开启事务
			TransactionManager.startTransaction();
			IOrderDao dao= (IOrderDao) ContextFactory.getInstance("order_dao");
			dao.saveOrder(order);
			
			//保存订单项
			for (OrderItem item : orderItems) {
				dao.saveOrderItem(item);
			}
			
			//提交事务
			TransactionManager.commit();
		} catch (Exception e) {
			
			try {
				//回滚事务
				TransactionManager.rollback();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally {
				//关闭
				try {
					TransactionManager.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}
		return order;
	}

	@Override
	public PageBean<Order> findPageBean(Integer curPage, User user) {
		//1.创建PageBean对象
		PageBean<Order> pageBean = new PageBean<>();
		//2.设置PageBean
		//2.1设置curPage
		pageBean.setCurPage(curPage);
		//2.2设置每页的数据条数pageSize
		int pageSize = Constant.ORDER_PAGESIZE;
		pageBean.setPageSize(pageSize);
		//2.3设置总数据条数totalSize
		IOrderDao dao;
		try {
			dao = (IOrderDao) ContextFactory.getInstance("order_dao");
			long totalSize = dao.getCount(user);
			pageBean.setTotalSize(totalSize);
			//2.4设置总页数
			int totalPage = (int) (totalSize/pageSize);
			//判断是否能除尽
			if (totalSize % pageSize != 0) {
				totalPage ++;
			}
			pageBean.setTotalPage(totalPage);
			//2.5设置每页的数据集合
			//到数据库中做分页查询
			List<Order> orders = dao.findPageOrders(user,curPage,pageSize);
			//这些订单，还得做一些处理,针对每个订单设置它的订单项
			for (Order order : orders) {
				//遍历出每一个订单
				String oid = order.getOid();
				//根据oid到orderitem表中找出该订单的所有订单项（连接product表一起查询）
				List<OrderItem> orderItems = dao.findOrderItems(order);
				
				//设置order中的orderItems
				order.setOrderItems(orderItems);
				//设置user
				order.setUser(user);
			}
			
			pageBean.setList(orders);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageBean;
	}

	@Override
	public Order findOrderByOid(String oid) {
		//调用dao层的方法，到数据库根据oid查找到该订单的信息
		Order order = null;
		try {
			IOrderDao dao = (IOrderDao) ContextFactory.getInstance("order_dao");
			order = dao.findOrderByOid(oid);
			//order真的设置好了吗?还差orderItems
			//调用dao层的方法，获取orderItems
			List<OrderItem> orderItems = dao.findOrderItems(order);
			order.setOrderItems(orderItems);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return order;
	}

	@Override
	public void updateOrder(Order order) {
		//调用dao层的方法，到数据库更新订单信息
		try {
			IOrderDao dao = (IOrderDao) ContextFactory.getInstance("order_dao");
			dao.updateOrder(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
