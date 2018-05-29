package com.myshop.service;

import com.myshop.bean.Car;
import com.myshop.bean.Order;
import com.myshop.bean.PageBean;
import com.myshop.bean.User;

public interface IOrderService {

	Order saveOrder(Car car, User user);

	PageBean<Order> findPageBean(Integer curPage, User user);


	Order findOrderByOid(String oid);

	void updateOrder(Order order);

}
