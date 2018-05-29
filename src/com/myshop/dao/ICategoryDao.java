package com.myshop.dao;

import java.sql.SQLException;
import java.util.List;

import com.myshop.bean.Category;

public interface ICategoryDao {

	List<Category> findAllCategory() throws SQLException;



}
