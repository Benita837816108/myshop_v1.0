package com.myshop.dao;

import java.sql.SQLException;

import com.myshop.bean.User;

public interface IUserDao {

	 void regist(User user) throws SQLException;

	User findUserByCode(String code) throws SQLException;

	void updateUser(User user) throws SQLException;

	User findUser(String username, String password) throws SQLException;
	
}
