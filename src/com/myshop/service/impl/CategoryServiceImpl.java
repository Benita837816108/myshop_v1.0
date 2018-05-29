package com.myshop.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.msyshop.constant.Constant;
import com.myshop.bean.Category;
import com.myshop.dao.ICategoryDao;
import com.myshop.factory.ContextFactory;
import com.myshop.service.ICategoryService;
import com.myshop.utils.JedisUtil;
import com.myshop.utils.JsonUtil;

import redis.clients.jedis.Jedis;
public class CategoryServiceImpl implements ICategoryService{
	@Override
	public String findAllCategory() {
		//调用dao层的方法，查找所有的分类信息
		//1.从redis中获取存放的所有分类信息的json字符串
		String json = getFromRedis(Constant.MYSHOP_CATEGORY_ALL);
		if (json == null) {
			//说明redis中没有跟所有分类相关的json字符串
			//2.从MySql中获取所有分类的集合
			try {
				List<Category> categories = getCategoriesFromMySql();
				//将集合转换成json字符串
				json = JsonUtil.list2json(categories);
				//3.将json字符串存放到redis中
				save2Redis(Constant.MYSHOP_CATEGORY_ALL,json);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return json;
	}
	/**
	 * 将值存放到redis中
	 * @param string
	 * @param json
	 */
	private void save2Redis(String key, String value) {
		//1.获取jedis
		Jedis jedis = JedisUtil.getJedis();
		
		jedis.set(key, value);
		
		//关闭资源
		jedis.close();
	}
	/**
	 * 从redis中获取值
	 * @param string
	 * @return
	 */
	private String getFromRedis(String key) {
		Jedis jedis = JedisUtil.getJedis();
		String json = jedis.get(key);
		//关闭资源
		jedis.close();
		return json;
	}
	/**
	 * 从mysql中获取所有分类的集合
	 * @return
	 * @throws Exception
	 */
	private List<Category> getCategoriesFromMySql() throws Exception{
		ICategoryDao dao = (ICategoryDao) ContextFactory.getInstance("category_dao");
		List<Category> categories = dao.findAllCategory();
		return categories;
	}
	@Override
	public List<Category> findAllCategorysFromMySql() {
		List<Category> categories=new ArrayList<>();
		try {
			categories = getCategoriesFromMySql();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return categories;
	}
	
	
}
