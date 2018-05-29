package com.myshop.dao.admin;

import java.sql.SQLException;
import java.util.List;

import com.myshop.bean.Product;

public interface IAdminProductDao {

	Long getCount() throws SQLException;

	List<Product> findProduct(Integer curPage, Integer pageSize) throws SQLException;

	void saveProduct(Product product) throws SQLException;

	void delProduct(String pid) throws Exception;

	Product findProductByPid(String pid) throws SQLException;

	void updateProduct(Product product) throws SQLException;

	

}
