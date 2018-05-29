package com.myshop.service.impl;

import java.util.List;

import com.google.gson.Gson;
import com.msyshop.constant.Constant;
import com.myshop.bean.Order;
import com.myshop.bean.PageBean;
import com.myshop.bean.Product;
import com.myshop.dao.IProductDao;
import com.myshop.factory.ContextFactory;
import com.myshop.service.IProductService;
import com.myshop.utils.JsonUtil;

public class ProductServiceImpl implements IProductService{

	@Override
	public String findHotProducts() {
		//调用dao的方法
		String json=null;
		try {
			
			IProductDao dao =(IProductDao) ContextFactory.getInstance("product_dao");
		List<Product> hotProducts=dao.findHotProducts();
	
		
		
		//将热门商品集合转换成json字符串
	Gson gson= new Gson();
		json = gson.toJson(hotProducts);
	
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json;
	}

	@Override
	public String findLatestProducts() {
		//调用dao的方法
				String json=null;
				try {
					
					IProductDao dao =(IProductDao) ContextFactory.getInstance("product_dao");
				List<Product> hotProducts=dao.findLatestProducts();
			
				
				
				//将热门商品集合转换成json字符串
				Gson gson= new Gson();
				json = gson.toJson(hotProducts);
			
			
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return json;
	}

	@Override
	public Product findProductByPid(String pid) {
		//调用dao层的方法根据pid查找商品信息
		Product product = null;
		try {
			IProductDao dao = (IProductDao) ContextFactory.getInstance("product_dao");
			product = dao.findProductByPid(pid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return product;
	}

	@Override
	public PageBean<Product> findPageBean(Integer curPage,String cid) {
		//1.创建一个PageBean对象
		PageBean<Product> pageBean = new PageBean<>();
		//2.将数据封装到PageBean对象中
		//2.1设置当前页数
		pageBean.setCurPage(curPage);
		//2.2设置每页的数据条数
		int pageSize = Constant.PRODUCT_PAGESIZE;
		pageBean.setPageSize(pageSize);
		//2.3设置总数据条数
		//调用dao层的方法，到数据库查找该分类的数据条数
		try {
			IProductDao dao = (IProductDao) ContextFactory.getInstance("product_dao");
			Long totalSize = dao.findCategoryProductCount(cid);
			pageBean.setTotalSize(totalSize);
			//2.4设置总页数
			int totalPage = (int) (totalSize/pageSize);
			//是否除得尽
			if (totalSize % pageSize != 0) {
				//没除尽，totalPage++
				totalPage ++;
			}
			pageBean.setTotalPage(totalPage);
			
			//2.5设置每页的数据集合list
			//调用dao层的方法到数据库进行分页查找
			List<Product> products = dao.findPageProducts(curPage,pageSize,cid);
			pageBean.setList(products);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageBean;
	}

	@Override
	public PageBean<Product> getPageBean(Integer curPage) {
		//1.创建一个PageBean对象
				PageBean<Product> pageBean = new PageBean<>();
				//2.将数据封装到PageBean对象中
				//2.1设置当前页数
				pageBean.setCurPage(curPage);
				//2.2设置每页的数据条数
				int pageSize = Constant.PRODUCT_PAGESIZE;
				pageBean.setPageSize(pageSize);
				//2.3设置总数据条数
				//调用dao层的方法，到数据库查找该分类的数据条数
				try {
					IProductDao dao = (IProductDao) ContextFactory.getInstance("product_dao");
					Long totalSize = dao.getProductCount();
					pageBean.setTotalSize(totalSize);
					//2.4设置总页数
					int totalPage = (int) (totalSize/pageSize);
					//是否除得尽
					if (totalSize % pageSize != 0) {
						//没除尽，totalPage++
						totalPage ++;
					}
					pageBean.setTotalPage(totalPage);
					
					//2.5设置每页的数据集合list
					//调用dao层的方法到数据库进行分页查找
					List<Product> products = dao.findPageProducts(curPage,pageSize);
					pageBean.setList(products);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return pageBean;
	}

}
