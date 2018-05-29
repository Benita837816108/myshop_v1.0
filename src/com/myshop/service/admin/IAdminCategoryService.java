package com.myshop.service.admin;

import java.sql.SQLException;
import java.util.List;

import com.myshop.bean.Category;

public interface IAdminCategoryService {

	List<Category> findAll() throws SQLException;

	void addCategory(Category category);

	void delCategory(String cid);

	Category findCategoryByCid(String cid);

	void editCategory(String cid, String cname);

	

	String findAll1();

	

}
