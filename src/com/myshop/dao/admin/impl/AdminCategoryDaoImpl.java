package com.myshop.dao.admin.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.myshop.bean.Category;
import com.myshop.dao.admin.IAdminCategoryDao;
import com.myshop.utils.C3P0Util;

public class AdminCategoryDaoImpl implements IAdminCategoryDao{
	@Override
	public List<Category> findCategory() throws SQLException {
		QueryRunner runner=new QueryRunner(C3P0Util.getDataSource());
		String sql="select * from category";
		List<Category> list = runner.query(sql, new BeanListHandler<>(Category.class));
		return list;
	}

	@Override
	public void addCategory(Category category) throws SQLException {
		QueryRunner runner=new QueryRunner(C3P0Util.getDataSource());
		String sql="insert into category values(?,?)";
		runner.update(sql,category.getCid(),category.getCname());
	
	}

	@Override
	public void delCategory(String cid) throws SQLException {
		QueryRunner runner=new QueryRunner(C3P0Util.getDataSource());
		String sql="delete from category where cid=?";
		runner.update(sql,cid);
	}

	@Override
	public Category findCategoryByCid(String cid) throws SQLException {
		QueryRunner runner=new QueryRunner(C3P0Util.getDataSource());
		String sql="select * from category where cid=?";
		Category category = runner.query(sql, new BeanHandler<>(Category.class),cid);
		return category;
	}

	@Override
	public void editCategory(String cid, String cname) throws SQLException {
		QueryRunner runner=new QueryRunner(C3P0Util.getDataSource());
		String sql="update category set cname=? where cid=? ";
		runner.update(sql,cname,cid);
	}
}
