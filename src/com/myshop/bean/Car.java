package com.myshop.bean;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 购物车类
 */
public class Car {
	private Collection<CarItem> values;
	//总价格
	private double totalPrice;
	//存放carItem的容器，为了方便移除购物车中的商品，所以使用map更好
	private Map<String, CarItem> items = new HashMap<>();
	@Override
	public String toString() {
		return "Car [totalPrice=" + totalPrice + ", items=" + items + "]";
	}
	/**
	 * 实时获取总价格
	 * @return
	 */
	public double getTotalPrice() {
		//每次计算之前让其清零
		totalPrice = 0;
		//实时使用算法计算总价格
		//遍历map
		Collection<CarItem> vals = getValues();
		for (CarItem carItem : vals) {
			totalPrice += carItem.getSubTotal();
		}
		return totalPrice;
	}
	public Map<String, CarItem> getItems() {
		return items;
	}
	//封装一个添加购物项的方法
	public void add2Car(CarItem item){
		//在添加之前，先判断这个购物项在购物车中有没有?是否存在
		String key = item.getProduct().getPid();
		if (items.containsKey(key)) {
			//表示这个购物项已经存在了
			//先获取到原有的数量，然后再将其数量加上这次的数量
			CarItem carItem = items.get(key);
			Integer count = carItem.getCount() + item.getCount();
			
			//将新的总数设置进去
			item.setCount(count);
		}
		//将carItem添加到容器中
		items.put(key, item);
	}
	public void removeFromCar(String pid){
		//从map中移除掉一个CarItem
		items.remove(pid);
	}
	public Collection<CarItem> getValues() {
		values = items.values();
		return values;
	}
}
