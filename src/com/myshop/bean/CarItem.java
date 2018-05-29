package com.myshop.bean;

/**
 * 购物项
 */
public class CarItem {
	@Override
	public String toString() {
		return "CarItem [product=" + product + ", count=" + count + ", subTotal=" + subTotal + "]";
	}
	private Product product;
	private Integer count;
	private Double subTotal;
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Double getSubTotal() {
		//价格小计等于单价乘以数量
		return product.getShop_price()*count;
	}
}
