package com.myshop.service;

import com.myshop.bean.PageBean;
import com.myshop.bean.Product;

public interface IProductService {

	String findHotProducts();

	String findLatestProducts();

	PageBean<Product> findPageBean(Integer curPage, String cid);

	Product findProductByPid(String pid);

	

	PageBean<Product> getPageBean(Integer curPage);

	

}
