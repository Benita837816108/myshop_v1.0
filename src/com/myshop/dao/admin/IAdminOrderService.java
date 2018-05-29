package com.myshop.dao.admin;

import com.myshop.bean.Order;
import com.myshop.bean.PageBean;

public interface IAdminOrderService {

	PageBean<Order> findPageBean(Integer curPage, Integer state);

	String findOrderJsonByOid(String oid);
	
	Order findOrderByOid(String oid);

	void updateOrder(Order order);
}
