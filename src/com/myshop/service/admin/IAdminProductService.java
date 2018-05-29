package com.myshop.service.admin;

import com.myshop.bean.PageBean;
import com.myshop.bean.Product;

public interface IAdminProductService {

	PageBean<Product> findPageBean(Integer curPage);

	void saveProduct(Product product);

	void delProduct(String pid);

	Product findProductByPid(String pid);

	void updateProduct(Product product);

}
