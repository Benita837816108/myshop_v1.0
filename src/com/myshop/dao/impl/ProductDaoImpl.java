package com.myshop.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.msyshop.constant.Constant;
import com.myshop.bean.Category;
import com.myshop.bean.Order;
import com.myshop.bean.OrderItem;
import com.myshop.bean.Product;
import com.myshop.dao.IProductDao;
import com.myshop.utils.C3P0Util;

public class ProductDaoImpl implements IProductDao{

	@Override
	public List<Product> findHotProducts() throws SQLException {
		QueryRunner runner= new QueryRunner(C3P0Util.getDataSource());
		String sql="select * from product where is_hot=? and pflag=? limit ?,?";
		List<Product> list = runner.query(sql, new BeanListHandler<>(Product.class),Constant.HOT,Constant.UPJIA,0,9);
	
		return list;
		
	}

	@Override
	public List<Product> findLatestProducts() throws SQLException {
		QueryRunner runner= new QueryRunner(C3P0Util.getDataSource());
		String sql="select * from product where pflag=? order by pdate desc limit ?,?";
		List<Product> list = runner.query(sql, new BeanListHandler<>(Product.class),Constant.UPJIA,0,9);
	
		return list;
	}

	@Override
	public Product findProductByPid(String pid) throws Exception {
		QueryRunner runner = new QueryRunner(C3P0Util.getDataSource());
		//针对连接查询出来的情况，要设置到JavaBean中，而javaBean对象中的属性又是一个JavaBean
		String sql = "select * from product p,category c where pid=? and p.cid=c.cid";
		//获取到的map中包含了结果集中的所有字段名和字段值，字段名就是map的key字段值就是map的value
		Map<String, Object> map = runner.query(sql, new MapHandler(), pid);
		//将map中与product类相关的字段值设置到product对象上
		Product product = new Product();
		BeanUtils.populate(product, map);
		
		//product只差一个category属性没设置好了
		Category category = new Category();
		//将map中跟category相关的信息设置到category对象上
		BeanUtils.populate(category, map);
		
		product.setCategory(category);
		
		return product;
	}

	@Override
	public Long findCategoryProductCount(String cid) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Util.getDataSource());
		String sql = "select count(*) from product where cid=?";
		Long count = (Long) runner.query(sql, new ScalarHandler(), cid);
		return count;
	}

	@Override
	public List<Product> findPageProducts(Integer curPage, int pageSize, String cid) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Util.getDataSource());
		int a = (curPage - 1)*pageSize;
		String sql = "select * from product where cid=? limit ?,?";
		List<Product> list = runner.query(sql, new BeanListHandler<>(Product.class), cid,a,pageSize);
		return list;
	}

	@Override
	public Long getProductCount() throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Util.getDataSource());
		String sql = "select count(*) from product ";
		Long count = (Long) runner.query(sql, new ScalarHandler());
		return count;
	}

	@Override
	public List<Product> findPageProducts(Integer curPage, int pageSize) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Util.getDataSource());
		int a = (curPage - 1)*pageSize;
		String sql = "select * from product  limit ?,?";
		List<Product> list = runner.query(sql, new BeanListHandler<>(Product.class),a,pageSize);
		return list;
	}

	

}
