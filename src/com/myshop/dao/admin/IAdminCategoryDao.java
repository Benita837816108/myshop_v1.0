package com.myshop.dao.admin;

import java.sql.SQLException;
import java.util.List;

import com.myshop.bean.Category;

public interface IAdminCategoryDao {

	List<Category> findCategory() throws SQLException;

	void addCategory(Category category) throws SQLException;

	void delCategory(String cid) throws SQLException;

	Category findCategoryByCid(String cid) throws SQLException;

	void editCategory(String cid, String cname) throws SQLException;

}
