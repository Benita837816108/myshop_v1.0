package com.myshop.dao;

import java.sql.SQLException;
import java.util.List;

import com.myshop.bean.Order;
import com.myshop.bean.OrderItem;
import com.myshop.bean.Product;

public interface IProductDao {

	List<Product> findHotProducts() throws SQLException;

	List<Product> findLatestProducts() throws SQLException;

	Product findProductByPid(String pid) throws Exception;

	Long findCategoryProductCount(String cid) throws SQLException;

	List<Product> findPageProducts(Integer curPage, int pageSize, String cid) throws SQLException;

	Long getProductCount() throws SQLException;

	List<Product> findPageProducts(Integer curPage, int pageSize) throws SQLException;

	

}
