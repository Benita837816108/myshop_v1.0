package com.myshop.dao.admin.impl;

import java.util.List;

import com.msyshop.constant.Constant;
import com.myshop.bean.Order;
import com.myshop.bean.OrderItem;
import com.myshop.bean.PageBean;
import com.myshop.dao.IAdminOrderDao;
import com.myshop.dao.admin.IAdminOrderService;
import com.myshop.factory.ContextFactory;

import com.myshop.utils.JsonUtil;

public class AdminOrderServiceImpl implements IAdminOrderService{
	@Override
	public PageBean<Order> findPageBean(Integer curPage, Integer state) {
		//1.创建PageBean对象
		PageBean<Order> pageBean = new PageBean<>();
		//2.设置PageBean的值
		//2.1设置curPage
		pageBean.setCurPage(curPage);
		//2.2设置pageSize
		Integer pageSize = Constant.ADMIN_ORDER_PAGESIZE;
		pageBean.setPageSize(pageSize);
		//2.3设置totalSize
		try {
			IAdminOrderDao dao = (IAdminOrderDao) ContextFactory.getInstance("adminOrder_dao");
			Long totalSize = dao.getCount(state);
			pageBean.setTotalSize(totalSize);
			
			//2.4设置totalPage
			Integer totalPage = (int) (totalSize/pageSize);
			if (totalSize % pageSize != 0) {
				totalPage ++;
			}
			pageBean.setTotalPage(totalPage);
			
			//2.5设置每页的数据集合
			List<Order> orders = dao.findPageOrders(curPage,pageSize,state);
			pageBean.setList(orders);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageBean;
	}

	@Override
	public String findOrderJsonByOid(String oid) {
		//调用dao层的方法根据oid获取Order对象
		String json = null;
		try {
			Order order = findOrderByOid(oid);
			//将order对象转换成json字符串
			json = JsonUtil.object2json(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	@Override
	public Order findOrderByOid(String oid) {
		//调用dao层的方法根据oid获取Order对象
		Order order = null;
		try {
			IAdminOrderDao dao = (IAdminOrderDao) ContextFactory.getInstance("adminOrder_dao");
			order = dao.findOrderByOid(oid);
			//order中有没有orderItems
			//往订单中设置orderItems
			//调用dao层的方法查找该订单的orderItems
			List<OrderItem> orderItems = dao.getOrderItemsByOid(oid);
			order.setOrderItems(orderItems);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return order;
	}

	@Override
	public void updateOrder(Order order) {
		//调用dao层的方法更新订单
		try {
			IAdminOrderDao dao = (IAdminOrderDao) ContextFactory.getInstance("adminOrder_dao");
			dao.updateOrder(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
