package com.myshop.dao.admin.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.myshop.bean.Product;
import com.myshop.dao.admin.IAdminProductDao;
import com.myshop.utils.C3P0Util;

public class AdminProductDaoImpl implements IAdminProductDao{

	@Override
	public Long getCount() throws SQLException {
		QueryRunner runner=new QueryRunner(C3P0Util.getDataSource());
		String sql="select count(*) from product";
		Long totalSize = (Long) runner.query(sql, new ScalarHandler());
		return totalSize;
	}

	@Override
	public List<Product> findProduct(Integer curPage, Integer pageSize) throws SQLException {
		QueryRunner runner=new QueryRunner(C3P0Util.getDataSource());
		String sql="select * from product limit ?,? ";
		List<Product> list = runner.query(sql, new BeanListHandler<>(Product.class),(curPage-1)*pageSize,pageSize);
		return list;
	}

	@Override
	public void saveProduct(Product product) throws SQLException {
		QueryRunner runner= new QueryRunner(C3P0Util.getDataSource());
		String sql="insert into product values(?,?,?,?,?,?,?,?,?,?)";
		
		runner.update(sql,product.getPid(),product.getPname(),product.getMarket_price(),product.getShop_price(),product.getPimage(),product.getPdate(),product.getIs_hot(),product.getPdesc(),product.getPflag(),product.getCategory().getCid());
	}

	@Override
	public void delProduct(String pid) throws Exception {
		QueryRunner runner= new QueryRunner(C3P0Util.getDataSource());
		String sql="delete from product where pid=?";
		runner.update(sql, pid);
	}

	@Override
	public Product findProductByPid(String pid) throws SQLException {
		QueryRunner runner= new QueryRunner(C3P0Util.getDataSource());
		String sql="select * from product where pid=?";
		Product product = runner.query(sql,new BeanHandler<>(Product.class),pid);
		return product;
	}

	@Override
	public void updateProduct(Product p) throws SQLException {
		QueryRunner runner= new QueryRunner(C3P0Util.getDataSource());
		String sql="update product set pname=?,market_price=?,shop_price=?,pimage=?,pdate=?,is_hot=?,pdesc=? where pid=?";
		 runner.update(sql,p.getPname(),p.getMarket_price(),p.getShop_price(),p.getPimage(),p.getPdate(),p.getIs_hot(),p.getPdesc(),p.getPid());
		
	}
	
}
