package com.myshop.dao.impl;


import com.myshop.bean.User;
import com.myshop.dao.IUserDao;
import com.myshop.utils.C3P0Util;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class UserDaoImpl implements IUserDao{

	@Override
	public void regist(User user) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Util.getDataSource());
        String sql="insert into user values(?,?,?,?,?,?,?,?,?,?,?)";
        runner.update(sql,user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(), user.getHobby(),user.getBirthday(),user.getSex(),user.getState(),user.getCode());
	}

	@Override
	public User findUserByCode(String code) throws SQLException {
		QueryRunner runner=new QueryRunner(C3P0Util.getDataSource());
		String sql="select * from user where code=?";
		User user = runner.query(sql,new BeanHandler<>(User.class),code);

		return user;
		
	}

	@Override
	public void updateUser(User user) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Util.getDataSource());
		String sql = "update user set username=?,password=?,name=?,email=?,birthday=?,state=?,code=?,telephone=? where uid=?";
		System.out.println(user.toString());
		runner.update(sql, user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getBirthday(),user.getState(),user.getCode(),user.getTelephone(),user.getUid());
	
	}

	@Override
	public User findUser(String username, String password) throws SQLException {
		QueryRunner runner = new QueryRunner(C3P0Util.getDataSource());
		String sql="select * from  user where username=? and password=?";
		User user = runner.query(sql, new BeanHandler<>(User.class),username,password);
		return user;
	}

	

}
