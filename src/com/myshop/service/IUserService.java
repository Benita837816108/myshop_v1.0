package com.myshop.service;

import com.myshop.bean.User;

public interface IUserService {
	/**
	 * 处理注册业务
	 * @param user
	 * @return
	 */
	boolean register(User user);
	/**
	 * 处理激活业务
	 * @param code
	 * @return
	 */
	boolean doActive(String code);
	/**
	 * 处理登陆业务
	 * @param username
	 * @param password
	 * @return
	 */
	User dologin(String username, String password);


	
}
