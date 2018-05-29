package com.myshop.service.admin.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.google.gson.Gson;
import com.msyshop.constant.Constant;
import com.myshop.bean.Category;
import com.myshop.dao.admin.IAdminCategoryDao;
import com.myshop.factory.ContextFactory;
import com.myshop.service.admin.IAdminCategoryService;
import com.myshop.utils.C3P0Util;
import com.myshop.utils.JedisUtil;

import redis.clients.jedis.Jedis;
import sun.print.resources.serviceui;

public class AdminCategoryServiceImpl implements IAdminCategoryService{

	@Override
	public List<Category> findAll() throws SQLException {
		//调用dao层的方法
		List<Category> list=new ArrayList<>();
		try {
			IAdminCategoryDao dao = (IAdminCategoryDao) ContextFactory.getInstance("adminCategory_dao");
			list=dao.findCategory();
			//把list转成json字符串
			Gson gson= new Gson();
			String json = gson.toJson(list);
			//更新redis数据库
			Jedis jedis=JedisUtil.getJedis();
		jedis.set(Constant.MYSHOP_CATEGORY_ALL, json);
		//关闭资源
		jedis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}


	@Override
	public void addCategory(Category category) {
		//调用dao层的方法
		try {
			IAdminCategoryDao dao= (IAdminCategoryDao) ContextFactory.getInstance("adminCategory_dao");
			dao.addCategory(category);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delCategory(String cid) {
		//调用dao层
		try {
			IAdminCategoryDao dao= (IAdminCategoryDao) ContextFactory.getInstance("adminCategory_dao");
			dao.delCategory(cid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Category findCategoryByCid(String cid) {
		//调用dao层的方法
		
		Category category=null;
		try {
			IAdminCategoryDao dao=(IAdminCategoryDao) ContextFactory.getInstance("adminCategory_dao");
			category=dao.findCategoryByCid(cid);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return category;
	}

	@Override
	public void editCategory(String cid, String cname) {
		//调用dao
		try {
			IAdminCategoryDao dao= (IAdminCategoryDao) ContextFactory.getInstance("adminCategory_dao");
			dao.editCategory(cid,cname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public String findAll1() {
		List<Category> list=new ArrayList<>();
		String json=null;
		try {
			IAdminCategoryDao dao = (IAdminCategoryDao) ContextFactory.getInstance("adminCategory_dao");
			list=dao.findCategory();
			//把list转成json字符串
			Gson gson= new Gson();
			json = gson.toJson(list);
			
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json;
	}

	

}
