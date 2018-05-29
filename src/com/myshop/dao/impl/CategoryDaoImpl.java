package com.myshop.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.myshop.bean.Category;
import com.myshop.dao.ICategoryDao;
import com.myshop.dao.ICategoryDao;
import com.myshop.utils.C3P0Util;

public class CategoryDaoImpl implements ICategoryDao{

	@Override
	public List<Category> findAllCategory() throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Util.getDataSource());
		String sql = "select * from category";
		List<Category> list = runner.query(sql, new BeanListHandler<>(Category.class));
		
		return list;
	}


}
