package com.myshop.service.admin.impl;

import java.util.List;

import com.msyshop.constant.Constant;
import com.myshop.bean.PageBean;
import com.myshop.bean.Product;
import com.myshop.dao.admin.IAdminProductDao;
import com.myshop.factory.ContextFactory;
import com.myshop.service.admin.IAdminProductService;

public class AdminProductServiceImpl implements IAdminProductService{

	@Override
	public PageBean<Product> findPageBean(Integer curPage) {
		//封装pegebean
		PageBean<Product> page=new PageBean<>();
		//当前页
		page.setCurPage(curPage);
		//每个显示的数据条数
		Integer pageSize=Constant.PRODUCT_PAGESIZE;
		page.setPageSize(pageSize);
		//总数据条数,调用dao层
		Long totalSize=0L;
		try {
			IAdminProductDao dao=(IAdminProductDao) ContextFactory.getInstance("adminProduct_dao");
			totalSize=dao.getCount();
			page.setTotalSize(totalSize);
			//设置总页数
			Integer totalPage=(int) (totalSize/pageSize);
			//除不尽要加一页
			if(totalSize%pageSize!=0){
				totalPage++;
			}
			page.setTotalPage(totalPage);
			//设置每个的商品集合
			List<Product> list=dao.findProduct(curPage,pageSize);
			page.setList(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return page;
	}

	@Override
	public void saveProduct(Product product) {
		//调用业务层的方法将商品保存到数据库
		try {
			IAdminProductDao dao= (IAdminProductDao) ContextFactory.getInstance("adminProduct_dao");
			dao.saveProduct(product);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delProduct(String pid) {
		//调用业务层的方法将商品保存到数据库
				try {
					IAdminProductDao dao= (IAdminProductDao) ContextFactory.getInstance("adminProduct_dao");
					dao.delProduct(pid);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

	@Override
	public Product findProductByPid(String pid) {
		//调用业务层的方法将商品保存到数据库
		Product product=null;
		try {
			IAdminProductDao dao= (IAdminProductDao) ContextFactory.getInstance("adminProduct_dao");
			product=dao.findProductByPid(pid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return product;
	}

	@Override
	public void updateProduct(Product product) {
		
		
		try {
			IAdminProductDao dao = (IAdminProductDao) ContextFactory.getInstance("adminProduct_dao");
			dao.updateProduct(product);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
